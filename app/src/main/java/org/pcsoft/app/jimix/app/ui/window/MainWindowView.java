package org.pcsoft.app.jimix.app.ui.window;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.collections.ListChangeListener;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.apache.commons.io.FilenameUtils;
import org.pcsoft.app.jimix.app.ui.component.PictureEditorPane;
import org.pcsoft.app.jimix.app.ui.component.ProjectInfoPane;
import org.pcsoft.app.jimix.app.ui.dialog.EffectManagerDialog;
import org.pcsoft.app.jimix.app.ui.dialog.FilterManagerDialog;
import org.pcsoft.app.jimix.app.ui.dialog.LayerCreateSimpleDialog;
import org.pcsoft.app.jimix.app.ui.splash.JimixSplash;
import org.pcsoft.app.jimix.app.util.FileChooserUtils;
import org.pcsoft.app.jimix.commons.exception.JimixPluginException;
import org.pcsoft.app.jimix.core.project.*;
import org.pcsoft.app.jimix.core.tooling.RecentFileManager;
import org.pcsoft.app.jimix.core.util.FileTypeUtils;
import org.pcsoft.app.jimix.core.util.ImageBuilder;
import org.pcsoft.app.jimix.plugin.manipulation.manager.ManipulationPluginManager;
import org.pcsoft.app.jimix.plugin.manipulation.manager.type.JimixEffectInstance;
import org.pcsoft.app.jimix.plugin.manipulation.manager.type.JimixEffectPlugin;
import org.pcsoft.app.jimix.plugin.manipulation.manager.type.JimixFilterInstance;
import org.pcsoft.app.jimix.plugin.manipulation.manager.type.JimixFilterPlugin;
import org.pcsoft.app.jimix.plugin.system.manager.SystemPluginManager;
import org.pcsoft.app.jimix.plugin.system.manager.type.JimixClipboardProviderInstance;
import org.pcsoft.app.jimix.plugin.system.manager.type.JimixClipboardProviderPlugin;
import org.pcsoft.app.jimix.plugin.system.manager.type.JimixImageFileTypeProviderInstance;
import org.pcsoft.app.jimix.plugin.system.manager.type.JimixProjectFileTypeProviderInstance;
import org.pcsoft.app.jimix.project.JimixProjectModel;
import org.pcsoft.framework.jfex.property.ExtendedWrapperProperty;
import org.pcsoft.framework.jfex.threading.JfxUiThreadPool;
import org.pcsoft.framework.jfex.ui.component.StatusProgressIndicatorPane;
import org.pcsoft.framework.jfex.util.BindingsEx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.List;

public class MainWindowView implements FxmlView<MainWindowViewModel>, Initializable {
    private static final Logger LOGGER = LoggerFactory.getLogger(MainWindowView.class);

    @FXML
    private BorderPane pnlRoot;

    @FXML
    private TabPane tabPicture;

    //<editor-fold desc="Status Bar">
    @FXML
    private ProjectInfoPane projectInfo;
    @FXML
    private StatusProgressIndicatorPane pbState;
    //</editor-fold>

    //<editor-fold desc="Menu">
    @FXML
    private Menu mnuFile;
    @FXML
    private MenuItem miOpen;
    @FXML
    private Menu mnuProjectNew;
    @FXML
    private MenuItem miProjectNewEmpty;
    @FXML
    private MenuItem miProjectNewFromClipboard;
    @FXML
    private MenuItem miProjectNewFromRenderer;
    @FXML
    private Menu mnuOpenRecent;
    @FXML
    private MenuItem miClose;
    @FXML
    private MenuItem miSave;
    @FXML
    private MenuItem miSaveAs;
    @FXML
    private MenuItem miExit;
    @FXML
    private Menu mnuPicture;
    @FXML
    private Menu mnuFilter;
    @FXML
    private MenuItem miFilterManager;
    @FXML
    private Menu mnuLayer;
    @FXML
    private Menu mnuHelp;
    @FXML
    private MenuItem miAbout;
    @FXML
    private Menu mnuView;
    @FXML
    private Menu mnuTheme;
    @FXML
    private CheckMenuItem miShowMask;
    @FXML
    private CheckMenuItem miThemeDefault;
    @FXML
    private CheckMenuItem miThemeDark;
    @FXML
    private Menu mnuEdit;
    @FXML
    private MenuItem miUndo;
    @FXML
    private MenuItem miRedo;
    @FXML
    private MenuItem miCut;
    @FXML
    private MenuItem miCopy;
    @FXML
    private Menu mnuPaste;
    @FXML
    private MenuItem miProjectInformation;
    @FXML
    private MenuItem miProjectTurnLeft;
    @FXML
    private MenuItem miProjectTurnRight;
    @FXML
    private MenuItem miProjectMirrorHorizontal;
    @FXML
    private MenuItem miProjectMirrorVertical;
    @FXML
    private MenuItem miProjectResize;
    @FXML
    private Menu mnuLayerNew;
    @FXML
    private MenuItem miLayerNewSimple;
    @FXML
    private MenuItem miLayerNewFromRenderer;
    @FXML
    private MenuItem miLayerTurnLeft;
    @FXML
    private MenuItem miLayerTurnRight;
    @FXML
    private MenuItem miLayerMirrorHorizontal;
    @FXML
    private MenuItem miLayerMirrorVertical;
    @FXML
    private Menu mnuEffect;
    @FXML
    private MenuItem miEffectManager;
    //</editor-fold>

    //<editor-fold desc="Toolbar">
    @FXML
    private Button btnNewEmpty;
    @FXML
    private Button btnOpen;
    @FXML
    private Button btnClose;
    @FXML
    private Button btnUndo;
    @FXML
    private Button btnRedo;
    @FXML
    private Button btnCut;
    @FXML
    private Button btnCopy;
    @FXML
    private MenuButton btnPaste;
    @FXML
    private Button btnFilterManager;
    @FXML
    private Button btnEffectManager;
    //</editor-fold>

    @InjectViewModel
    private MainWindowViewModel viewModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        final TopLayerSelectedBooleanProperty topLayerSelectedBooleanProperty = new TopLayerSelectedBooleanProperty();
        final ElementSelectedBooleanProperty elementSelectedBooleanProperty = new ElementSelectedBooleanProperty();
        final MenuEmptyBooleanProperty menuEmptyBooleanProperty = new MenuEmptyBooleanProperty();
        final CanUndoBooleanProperty canUndoBooleanProperty = new CanUndoBooleanProperty();
        final CanRedoBooleanProperty canRedoBooleanProperty = new CanRedoBooleanProperty();

        mnuPicture.disableProperty().bind(tabPicture.getSelectionModel().selectedItemProperty().isNull());
        mnuLayer.disableProperty().bind(tabPicture.getSelectionModel().selectedItemProperty().isNull());
        mnuFilter.disableProperty().bind(BindingsEx.not(topLayerSelectedBooleanProperty));
        mnuEffect.disableProperty().bind(BindingsEx.not(elementSelectedBooleanProperty));
        miClose.disableProperty().bind(tabPicture.getSelectionModel().selectedItemProperty().isNull());
        miSave.disableProperty().bind(tabPicture.getSelectionModel().selectedItemProperty().isNull());
        miSaveAs.disableProperty().bind(tabPicture.getSelectionModel().selectedItemProperty().isNull());
        mnuEdit.disableProperty().bind(tabPicture.getSelectionModel().selectedItemProperty().isNull());
        mnuPaste.disableProperty().bind(Bindings.or(BindingsEx.not(topLayerSelectedBooleanProperty), BindingsEx.convert(menuEmptyBooleanProperty)));
        miLayerTurnLeft.disableProperty().bind(BindingsEx.not(topLayerSelectedBooleanProperty));
        miLayerTurnRight.disableProperty().bind(BindingsEx.not(topLayerSelectedBooleanProperty));
        miLayerMirrorHorizontal.disableProperty().bind(BindingsEx.not(topLayerSelectedBooleanProperty));
        miLayerMirrorVertical.disableProperty().bind(BindingsEx.not(topLayerSelectedBooleanProperty));
        miUndo.disableProperty().bind(BindingsEx.not(canUndoBooleanProperty));
        miRedo.disableProperty().bind(BindingsEx.not(canRedoBooleanProperty));

        btnNewEmpty.disableProperty().bind(miProjectNewEmpty.disableProperty().or(mnuFile.disableProperty()));
        btnOpen.disableProperty().bind(miOpen.disableProperty().or(mnuFile.disableProperty()));
        btnClose.disableProperty().bind(miClose.disableProperty().or(mnuFile.disableProperty()));
        btnUndo.disableProperty().bind(miUndo.disableProperty().or(mnuEdit.disableProperty()));
        btnRedo.disableProperty().bind(miRedo.disableProperty().or(mnuEdit.disableProperty()));
        btnCut.disableProperty().bind(miCut.disableProperty().or(mnuEdit.disableProperty()));
        btnCopy.disableProperty().bind(miCopy.disableProperty().or(mnuEdit.disableProperty()));
        btnPaste.disableProperty().bind(mnuPaste.disableProperty().or(mnuEdit.disableProperty()));
        Bindings.bindContent(btnPaste.getItems(), mnuPaste.getItems());
        btnFilterManager.disableProperty().bind(miFilterManager.disableProperty().or(mnuFilter.disableProperty()));
        btnEffectManager.disableProperty().bind(miEffectManager.disableProperty().or(mnuEffect.disableProperty()));

        refreshRecentMenu();
        RecentFileManager.getInstance().addListener(c -> refreshRecentMenu());

        buildFilterMenu();
        buildEffectMenu();
        buildPasteMenu();

        viewModel.getProjectList().addListener((ListChangeListener<JimixProject>) c -> {
            while (c.next()) {
                if (c.wasAdded()) {
                    for (final JimixProject jimixProject : c.getAddedSubList()) {
                        LOGGER.debug("Add project " + jimixProject.getUuid());
                        createTabForProject(jimixProject);
                    }
                }
                if (c.wasRemoved()) {
                    for (final JimixProject jimixProject : c.getRemoved()) {
                        LOGGER.debug("Remove project " + jimixProject.getUuid());
                        removeTabForProject(jimixProject);
                    }
                }
            }
        });

        projectInfo.visibleProperty().bind(tabPicture.getSelectionModel().selectedItemProperty().isNotNull());
        projectInfo.projectProperty().bind(Bindings.createObjectBinding(
                () -> tabPicture.getSelectionModel().getSelectedItem() == null ? null : ((PictureEditorPane) tabPicture.getSelectionModel().getSelectedItem().getContent()).getProject(),
                tabPicture.getSelectionModel().selectedItemProperty()
        ));
    }

    private void buildPasteMenu() {
        for (final JimixClipboardProviderPlugin clipboardProviderPlugin : SystemPluginManager.getInstance().getAllClipboardProviders()) {
            try {
                final JimixClipboardProviderInstance clipboardProviderInstance = clipboardProviderPlugin.createInstance();

                final MenuItem menuItem = new MenuItem(clipboardProviderPlugin.getName());
                if (clipboardProviderPlugin.getIcon() != null) {
                    menuItem.setGraphic(new ImageView(clipboardProviderPlugin.getIcon()));
                }
                menuItem.setUserData(clipboardProviderInstance);
                menuItem.setOnAction(this::onActionPasteFromClipboard);
                Toolkit.getDefaultToolkit().getSystemClipboard().addFlavorListener(e -> Platform.runLater(() -> {
                    final boolean accept = clipboardProviderInstance.acceptClipboardContent(Clipboard.getSystemClipboard());
                    LOGGER.debug("Clipboard content has changed, accept from " + clipboardProviderPlugin.getIdentifier() + ": " + accept);

                    menuItem.setVisible(accept);
                }));
                menuItem.setVisible(clipboardProviderInstance.acceptClipboardContent(Clipboard.getSystemClipboard()));
                mnuPaste.getItems().add(menuItem);
            } catch (JimixPluginException e) {
                LOGGER.error("Unable to create instance from clipboard provider " + clipboardProviderPlugin.getIdentifier() + ", skip", e);
            }
        }
    }

    private void buildFilterMenu() {
        final Map<String, Menu> menuMap = new HashMap<>();

        for (final JimixFilterPlugin filterPlugin : ManipulationPluginManager.getInstance().getAllFilters()) {
            final MenuItem menuItem = new MenuItem(filterPlugin.getName());
            if (filterPlugin.getIcon() != null) {
                menuItem.setGraphic(new ImageView(filterPlugin.getIcon()));
            }
            menuItem.setUserData(filterPlugin);
            menuItem.setOnAction(this::onActionPictureFilter);

            if (filterPlugin.getGroup() == null) {
                mnuFilter.getItems().add(menuItem);
            } else {
                if (!menuMap.containsKey(filterPlugin.getGroup())) {
                    final Menu menu = new Menu(filterPlugin.getGroup());
                    menuMap.put(filterPlugin.getGroup(), menu);
                    mnuFilter.getItems().add(menu);
                }
                menuMap.get(filterPlugin.getGroup()).getItems().add(menuItem);
            }
        }
    }

    private void buildEffectMenu() {
        final Map<String, Menu> menuMap = new HashMap<>();

        for (final JimixEffectPlugin effectPlugin : ManipulationPluginManager.getInstance().getAllEffects()) {
            final MenuItem menuItem = new MenuItem(effectPlugin.getName());
            if (effectPlugin.getIcon() != null) {
                menuItem.setGraphic(new ImageView(effectPlugin.getIcon()));
            }
            menuItem.setUserData(effectPlugin);
            menuItem.setOnAction(this::onActionPictureEffect);

            if (effectPlugin.getGroup() == null) {
                mnuEffect.getItems().add(menuItem);
            } else {
                if (!menuMap.containsKey(effectPlugin.getGroup())) {
                    final Menu menu = new Menu(effectPlugin.getGroup());
                    menuMap.put(effectPlugin.getGroup(), menu);
                    mnuEffect.getItems().add(menu);
                }
                menuMap.get(effectPlugin.getGroup()).getItems().add(menuItem);
            }
        }
    }

    private void refreshRecentMenu() {
        mnuOpenRecent.getItems().clear();
        for (final File file : RecentFileManager.getInstance().getFiles()) {
            final MenuItem menuItem = new MenuItem(file.getName());
            menuItem.setUserData(file);
            menuItem.setOnAction(this::onActionOpenRecent);
            mnuOpenRecent.getItems().add(menuItem);
        }
    }

    //<editor-fold desc="Tab Handling">
    private void createTabForProject(final JimixProject project) {
        final Tab tab = new Tab();
        tab.setUserData(project);
        tab.textProperty().bind(Bindings.createStringBinding(
                () -> project.getFile() != null ? project.getFile().getName() : "<unnamed>",
                project.fileProperty()
        ));
        final PictureEditorPane pictureEditorPane = new PictureEditorPane(project);
        if (!project.getLayerList().isEmpty() && !project.getLayerList().get(0).getPictureElementList().isEmpty()) {
            pictureEditorPane.selectElement(project.getLayerList().get(0).getPictureElementList().get(0));
        } else if (!project.getLayerList().isEmpty()) {
            pictureEditorPane.selectLayer(project.getLayerList().get(0));
        }
        tab.setContent(pictureEditorPane);
        tab.setOnClosed(e -> viewModel.getProjectList().remove(project));

        final Tooltip tooltip = new Tooltip();
        tooltip.textProperty().bind(Bindings.createStringBinding(
                () -> project.getFile() != null ? project.getFile().getAbsolutePath() : null,
                project.fileProperty()
        ));
        tab.setTooltip(tooltip);

        tabPicture.getTabs().add(tab);
        tabPicture.getSelectionModel().select(tab);
    }

    private void removeTabForProject(final JimixProject project) {
        final Tab tabToRemove = tabPicture.getTabs().stream()
                .filter(tab -> tab.getUserData().equals(project))
                .findFirst().orElse(null);
        if (tabToRemove == null)
            return;

        tabPicture.getTabs().remove(tabToRemove);
    }
    //</editor-fold>

    //<editor-fold desc="Menu Action">
    @FXML
    private void onActionOpen(ActionEvent actionEvent) {
        final List<File> files = FileChooserUtils.showOpenPicturesFileChooser();
        if (files == null || files.isEmpty())
            return;

        openPicture(files);
        RecentFileManager.getInstance().addFiles(files);
    }

    @FXML
    private void onActionProjectNewEmpty(ActionEvent actionEvent) {

    }

    @FXML
    private void onActionProjectNewFromClipboard(ActionEvent actionEvent) {

    }

    @FXML
    private void onActionProjectNewFromRenderer(ActionEvent actionEvent) {

    }

    @FXML
    private void onActionClose(ActionEvent actionEvent) {
        final Tab tab = tabPicture.getSelectionModel().getSelectedItem();
        if (tab == null)
            return;

        viewModel.getProjectList().remove(tab.getUserData());
    }

    @FXML
    private void onActionSave(ActionEvent actionEvent) {
        final Tab tab = tabPicture.getSelectionModel().getSelectedItem();
        if (tab == null)
            return;
        final JimixProject project = ((PictureEditorPane) tab.getContent()).getProject();

        if (project.getFile() == null) {
            onActionSaveAs(actionEvent);
        } else {
            savePicture(project, project.getFile());
        }
    }

    @FXML
    private void onActionSaveAs(ActionEvent actionEvent) {
        final Tab tab = tabPicture.getSelectionModel().getSelectedItem();
        if (tab == null)
            return;
        final JimixProject project = ((PictureEditorPane) tab.getContent()).getProject();

        final File file = FileChooserUtils.showSavePictureFileChooser();
        if (file == null)
            return;

        final File oldFile = project.getFile();
        savePicture(project, file);

        if (oldFile != null) {
            RecentFileManager.getInstance().removeFile(oldFile);
        }
        RecentFileManager.getInstance().addFile(file);
    }

    @FXML
    private void onActionExit(ActionEvent actionEvent) {
        ((Stage) pnlRoot.getScene().getWindow()).close();
    }

    @FXML
    private void onActionFilterManager(ActionEvent actionEvent) {
        final Tab tab = tabPicture.getSelectionModel().getSelectedItem();
        if (tab == null)
            return;
        final PictureEditorPane pictureEditorPane = (PictureEditorPane) tab.getContent();
        if (pictureEditorPane.getSelectedTopLayer() == null)
            return;
        final Image image = pictureEditorPane.getProject().getResultImage();

        final Optional<FilterManagerDialog.Result> result = new FilterManagerDialog(pnlRoot.getScene().getWindow(), image).showAndWait();
        if (result.isPresent()) {
            pictureEditorPane.getSelectedTopLayer().getModel().getFilterList().add(result.get().getInstance());
            pictureEditorPane.selectFilter(result.get().getInstance());
        }
    }

    private void onActionPictureFilter(ActionEvent actionEvent) {
        final Tab tab = tabPicture.getSelectionModel().getSelectedItem();
        if (tab == null)
            return;
        final PictureEditorPane pictureEditorPane = (PictureEditorPane) tab.getContent();
        final JimixFilterPlugin filterPlugin = (JimixFilterPlugin) ((MenuItem) actionEvent.getSource()).getUserData();

        try {
            final JimixFilterInstance filterInstance = filterPlugin.createInstance();
            pictureEditorPane.getSelectedTopLayer().getModel().getFilterList().add(filterInstance);
            pictureEditorPane.selectFilter(filterInstance);
        } catch (JimixPluginException e) {
            LOGGER.error("Unable to create filter instance " + filterPlugin.getIdentifier(), e);
            new Alert(Alert.AlertType.ERROR, "Unable to create filter instance: " + e.getMessage(), ButtonType.OK).showAndWait();
        }
    }

    private void onActionPictureEffect(ActionEvent actionEvent) {
        final Tab tab = tabPicture.getSelectionModel().getSelectedItem();
        if (tab == null)
            return;
        final PictureEditorPane pictureEditorPane = (PictureEditorPane) tab.getContent();
        final JimixEffectPlugin effectPlugin = (JimixEffectPlugin) ((MenuItem) actionEvent.getSource()).getUserData();

        try {
            final JimixEffectInstance effectInstance = (JimixEffectInstance) effectPlugin.createInstance();
            ((JimixPictureElement) pictureEditorPane.getSelectedItem()).getModel().getEffectList().add(effectInstance);
            pictureEditorPane.selectEffect(effectInstance);
        } catch (JimixPluginException e) {
            LOGGER.error("Unable to create effect instance " + effectPlugin.getIdentifier(), e);
            new Alert(Alert.AlertType.ERROR, "Unable to create effect instance: " + e.getMessage(), ButtonType.OK).showAndWait();
        }
    }

    private void onActionPasteFromClipboard(ActionEvent actionEvent) {
        final JimixClipboardProviderInstance instance = (JimixClipboardProviderInstance) ((MenuItem) actionEvent.getSource()).getUserData();

        final Tab tab = tabPicture.getSelectionModel().getSelectedItem();
        if (tab == null)
            return;
        final PictureEditorPane pictureEditorPane = (PictureEditorPane) tab.getContent();

        ProjectManager.getInstance().createPictureElementFromClipboardForLayer(pictureEditorPane.getSelectedTopLayer(), instance);
    }

    @FXML
    private void onActionAbout(ActionEvent actionEvent) {
        JimixSplash.show(false);
    }

    @FXML
    private void onActionThemeDefault(ActionEvent actionEvent) {
        miThemeDefault.setSelected(true);
        miThemeDark.setSelected(false);

        pnlRoot.getStylesheets().setAll(
                "/css/default.css"
        );
    }

    @FXML
    private void onActionThemeDark(ActionEvent actionEvent) {
        miThemeDark.setSelected(true);
        miThemeDefault.setSelected(false);

        pnlRoot.getStylesheets().setAll(
                "/css/modena_dark.css"
        );
    }

    @FXML
    private void onActionUndo(ActionEvent actionEvent) {
        final Tab tab = tabPicture.getSelectionModel().getSelectedItem();
        if (tab == null)
            return;
        final PictureEditorPane pictureEditorPane = (PictureEditorPane) tab.getContent();

        pictureEditorPane.undo();
    }

    @FXML
    private void onActionRedo(ActionEvent actionEvent) {
        final Tab tab = tabPicture.getSelectionModel().getSelectedItem();
        if (tab == null)
            return;
        final PictureEditorPane pictureEditorPane = (PictureEditorPane) tab.getContent();

        pictureEditorPane.redo();
    }

    @FXML
    private void onActionCut(ActionEvent actionEvent) {

    }

    @FXML
    private void onActionCopy(ActionEvent actionEvent) {

    }

    private void onActionOpenRecent(ActionEvent actionEvent) {
        final File file = (File) ((MenuItem) actionEvent.getSource()).getUserData();
        openPicture(Collections.singletonList(file));
        RecentFileManager.getInstance().addFile(file);
    }

    @FXML
    private void onActionProjectInformation(ActionEvent actionEvent) {
        final Tab tab = tabPicture.getSelectionModel().getSelectedItem();
        if (tab == null)
            return;
        final JimixProject project = ((PictureEditorPane) tab.getContent()).getProject();


    }

    @FXML
    private void onActionProjectTurnLeft(ActionEvent actionEvent) {
        final Tab tab = tabPicture.getSelectionModel().getSelectedItem();
        if (tab == null)
            return;
        final JimixProject project = ((PictureEditorPane) tab.getContent()).getProject();

        project.turnLeft();
    }

    @FXML
    private void onActionProjectTurnRight(ActionEvent actionEvent) {
        final Tab tab = tabPicture.getSelectionModel().getSelectedItem();
        if (tab == null)
            return;
        final JimixProject project = ((PictureEditorPane) tab.getContent()).getProject();

        project.turnRight();
    }

    @FXML
    private void onActionProjectMirrorHorizontal(ActionEvent actionEvent) {
        final Tab tab = tabPicture.getSelectionModel().getSelectedItem();
        if (tab == null)
            return;
        final JimixProject project = ((PictureEditorPane) tab.getContent()).getProject();

        project.mirrorHorizontal();
    }

    @FXML
    private void onActionProjectMirrorVertical(ActionEvent actionEvent) {
        final Tab tab = tabPicture.getSelectionModel().getSelectedItem();
        if (tab == null)
            return;
        final JimixProject project = ((PictureEditorPane) tab.getContent()).getProject();

        project.mirrorVertical();
    }

    @FXML
    private void onActionProjectResize(ActionEvent actionEvent) {
        final Tab tab = tabPicture.getSelectionModel().getSelectedItem();
        if (tab == null)
            return;
        final JimixProject project = ((PictureEditorPane) tab.getContent()).getProject();


    }

    @FXML
    private void onActionLayerNewSimple(ActionEvent actionEvent) {
        final Tab tab = tabPicture.getSelectionModel().getSelectedItem();
        if (tab == null)
            return;
        final JimixProject project = ((PictureEditorPane) tab.getContent()).getProject();

        final Optional<LayerCreateSimpleDialog.Result> result = new LayerCreateSimpleDialog(pnlRoot.getScene().getWindow()).showAndWait();
        if (result.isPresent()) {
            final JimixLayer layer = ProjectManager.getInstance().createLayerForProject(project);
            layer.getModel().setName(result.get().getName());
            layer.getModel().setBackground(result.get().getPaint());
        }
    }

    @FXML
    private void onActionLayerNewFromRenderer(ActionEvent actionEvent) {

    }

    @FXML
    private void onActionLayerTurnLeft(ActionEvent actionEvent) {
        final Tab tab = tabPicture.getSelectionModel().getSelectedItem();
        if (tab == null)
            return;
        final JimixLayer topLayer = ((PictureEditorPane) tab.getContent()).getSelectedTopLayer();

        topLayer.turnLeft();
    }

    @FXML
    private void onActionLayerTurnRight(ActionEvent actionEvent) {
        final Tab tab = tabPicture.getSelectionModel().getSelectedItem();
        if (tab == null)
            return;
        final JimixLayer topLayer = ((PictureEditorPane) tab.getContent()).getSelectedTopLayer();

        topLayer.turnRight();
    }

    @FXML
    private void onActionLayerMirrorHorizontal(ActionEvent actionEvent) {
        final Tab tab = tabPicture.getSelectionModel().getSelectedItem();
        if (tab == null)
            return;
        final JimixLayer topLayer = ((PictureEditorPane) tab.getContent()).getSelectedTopLayer();

        topLayer.mirrorHorizontal();
    }

    @FXML
    private void onActionLayerMirrorVertical(ActionEvent actionEvent) {
        final Tab tab = tabPicture.getSelectionModel().getSelectedItem();
        if (tab == null)
            return;
        final JimixLayer topLayer = ((PictureEditorPane) tab.getContent()).getSelectedTopLayer();

        topLayer.mirrorVertical();
    }

    @FXML
    private void onActionEffectManager(ActionEvent actionEvent) {
        final Tab tab = tabPicture.getSelectionModel().getSelectedItem();
        if (tab == null)
            return;
        final PictureEditorPane pictureEditorPane = (PictureEditorPane) tab.getContent();
        if (!(pictureEditorPane.getSelectedItem() instanceof JimixElement))
            return;
        final JimixPictureElement element = (JimixPictureElement) pictureEditorPane.getSelectedItem();

        final Image image = ImageBuilder.getInstance().buildPictureElementImage(element);
        final Optional<EffectManagerDialog.Result> result = new EffectManagerDialog(pnlRoot.getScene().getWindow(), image).showAndWait();
        if (result.isPresent()) {
            element.getModel().getEffectList().add(result.get().getInstance());
            pictureEditorPane.selectEffect(result.get().getInstance());
        }
    }

    //</editor-fold>

    //<editor-fold desc="Open / Save Picture">
    private void savePicture(JimixProject project, File file) {
        pbState.show("Save to file...");
        JfxUiThreadPool.submit(() -> {
            try {
                final BufferedImage bufferedImage = SwingFXUtils.fromFXImage(project.getResultImage(), null);
                ImageIO.write(bufferedImage, FilenameUtils.getExtension(file.getAbsolutePath()), file);
                Platform.runLater(() -> project.setFile(file));
            } catch (IOException e) {
                LOGGER.error("Unable to save image", e);
                Platform.runLater(() -> new Alert(Alert.AlertType.ERROR, "Unable to save image: " + e.getMessage(), ButtonType.OK).showAndWait());
            } finally {
                Platform.runLater(() -> pbState.hide());
            }
        });
    }

    private void openPicture(List<File> files) {
        pbState.show("Open files...");
        JfxUiThreadPool.submit(() -> {
            try {
                for (final File file : files) {
                    final Tab pictureTab = tabPicture.getTabs().stream()
                            .filter(tab -> ((PictureEditorPane) tab.getContent()).getProject().getFile().equals(file))
                            .findFirst().orElse(null);
                    if (pictureTab != null) {
                        tabPicture.getSelectionModel().select(pictureTab);
                        continue;
                    }

                    try {
                        final JimixImageFileTypeProviderInstance imageFileTypeProvider = FileTypeUtils.findImageSupport(file);
                        if (imageFileTypeProvider == null) {
                            final JimixProjectFileTypeProviderInstance projectFileTypeProvider = FileTypeUtils.findProjectSupport(file);
                            if (projectFileTypeProvider == null) {
                                LOGGER.error("Unable to find any file type provider to open file " + file);
                                Platform.runLater(() -> new Alert(Alert.AlertType.ERROR, "Unable to find provider to open file", ButtonType.OK).showAndWait());
                                continue;
                            }

                            final JimixProjectModel projectModel = projectFileTypeProvider.load(file);
                            final JimixProject jimixProject = ProjectManager.getInstance().createProjectNative(projectModel);
                            jimixProject.setFile(file);
                            Platform.runLater(() -> viewModel.getProjectList().add(jimixProject));
                            continue;
                        }

                        final Image image = imageFileTypeProvider.load(file);
                        Platform.runLater(() -> {
                            final JimixProject jimixProject = ProjectManager.getInstance().createProjectFromImage(image);
                            jimixProject.setFile(file);
                            viewModel.getProjectList().add(jimixProject);
                        });
                    } catch (IOException e) {
                        LOGGER.error("Unable to open file " + file.getAbsolutePath(), e);
                        Platform.runLater(() -> new Alert(Alert.AlertType.ERROR, "Unable to load file " + file.getAbsolutePath(), ButtonType.OK).showAndWait());
                    }
                }
            } finally {
                Platform.runLater(() -> pbState.hide());
            }
        });
    }

    @FXML
    private void onActionShowMask(ActionEvent actionEvent) {
        final Tab tab = tabPicture.getSelectionModel().getSelectedItem();
        if (tab == null)
            return;
        final PictureEditorPane pictureEditorPane = (PictureEditorPane) tab.getContent();
        pictureEditorPane.setShowMask(miShowMask.isSelected());
    }
    //</editor-fold>

    //<editor-fold desc="Local Helper Properties">
    private final class ElementSelectedBooleanProperty extends ExtendedWrapperProperty<Boolean> {
        public ElementSelectedBooleanProperty() {
            super(tabPicture.getSelectionModel().selectedItemProperty());

            tabPicture.getSelectionModel().selectedItemProperty().addListener((v, o, n) -> {
                if (o != null) {
                    ((PictureEditorPane) o.getContent()).selectedItemProperty().removeListener(this::invalidated);
                }
                if (n != null) {
                    ((PictureEditorPane) n.getContent()).selectedItemProperty().addListener(this::invalidated);
                }
            });
        }

        @Override
        protected Boolean getPseudoValue() {
            return tabPicture.getSelectionModel().getSelectedItem() != null &&
                    ((PictureEditorPane) tabPicture.getSelectionModel().getSelectedItem().getContent()).getSelectedItem() instanceof JimixElement;
        }

        @Override
        protected void setPseudoValue(Boolean value) {
            throw new RuntimeException("Not Supported");
        }

        private void invalidated(Observable obs) {
            fireValueChangedEvent();
        }
    }

    private final class TopLayerSelectedBooleanProperty extends ExtendedWrapperProperty<Boolean> {

        private TopLayerSelectedBooleanProperty() {
            super(tabPicture.getSelectionModel().selectedItemProperty());

            tabPicture.getSelectionModel().selectedItemProperty().addListener((v, o, n) -> {
                if (o != null) {
                    ((PictureEditorPane) o.getContent()).selectedTopLayerProperty().removeListener(this::invalidated);
                }
                if (n != null) {
                    ((PictureEditorPane) n.getContent()).selectedTopLayerProperty().addListener(this::invalidated);
                }
            });
        }

        @Override
        protected Boolean getPseudoValue() {
            return tabPicture.getSelectionModel().getSelectedItem() != null &&
                    ((PictureEditorPane) tabPicture.getSelectionModel().getSelectedItem().getContent()).getSelectedTopLayer() != null;
        }

        @Override
        protected void setPseudoValue(Boolean value) {
            throw new RuntimeException("Not Supported");
        }

        private void invalidated(Observable obs) {
            fireValueChangedEvent();
        }

    }

    private final class MenuEmptyBooleanProperty extends ExtendedWrapperProperty<Boolean> {

        private MenuEmptyBooleanProperty() {
            super(mnuPaste.getItems());

            mnuPaste.getItems().addListener((ListChangeListener<MenuItem>) c -> {
                while (c.next()) {
                    if (c.wasAdded()) {
                        for (final MenuItem menuItem : c.getAddedSubList()) {
                            menuItem.visibleProperty().addListener(this::invalidated);
                        }
                    }
                    if (c.wasRemoved()) {
                        for (final MenuItem menuItem : c.getRemoved()) {
                            menuItem.visibleProperty().removeListener(this::invalidated);
                        }
                    }
                }
            });
        }

        @Override
        protected Boolean getPseudoValue() {
            return mnuPaste.getItems().isEmpty() || mnuPaste.getItems().stream().noneMatch(MenuItem::isVisible);
        }

        @Override
        protected void setPseudoValue(Boolean value) {
            throw new RuntimeException("Not Supported");
        }

        private void invalidated(Observable obs) {
            fireValueChangedEvent();
        }

    }

    private final class CanUndoBooleanProperty extends ExtendedWrapperProperty<Boolean> {

        private CanUndoBooleanProperty() {
            super(tabPicture.getSelectionModel().selectedItemProperty());

            tabPicture.getSelectionModel().selectedItemProperty().addListener((v, o, n) -> {
                if (o != null) {
                    ((PictureEditorPane) o.getContent()).canUndoProperty().removeListener(this::invalidated);
                }
                if (n != null) {
                    ((PictureEditorPane) n.getContent()).canUndoProperty().addListener(this::invalidated);
                }
            });
        }

        @Override
        protected Boolean getPseudoValue() {
            return tabPicture.getSelectionModel().getSelectedItem() != null &&
                    ((PictureEditorPane) tabPicture.getSelectionModel().getSelectedItem().getContent()).canUndo();
        }

        @Override
        protected void setPseudoValue(Boolean value) {
            throw new RuntimeException("Not Supported");
        }

        private void invalidated(Observable obs) {
            fireValueChangedEvent();
        }

    }

    private final class CanRedoBooleanProperty extends ExtendedWrapperProperty<Boolean> {

        private CanRedoBooleanProperty() {
            super(tabPicture.getSelectionModel().selectedItemProperty());

            tabPicture.getSelectionModel().selectedItemProperty().addListener((v, o, n) -> {
                if (o != null) {
                    ((PictureEditorPane) o.getContent()).canRedoProperty().removeListener(this::invalidated);
                }
                if (n != null) {
                    ((PictureEditorPane) n.getContent()).canRedoProperty().addListener(this::invalidated);
                }
            });
        }

        @Override
        protected Boolean getPseudoValue() {
            return tabPicture.getSelectionModel().getSelectedItem() != null &&
                    ((PictureEditorPane) tabPicture.getSelectionModel().getSelectedItem().getContent()).canRedo();
        }

        @Override
        protected void setPseudoValue(Boolean value) {
            throw new RuntimeException("Not Supported");
        }

        private void invalidated(Observable obs) {
            fireValueChangedEvent();
        }

    }
    //</editor-fold>
}

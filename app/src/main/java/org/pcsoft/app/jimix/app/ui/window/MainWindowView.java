package org.pcsoft.app.jimix.app.ui.window;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.pcsoft.app.jimix.app.ui.component.PictureEditorPane;
import org.pcsoft.app.jimix.app.ui.splash.JimixSplash;
import org.pcsoft.app.jimix.app.util.FileChooserUtils;
import org.pcsoft.app.jimix.commons.exception.JimixProjectException;
import org.pcsoft.app.jimix.core.plugin.PluginManager;
import org.pcsoft.app.jimix.core.plugin.type.JimixClipboardProviderInstance;
import org.pcsoft.app.jimix.core.plugin.type.JimixFilterInstance;
import org.pcsoft.app.jimix.core.project.JimixProject;
import org.pcsoft.app.jimix.core.project.ProjectManager;
import org.pcsoft.framework.jfex.property.ExtendedWrapperProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MainWindowView implements FxmlView<MainWindowViewModel>, Initializable {
    private static final Logger LOGGER = LoggerFactory.getLogger(MainWindowView.class);

    @FXML
    private BorderPane pnlRoot;

    @FXML
    private TabPane tabPicture;

    //<editor-fold desc="Menu">
    @FXML
    private Menu mnuFile;
    @FXML
    private MenuItem miOpen;
    @FXML
    private Menu mnuNew;
    @FXML
    private MenuItem miNewEmpty;
    @FXML
    private MenuItem miNewFromClipboard;
    @FXML
    private MenuItem miNewFromRenderer;
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
    private Button btnDefaultPaste;
    //</editor-fold>

    @InjectViewModel
    private MainWindowViewModel viewModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mnuPicture.disableProperty().bind(tabPicture.getSelectionModel().selectedItemProperty().isNull());
        mnuLayer.disableProperty().bind(tabPicture.getSelectionModel().selectedItemProperty().isNull());
        mnuFilter.disableProperty().bind(new TopLayerBooleanProperty());
        miClose.disableProperty().bind(tabPicture.getSelectionModel().selectedItemProperty().isNull());
        miSave.disableProperty().bind(tabPicture.getSelectionModel().selectedItemProperty().isNull());
        miSaveAs.disableProperty().bind(tabPicture.getSelectionModel().selectedItemProperty().isNull());
        mnuEdit.disableProperty().bind(tabPicture.getSelectionModel().selectedItemProperty().isNull());
        mnuPaste.disableProperty().bind(new TopLayerBooleanProperty().or(new MenuEmptyBooleanProperty()));

        btnNewEmpty.disableProperty().bind(miNewEmpty.disableProperty().or(mnuFile.disableProperty()));
        btnOpen.disableProperty().bind(miOpen.disableProperty().or(mnuFile.disableProperty()));
        btnClose.disableProperty().bind(miClose.disableProperty().or(mnuFile.disableProperty()));
        btnUndo.disableProperty().bind(miUndo.disableProperty().or(mnuEdit.disableProperty()));
        btnRedo.disableProperty().bind(miRedo.disableProperty().or(mnuEdit.disableProperty()));
        btnCut.disableProperty().bind(miCut.disableProperty().or(mnuEdit.disableProperty()));
        btnCopy.disableProperty().bind(miCopy.disableProperty().or(mnuEdit.disableProperty()));
        btnDefaultPaste.disableProperty().bind(mnuPaste.disableProperty().or(mnuEdit.disableProperty()));

        for (final JimixFilterInstance filterInstance : PluginManager.getInstance().getAllFilters()) {
            final MenuItem menuItem = new MenuItem(filterInstance.getName());
            if (filterInstance.getIcon() != null) {
                menuItem.setGraphic(new ImageView(filterInstance.getIcon()));
            }
            menuItem.setUserData(filterInstance);
            menuItem.setOnAction(this::onActionPictureFilter);
            mnuFilter.getItems().add(menuItem);
        }

        for (final JimixClipboardProviderInstance clipboardProviderInstance : PluginManager.getInstance().getAllClipboardProviders()) {
            final MenuItem menuItem = new MenuItem(clipboardProviderInstance.getName());
            if (clipboardProviderInstance.getIcon() != null) {
                menuItem.setGraphic(new ImageView(clipboardProviderInstance.getIcon()));
            }
            menuItem.setUserData(clipboardProviderInstance);
            menuItem.setOnAction(this::onActionPasteFromClipboard);
            Toolkit.getDefaultToolkit().getSystemClipboard().addFlavorListener(e -> Platform.runLater(() -> {
                final boolean accept = clipboardProviderInstance.acceptClipboardContent(Clipboard.getSystemClipboard());
                LOGGER.debug("Clipboard content has changed, accept from " + clipboardProviderInstance.getIdentifier() + ": " + accept);

                menuItem.setVisible(accept);
            }));
            menuItem.setVisible(clipboardProviderInstance.acceptClipboardContent(Clipboard.getSystemClipboard()));
            mnuPaste.getItems().add(menuItem);
        }

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
    }

    private void createTabForProject(final JimixProject project) {
        final Tab tab = new Tab();
        tab.setUserData(project);
        tab.textProperty().bind(Bindings.createStringBinding(
                () -> project.getModel().getFile() != null ? project.getModel().getFile().getName() : "<unnamed>",
                project.getModel().fileProperty()
        ));
        tab.setContent(new PictureEditorPane(project));
        tab.setOnClosed(e -> viewModel.getProjectList().remove(project));

        final Tooltip tooltip = new Tooltip();
        tooltip.textProperty().bind(Bindings.createStringBinding(
                () -> project.getModel().getFile() != null ? project.getModel().getFile().getAbsolutePath() : null,
                project.getModel().fileProperty()
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

    //<editor-fold desc="Menu Action">
    @FXML
    private void onActionOpen(ActionEvent actionEvent) {
        final List<File> files = FileChooserUtils.showOpenPictureFileChooser();
        if (files == null || files.isEmpty())
            return;

        for (final File file : files) {
            try {
                final JimixProject jimixProject = ProjectManager.getInstance().createProjectFromFile(file);
                viewModel.getProjectList().add(jimixProject);
            } catch (JimixProjectException e) {
                LOGGER.error("Unable to open file " + file.getAbsolutePath(), e);
                new Alert(Alert.AlertType.ERROR, "Unable to load file " + file.getAbsolutePath(), ButtonType.OK).showAndWait();
            }
        }
    }

    @FXML
    private void onActionNewEmpty(ActionEvent actionEvent) {

    }

    @FXML
    private void onActionFromClipboard(ActionEvent actionEvent) {

    }

    @FXML
    private void onActionFromRenderer(ActionEvent actionEvent) {

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

    }

    @FXML
    private void onActionSaveAs(ActionEvent actionEvent) {

    }

    @FXML
    private void onActionExit(ActionEvent actionEvent) {
        ((Stage) pnlRoot.getScene().getWindow()).close();
    }

    @FXML
    private void onActionFilterManager(ActionEvent actionEvent) {

    }

    @FXML
    private void onActionPictureRendererManager(ActionEvent actionEvent) {

    }

    private void onActionPictureFilter(ActionEvent actionEvent) {
        final Tab tab = tabPicture.getSelectionModel().getSelectedItem();
        if (tab == null)
            return;
        final PictureEditorPane pictureEditorPane = (PictureEditorPane) tab.getContent();
        final JimixFilterInstance filterInstance = (JimixFilterInstance) ((MenuItem) actionEvent.getSource()).getUserData();

        pictureEditorPane.getSelectedTopLayer().getModel().getFilterList().add(filterInstance.getIdentifier());
    }

    private void onActionPasteFromClipboard(ActionEvent actionEvent) {
        final JimixClipboardProviderInstance instance = (JimixClipboardProviderInstance) ((MenuItem) actionEvent.getSource()).getUserData();

        final Tab tab = tabPicture.getSelectionModel().getSelectedItem();
        if (tab == null)
            return;
        final PictureEditorPane pictureEditorPane = (PictureEditorPane) tab.getContent();

        ProjectManager.getInstance().createElementFromClipboardForLayer(pictureEditorPane.getSelectedTopLayer(), instance);
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

    }

    @FXML
    private void onActionRedo(ActionEvent actionEvent) {

    }

    @FXML
    private void onActionCut(ActionEvent actionEvent) {

    }

    @FXML
    private void onActionCopy(ActionEvent actionEvent) {

    }

    @FXML
    private void onActionDefaultPaste(ActionEvent actionEvent) {
        if (mnuPaste.getItems().isEmpty())
            return;

        final MenuItem defaultMenuItem = mnuPaste.getItems().stream()
                .filter(MenuItem::isVisible)
                .findFirst().orElse(null);
        if (defaultMenuItem == null)
            return;

        defaultMenuItem.getOnAction().handle(new ActionEvent(defaultMenuItem, Event.NULL_SOURCE_TARGET));
    }
    //</editor-fold>

    //<editor-fold desc="Local Helper Properties">
    private final class TopLayerBooleanProperty extends ExtendedWrapperProperty<Boolean> {
        private TopLayerBooleanProperty() {
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

        public BooleanBinding or(ObservableValue<Boolean> value) {
            return Bindings.createBooleanBinding(() -> getValue() || value.getValue(), this, value);
        }

        @Override
        protected Boolean getPseudoValue() {
            return tabPicture.getSelectionModel().getSelectedItem() == null ||
                    ((PictureEditorPane) tabPicture.getSelectionModel().getSelectedItem().getContent()).getSelectedTopLayer() == null;
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

        public BooleanBinding or(ObservableValue<Boolean> value) {
            return Bindings.createBooleanBinding(() -> getValue() || value.getValue(), this, value);
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
    //</editor-fold>
}

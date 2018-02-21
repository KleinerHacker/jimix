package org.pcsoft.app.jimix.app.ui.window;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.beans.binding.Bindings;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.pcsoft.app.jimix.app.ui.component.PictureEditorPane;
import org.pcsoft.app.jimix.app.ui.splash.JimixSplash;
import org.pcsoft.app.jimix.app.util.FileChooserUtils;
import org.pcsoft.app.jimix.commons.exception.JimixProjectException;
import org.pcsoft.app.jimix.core.plugin.PluginManager;
import org.pcsoft.app.jimix.core.plugin.type.JimixFilterInstance;
import org.pcsoft.app.jimix.core.plugin.type.JimixRendererInstance;
import org.pcsoft.app.jimix.core.project.JimixProject;
import org.pcsoft.app.jimix.core.project.ProjectManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    //</editor-fold>

    @InjectViewModel
    private MainWindowViewModel viewModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mnuPicture.disableProperty().bind(tabPicture.getSelectionModel().selectedItemProperty().isNull());
        mnuLayer.disableProperty().bind(tabPicture.getSelectionModel().selectedItemProperty().isNull());
        mnuFilter.disableProperty().bind(tabPicture.getSelectionModel().selectedItemProperty().isNull());
        miClose.disableProperty().bind(tabPicture.getSelectionModel().selectedItemProperty().isNull());
        miSave.disableProperty().bind(tabPicture.getSelectionModel().selectedItemProperty().isNull());
        miSaveAs.disableProperty().bind(tabPicture.getSelectionModel().selectedItemProperty().isNull());

        for (final JimixFilterInstance filterInstance : PluginManager.getInstance().getAllFilters()) {
            final MenuItem menuItem = new MenuItem(filterInstance.getName());
            if (filterInstance.getIcon() != null) {
                menuItem.setGraphic(new ImageView(filterInstance.getIcon()));
            }
            menuItem.setUserData(filterInstance);
            menuItem.setOnAction(this::onActionPictureFilter);
            mnuFilter.getItems().add(menuItem);
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
        final List<File> files = FileChooserUtils.showOpenPictureFileChooser(pnlRoot.getScene().getWindow());
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

    private void onActionPictureRenderer(ActionEvent actionEvent) {
        final JimixRendererInstance rendererInstance = (JimixRendererInstance) ((MenuItem) actionEvent.getSource()).getUserData();

        //TODO
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
    //</editor-fold>
}

package org.pcsoft.app.jimix.app.ui.window;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.beans.binding.Bindings;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.pcsoft.app.jimix.app.ui.component.PictureEditorPane;
import org.pcsoft.app.jimix.app.util.FileChooserUtils;
import org.pcsoft.app.jimix.commons.exception.JimixProjectException;
import org.pcsoft.app.jimix.core.project.JimixProject;
import org.pcsoft.app.jimix.core.project.ProjectManager;
import org.pcsoft.app.jimix.core.plugin.PluginManager;
import org.pcsoft.app.jimix.core.plugin.type.JimixEffectInstance;
import org.pcsoft.app.jimix.core.plugin.type.JimixRendererInstance;
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
    private Menu mnuPictureEffect;
    @FXML
    private MenuItem miPictureEffectManager;
    @FXML
    private Menu mnuPictureRenderer;
    @FXML
    private MenuItem miPictureRendererManager;
    //</editor-fold>

    @InjectViewModel
    private MainWindowViewModel viewModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mnuPicture.disableProperty().bind(tabPicture.getSelectionModel().selectedItemProperty().isNull());
        miClose.disableProperty().bind(tabPicture.getSelectionModel().selectedItemProperty().isNull());
        miSave.disableProperty().bind(tabPicture.getSelectionModel().selectedItemProperty().isNull());
        miSaveAs.disableProperty().bind(tabPicture.getSelectionModel().selectedItemProperty().isNull());

        for (final JimixEffectInstance effectHolder : PluginManager.getInstance().getAllEffects()) {
            final MenuItem menuItem = new MenuItem(effectHolder.getName());
            menuItem.setUserData(effectHolder);
            menuItem.setOnAction(this::onActionPictureEffect);
            mnuPictureEffect.getItems().add(menuItem);
        }

        for (final JimixRendererInstance rendererHolder : PluginManager.getInstance().getAllRenderers()) {
            final MenuItem menuItem = new MenuItem(rendererHolder.getName());
            menuItem.setUserData(rendererHolder);
            menuItem.setOnAction(this::onActionPictureRenderer);
            mnuPictureRenderer.getItems().add(menuItem);
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

        tabPicture.getTabs().add(tab);
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
    private void onActionPictureEffectManager(ActionEvent actionEvent) {

    }

    @FXML
    private void onActionPictureRendererManager(ActionEvent actionEvent) {

    }

    private void onActionPictureEffect(ActionEvent actionEvent) {
        final Tab tab = tabPicture.getSelectionModel().getSelectedItem();
        if (tab == null)
            return;
        final PictureEditorPane pictureEditorPane = (PictureEditorPane) tab.getContent();
        final JimixEffectInstance effectHolder = (JimixEffectInstance) ((MenuItem) actionEvent.getSource()).getUserData();

        //TODO
    }

    private void onActionPictureRenderer(ActionEvent actionEvent) {
        final JimixRendererInstance rendererHolder = (JimixRendererInstance) ((MenuItem) actionEvent.getSource()).getUserData();
    }
    //</editor-fold>
}

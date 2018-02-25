package org.pcsoft.app.jimix.app.ui.window;

import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.ViewTuple;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.pcsoft.app.jimix.app.language.LanguageResources;
import org.pcsoft.app.jimix.core.plugin.type.JimixFileTypeProviderInstance;
import org.pcsoft.app.jimix.core.project.JimixProject;
import org.pcsoft.app.jimix.core.project.ProjectManager;
import org.pcsoft.app.jimix.core.tooling.RecentFileManager;
import org.pcsoft.app.jimix.core.util.FileTypeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

public class MainWindow extends Stage {
    private static final Logger LOGGER = LoggerFactory.getLogger(MainWindow.class);

    private final MainWindowView controller;
    private final MainWindowViewModel viewModel;

    public MainWindow(final File file) {
        setTitle(LanguageResources.getText("app.title"));

        final ViewTuple<MainWindowView, MainWindowViewModel> viewTuple =
                FluentViewLoader.fxmlView(MainWindowView.class).resourceBundle(LanguageResources.getBundle()).load();
        controller = viewTuple.getCodeBehind();
        viewModel = viewTuple.getViewModel();

        setScene(new Scene(viewTuple.getView()));

        if (file != null && (!file.exists() || file.isDirectory())) {
            LOGGER.error("Unable to find file " + file.getAbsolutePath());
            new Alert(Alert.AlertType.ERROR, "Unable to find file " + file.getAbsolutePath(), ButtonType.OK).showAndWait();
            Platform.exit();
            System.exit(-1);
        }
        if (file != null) {
            try {
                final JimixFileTypeProviderInstance fileTypeProvider = FileTypeUtils.find(file);
                if (fileTypeProvider == null) {
                    LOGGER.error("Unable to find any file type provider to open file " + file);
                    Platform.runLater(() -> new Alert(Alert.AlertType.ERROR, "Unable to find provider to open file", ButtonType.OK).showAndWait());
                    return;
                }
                final Image image = fileTypeProvider.load(file);
                final JimixProject jimixProject = ProjectManager.getInstance().createProjectFromImage(image);
                jimixProject.setFile(file);
                Platform.runLater(() -> {
                    viewModel.getProjectList().add(jimixProject);
                    RecentFileManager.getInstance().addFile(file);
                });
            } catch (IOException e) {
                LOGGER.error("Unable to open file " + file.getAbsolutePath(), e);
                Platform.runLater(() -> new Alert(Alert.AlertType.ERROR, "Unable to load file " + file.getAbsolutePath(), ButtonType.OK).showAndWait());
            }
        }
    }
}

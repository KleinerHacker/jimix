package org.pcsoft.app.jimix.app.ui.window;

import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.ViewTuple;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.pcsoft.app.jimix.app.language.LanguageResources;

public class MainWindow extends Stage {

    private final MainWindowView controller;
    private final MainWindowViewModel viewModel;

    public MainWindow() {
        setTitle(LanguageResources.getText("app.title"));

        final ViewTuple<MainWindowView, MainWindowViewModel> viewTuple =
                FluentViewLoader.fxmlView(MainWindowView.class).resourceBundle(LanguageResources.getBundle()).load();
        controller = viewTuple.getCodeBehind();
        viewModel = viewTuple.getViewModel();

        setScene(new Scene(viewTuple.getView()));
    }
}

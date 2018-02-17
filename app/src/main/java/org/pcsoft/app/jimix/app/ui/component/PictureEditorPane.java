package org.pcsoft.app.jimix.app.ui.component;

import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.ViewTuple;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.scene.layout.BorderPane;
import org.pcsoft.app.jimix.app.language.LanguageResources;
import org.pcsoft.app.jimix.core.project.JimixProject;
import org.pcsoft.app.jimix.core.project.JimixProjectModel;
import org.pcsoft.app.jimix.core.plugin.type.JimixEffectInstance;

public class PictureEditorPane extends BorderPane {

    private final PictureEditorPaneView controller;
    private final PictureEditorPaneViewModel viewModel;

    private final ReadOnlyObjectProperty<JimixProject> project;

    public PictureEditorPane(final JimixProject project) {
        final ViewTuple<PictureEditorPaneView, PictureEditorPaneViewModel> viewTuple =
                FluentViewLoader.fxmlView(PictureEditorPaneView.class).resourceBundle(LanguageResources.getBundle()).root(this).load();
        controller = viewTuple.getCodeBehind();
        viewModel = viewTuple.getViewModel();

        this.project = new ReadOnlyObjectWrapper<>(project).getReadOnlyProperty();
        viewTuple.getViewModel().levelListProperty().bindContent(project.levelListProperty());
        viewTuple.getViewModel().resultPictureProperty().bind(project.resultImageProperty());
    }

    public JimixProject getProject() {
        return project.get();
    }

    public ReadOnlyObjectProperty<JimixProject> projectProperty() {
        return project;
    }
}

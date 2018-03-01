package org.pcsoft.app.jimix.app.ui.component;

import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.ViewTuple;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.layout.HBox;
import org.pcsoft.app.jimix.app.language.LanguageResources;
import org.pcsoft.app.jimix.core.project.JimixProject;

public class ProjectInfoPane extends HBox {
    private final ProjectInfoPaneView controller;
    private final ProjectInfoPaneViewModel viewModel;

    private final ObjectProperty<JimixProject> project = new SimpleObjectProperty<>();

    public ProjectInfoPane() {
        final ViewTuple<ProjectInfoPaneView, ProjectInfoPaneViewModel> viewTuple =
                FluentViewLoader.fxmlView(ProjectInfoPaneView.class).resourceBundle(LanguageResources.getBundle()).root(this).load();
        controller = viewTuple.getCodeBehind();
        viewModel = viewTuple.getViewModel();

        project.addListener((v, o, n) -> {
            if (n == null)
                return;

            viewModel.setDimension(n.getModel().getWidth() + "x" + n.getModel().getHeight());
        });
    }

    public JimixProject getProject() {
        return project.get();
    }

    public ObjectProperty<JimixProject> projectProperty() {
        return project;
    }

    public void setProject(JimixProject project) {
        this.project.set(project);
    }
}

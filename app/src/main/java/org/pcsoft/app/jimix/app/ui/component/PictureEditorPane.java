package org.pcsoft.app.jimix.app.ui.component;

import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.ViewTuple;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.scene.layout.BorderPane;
import org.pcsoft.app.jimix.app.language.LanguageResources;
import org.pcsoft.app.jimix.core.project.JimixLayer;
import org.pcsoft.app.jimix.core.project.JimixProject;

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
        viewTuple.getViewModel().layerListProperty().bind(project.layerListProperty());
        viewTuple.getViewModel().resultPictureProperty().bind(project.resultImageProperty());
    }

    public JimixProject getProject() {
        return project.get();
    }

    public ReadOnlyObjectProperty<JimixProject> projectProperty() {
        return project;
    }

    public JimixLayer getSelectedLayer() {
        return viewModel.getSelectedLayer();
    }

    public ObjectProperty<JimixLayer> selectedLayerProperty() {
        return viewModel.selectedLayerProperty();
    }

    public void setSelectedLayer(JimixLayer selectedLayer) {
        viewModel.setSelectedLayer(selectedLayer);
    }
}

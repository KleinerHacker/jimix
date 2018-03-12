package org.pcsoft.app.jimix.app.ui.component;

import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.ViewTuple;
import javafx.beans.Observable;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.layout.BorderPane;
import org.pcsoft.app.jimix.app.language.LanguageResources;
import org.pcsoft.app.jimix.core.project.JimixElement;
import org.pcsoft.app.jimix.core.project.JimixLayer;
import org.pcsoft.app.jimix.core.project.JimixProject;
import org.pcsoft.app.jimix.core.project.ProjectManager;
import org.pcsoft.app.jimix.core.tooling.UndoRedoManager;
import org.pcsoft.app.jimix.plugin.manipulation.manager.type.JimixEffectInstance;
import org.pcsoft.app.jimix.plugin.manipulation.manager.type.JimixFilterInstance;
import org.pcsoft.app.jimix.project.JimixProjectModel;

public class PictureEditorPane extends BorderPane {

    private final PictureEditorPaneView controller;
    private final PictureEditorPaneViewModel viewModel;

    private final ObjectProperty<JimixProject> project = new SimpleObjectProperty<>();
    private final UndoRedoManager undoRedoManager = new UndoRedoManager(new UndoRedoHandler());

    public PictureEditorPane() {
        final ViewTuple<PictureEditorPaneView, PictureEditorPaneViewModel> viewTuple =
                FluentViewLoader.fxmlView(PictureEditorPaneView.class).resourceBundle(LanguageResources.getBundle()).root(this).load();
        controller = viewTuple.getCodeBehind();
        viewModel = viewTuple.getViewModel();

        project.addListener((v, o, n) -> {
            if (o != null) {
                viewTuple.getViewModel().layerListProperty().unbind();
                viewTuple.getViewModel().resultPictureProperty().unbind();
                o.resultImageProperty().removeListener(this::projectImageChanged);
            }
            if (n != null) {
                viewTuple.getViewModel().layerListProperty().bind(n.layerListProperty());
                viewTuple.getViewModel().resultPictureProperty().bind(n.resultImageProperty());
                n.resultImageProperty().addListener(this::projectImageChanged);
            }
        });
    }

    public PictureEditorPane(final JimixProject project) {
        this();
        setProject(project);
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

    public JimixLayer getSelectedTopLayer() {
        return viewModel.getSelectedTopLayer();
    }

    public ObjectProperty<JimixLayer> selectedTopLayerProperty() {
        return viewModel.selectedTopLayerProperty();
    }

    public void setSelectedTopLayer(JimixLayer selectedTopLayer) {
        viewModel.setSelectedTopLayer(selectedTopLayer);
    }

    public Object getSelectedItem() {
        return viewModel.getSelectedItem();
    }

    public ObjectProperty<Object> selectedItemProperty() {
        return viewModel.selectedItemProperty();
    }

    public void setSelectedItem(Object selectedItem) {
        viewModel.setSelectedItem(selectedItem);
    }

    public void selectEffect(JimixEffectInstance instance) {
        controller.selectEffect(instance);
    }

    public void selectFilter(JimixFilterInstance instance) {
        controller.selectFilter(instance);
    }

    public void selectElement(JimixElement element) {
        controller.selectElement(element);
    }

    public void selectLayer(JimixLayer layer) {
        controller.selectLayer(layer);
    }

    public boolean canUndo() {
        return undoRedoManager.canUndo();
    }

    public BooleanBinding canUndoProperty() {
        return undoRedoManager.canUndoProperty();
    }

    public boolean canRedo() {
        return undoRedoManager.canRedo();
    }

    public BooleanBinding canRedoProperty() {
        return undoRedoManager.canRedoProperty();
    }

    public void undo() {
        undoRedoManager.undo();
    }

    public void redo() {
        undoRedoManager.redo();
    }

    private void projectImageChanged(Observable obs) {
        undoRedoManager.addChangeable(project.get().getModel().copy());
    }

    private final class UndoRedoHandler implements UndoRedoManager.UndoRedoListener {
        @Override
        public void onUndo(Object changeable) {
            if (changeable instanceof JimixProjectModel) {
                project.set(ProjectManager.getInstance().createProjectNative((JimixProjectModel) changeable, false));
            }
        }

        @Override
        public void onRedo(Object changeable) {
            if (changeable instanceof JimixProjectModel) {
                project.set(ProjectManager.getInstance().createProjectNative((JimixProjectModel) changeable, false));
            }
        }
    }
}

package org.pcsoft.app.jimix.app.ui.component;

import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.ViewTuple;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.collections.ObservableList;
import javafx.scene.layout.HBox;
import org.pcsoft.app.jimix.app.language.LanguageResources;
import org.pcsoft.app.jimix.core.project.JimixLayer;

public class LayerList extends HBox {
    private final LayerListView controller;
    private final LayerListViewModel viewModel;

    public LayerList() {
        final ViewTuple<LayerListView, LayerListViewModel> viewTuple =
                FluentViewLoader.fxmlView(LayerListView.class).resourceBundle(LanguageResources.getBundle()).root(this).load();
        controller = viewTuple.getCodeBehind();
        viewModel = viewTuple.getViewModel();
    }

    public ObservableList<JimixLayer> getLayerList() {
        return viewModel.getLayerList();
    }

    public ListProperty<JimixLayer> layerListProperty() {
        return viewModel.layerListProperty();
    }

    public void setLayerList(ObservableList<JimixLayer> layerList) {
        viewModel.setLayerList(layerList);
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
package org.pcsoft.app.jimix.app.ui.component;

import de.saxsys.mvvmfx.ViewModel;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import org.pcsoft.app.jimix.core.project.JimixLayer;

public class LayerListViewModel implements ViewModel {
    private final ListProperty<JimixLayer> layerList = new SimpleListProperty<>();
    private final ObjectProperty<JimixLayer> selectedLayer = new SimpleObjectProperty<>();

    public ObservableList<JimixLayer> getLayerList() {
        return layerList.get();
    }

    public ListProperty<JimixLayer> layerListProperty() {
        return layerList;
    }

    public void setLayerList(ObservableList<JimixLayer> layerList) {
        this.layerList.set(layerList);
    }

    public JimixLayer getSelectedLayer() {
        return selectedLayer.get();
    }

    public ObjectProperty<JimixLayer> selectedLayerProperty() {
        return selectedLayer;
    }

    public void setSelectedLayer(JimixLayer selectedLayer) {
        this.selectedLayer.set(selectedLayer);
    }
}

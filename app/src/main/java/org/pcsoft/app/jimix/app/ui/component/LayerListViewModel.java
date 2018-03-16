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
    //Top Layer of selection
    private final ObjectProperty<JimixLayer> selectedTopLayer = new SimpleObjectProperty<>();
    private final ObjectProperty<LayerList.LayerSubType> selectedTopLayerType = new SimpleObjectProperty<>();
    private final ObjectProperty<Object> selectedItem = new SimpleObjectProperty<>();


    public ObservableList<JimixLayer> getLayerList() {
        return layerList.get();
    }

    public ListProperty<JimixLayer> layerListProperty() {
        return layerList;
    }

    public void setLayerList(ObservableList<JimixLayer> layerList) {
        this.layerList.set(layerList);
    }

    public JimixLayer getSelectedTopLayer() {
        return selectedTopLayer.get();
    }

    public ObjectProperty<JimixLayer> selectedTopLayerProperty() {
        return selectedTopLayer;
    }

    public void setSelectedTopLayer(JimixLayer selectedTopLayer) {
        this.selectedTopLayer.set(selectedTopLayer);
    }

    public LayerList.LayerSubType getSelectedTopLayerType() {
        return selectedTopLayerType.get();
    }

    public ObjectProperty<LayerList.LayerSubType> selectedTopLayerTypeProperty() {
        return selectedTopLayerType;
    }

    public void setSelectedTopLayerType(LayerList.LayerSubType selectedTopLayerType) {
        this.selectedTopLayerType.set(selectedTopLayerType);
    }

    public Object getSelectedItem() {
        return selectedItem.get();
    }

    public ObjectProperty<Object> selectedItemProperty() {
        return selectedItem;
    }

    public void setSelectedItem(Object selectedItem) {
        this.selectedItem.set(selectedItem);
    }
}

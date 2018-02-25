package org.pcsoft.app.jimix.app.ui.component;

import de.saxsys.mvvmfx.ViewModel;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import org.pcsoft.app.jimix.core.plugin.type.JimixFilterInstance;
import org.pcsoft.app.jimix.core.project.JimixElement;
import org.pcsoft.app.jimix.core.project.JimixLayer;

public class LayerListViewModel implements ViewModel {
    private final ListProperty<JimixLayer> layerList = new SimpleListProperty<>();
    //Top Layer of selection
    private final ObjectProperty<JimixLayer> selectedTopLayer = new SimpleObjectProperty<>();
    private final ObjectProperty<JimixLayer> selectedLayer = new SimpleObjectProperty<>();
    private final ObjectProperty<JimixElement> selectedElement = new SimpleObjectProperty<>();
    private final ObjectProperty<JimixFilterInstance> selectedFilter = new SimpleObjectProperty<>();

    public JimixFilterInstance getSelectedFilter() {
        return selectedFilter.get();
    }

    public ObjectProperty<JimixFilterInstance> selectedFilterProperty() {
        return selectedFilter;
    }

    public void setSelectedFilter(JimixFilterInstance selectedFilter) {
        this.selectedFilter.set(selectedFilter);
    }

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

    public JimixElement getSelectedElement() {
        return selectedElement.get();
    }

    public ObjectProperty<JimixElement> selectedElementProperty() {
        return selectedElement;
    }

    public void setSelectedElement(JimixElement selectedElement) {
        this.selectedElement.set(selectedElement);
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
}

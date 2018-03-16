package org.pcsoft.app.jimix.app.ui.component;

import de.saxsys.mvvmfx.ViewModel;
import javafx.beans.property.*;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import org.pcsoft.app.jimix.core.project.JimixLayer;

public class PictureEditorPaneViewModel implements ViewModel {
    private final ListProperty<JimixLayer> layerList = new SimpleListProperty<>();
    private final ObjectProperty<JimixLayer> selectedTopLayer = new SimpleObjectProperty<>();
    private final ObjectProperty<Object> selectedItem = new SimpleObjectProperty<>();

    private final BooleanProperty showMask = new SimpleBooleanProperty();

    private final ObjectProperty<Image> resultPicture = new SimpleObjectProperty<>();

    /********************************************************************************/

    public Object getSelectedItem() {
        return selectedItem.get();
    }

    public ObjectProperty<Object> selectedItemProperty() {
        return selectedItem;
    }

    public void setSelectedItem(Object selectedItem) {
        this.selectedItem.set(selectedItem);
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

    public JimixLayer getSelectedTopLayer() {
        return selectedTopLayer.get();
    }

    public ObjectProperty<JimixLayer> selectedTopLayerProperty() {
        return selectedTopLayer;
    }

    public void setSelectedTopLayer(JimixLayer selectedTopLayer) {
        this.selectedTopLayer.set(selectedTopLayer);
    }

    public Image getResultPicture() {
        return resultPicture.get();
    }

    public ObjectProperty<Image> resultPictureProperty() {
        return resultPicture;
    }

    public void setResultPicture(Image resultPicture) {
        this.resultPicture.set(resultPicture);
    }

    public boolean isShowMask() {
        return showMask.get();
    }

    public BooleanProperty showMaskProperty() {
        return showMask;
    }

    public void setShowMask(boolean showMask) {
        this.showMask.set(showMask);
    }
}

package org.pcsoft.app.jimix.app.ui.component;

import de.saxsys.mvvmfx.ViewModel;
import javafx.beans.Observable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.ReadOnlyListWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import org.pcsoft.app.jimix.core.project.JimixLayer;

public class PictureEditorPaneViewModel implements ViewModel {
    private final ReadOnlyListProperty<JimixLayer> layerList =
            new ReadOnlyListWrapper<JimixLayer>(FXCollections.observableArrayList(param -> new Observable[] {param.getModel().maskProperty()}))
                    .getReadOnlyProperty();
    private final ObjectProperty<JimixLayer> selectedLayer = new SimpleObjectProperty<>();

    private final ObjectProperty<Image> resultPicture = new SimpleObjectProperty<>();

    /********************************************************************************/

    public ObservableList<JimixLayer> getLayerList() {
        return layerList.get();
    }

    public ReadOnlyListProperty<JimixLayer> layerListProperty() {
        return layerList;
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

    public Image getResultPicture() {
        return resultPicture.get();
    }

    public ObjectProperty<Image> resultPictureProperty() {
        return resultPicture;
    }

    public void setResultPicture(Image resultPicture) {
        this.resultPicture.set(resultPicture);
    }
}

package org.pcsoft.app.jimix.app.ui.component;

import de.saxsys.mvvmfx.ViewModel;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.ReadOnlyListWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import org.pcsoft.app.jimix.core.model.JimixLevel;

public class PictureEditorPaneViewModel implements ViewModel {
    private final ReadOnlyListProperty<JimixLevel> levelList =
            new ReadOnlyListWrapper<JimixLevel>(FXCollections.observableArrayList(param -> new Observable[] {param.maskProperty(), param.pictureProperty()}))
                    .getReadOnlyProperty();

    private final ObjectBinding<Image> resultPicture;

    public PictureEditorPaneViewModel() {
        resultPicture = Bindings.createObjectBinding(() -> {
            if (levelList.isEmpty())
                return null;

            return levelList.get(0).getPicture();
        }, levelList);
    }

    public ObservableList<JimixLevel> getLevelList() {
        return levelList.get();
    }

    public ReadOnlyListProperty<JimixLevel> levelListProperty() {
        return levelList;
    }

    public Image getResultPicture() {
        return resultPicture.get();
    }

    public ObjectBinding<Image> resultPictureProperty() {
        return resultPicture;
    }
}

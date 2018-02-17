package org.pcsoft.app.jimix.core.project;

import javafx.beans.Observable;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javafx.util.Callback;
import org.pcsoft.app.jimix.core.plugin.builtin.blender.OverlayBlender;

import java.util.ArrayList;
import java.util.List;

public final class JimixLayerModel {
    private final StringProperty name = new SimpleStringProperty("Layer");
    private final ObjectProperty<Image> mask = new SimpleObjectProperty<>();
    private final StringProperty blender = new SimpleStringProperty(OverlayBlender.class.getName());
    private final ReadOnlyListProperty<JimixElementModel> elementList =
            new ReadOnlyListWrapper<>(FXCollections.observableArrayList(new JimixElementObserverCallback())).getReadOnlyProperty();
    private final ReadOnlyListProperty<String> filterList =
            new ReadOnlyListWrapper<String>(FXCollections.observableArrayList()).getReadOnlyProperty();

    public JimixLayerModel() {
    }

    public JimixLayerModel(final Image image) {
        elementList.add(new JimixImageElementModel(image));
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public Image getMask() {
        return mask.get();
    }

    public ObjectProperty<Image> maskProperty() {
        return mask;
    }

    public void setMask(Image mask) {
        this.mask.set(mask);
    }

    public String getBlender() {
        return blender.get();
    }

    public StringProperty blenderProperty() {
        return blender;
    }

    public void setBlender(String blender) {
        this.blender.set(blender);
    }

    public ObservableList<JimixElementModel> getElementList() {
        return elementList.get();
    }

    public ReadOnlyListProperty<JimixElementModel> elementListProperty() {
        return elementList;
    }

    public ObservableList<String> getFilterList() {
        return filterList.get();
    }

    public ReadOnlyListProperty<String> filterListProperty() {
        return filterList;
    }

    @Override
    public String toString() {
        return "JimixLayerModel{" +
                "name=" + name +
                '}';
    }

    private static final class JimixElementObserverCallback implements Callback<JimixElementModel, Observable[]> {
        @Override
        public Observable[] call(JimixElementModel param) {
            final List<Observable> list = new ArrayList<>();
            list.add(param.opacityProperty());
            list.add(param.xProperty());
            list.add(param.yProperty());
            if (param instanceof JimixImageElementModel) {
                list.add(((JimixImageElementModel) param).valueProperty());
            }

            return list.toArray(new Observable[list.size()]);
        }
    }
}

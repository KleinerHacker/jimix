package org.pcsoft.app.jimix.core.project;

import javafx.beans.Observable;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javafx.util.Callback;
import org.pcsoft.app.jimix.commons.exception.JimixPluginException;
import org.pcsoft.app.jimix.core.plugin.builtin.blender.OverlayBlender;
import org.pcsoft.app.jimix.core.plugin.builtin.model.JimixImageElementModel;
import org.pcsoft.app.jimix.core.plugin.type.JimixBlenderInstance;
import org.pcsoft.app.jimix.core.plugin.type.JimixBlenderPlugin;
import org.pcsoft.app.jimix.core.plugin.type.JimixFilterInstance;
import org.pcsoft.app.jimix.plugins.api.annotation.JimixProperty;
import org.pcsoft.app.jimix.plugins.api.annotation.JimixPropertyDoubleRestriction;
import org.pcsoft.app.jimix.plugins.api.model.JimixElementModel;
import org.pcsoft.app.jimix.plugins.api.model.JimixModel;

import java.util.ArrayList;
import java.util.List;

public final class JimixLayerModel implements JimixModel {
    @JimixProperty(fieldType = String.class, name = "Name", description = "Name of layer", category = "Default")
    private final StringProperty name = new SimpleStringProperty("Layer");
    @JimixProperty(fieldType = Double.class, name = "Opacity", description = "Opacity of layer", category = "View")
    @JimixPropertyDoubleRestriction(minValue = 0d, maxValue = 1d)
    private final DoubleProperty opacity = new SimpleDoubleProperty(1d);
    private final BooleanProperty visibility = new SimpleBooleanProperty(true);
    private final ObjectProperty<Image> mask = new SimpleObjectProperty<>();
    private final ObjectProperty<JimixBlenderInstance> blender;

    private final ReadOnlyListProperty<JimixElementModel> elementList =
            new ReadOnlyListWrapper<>(FXCollections.observableArrayList(new JimixElementObserverCallback())).getReadOnlyProperty();
    private final ReadOnlyListProperty<JimixFilterInstance> filterList =
            new ReadOnlyListWrapper<JimixFilterInstance>(FXCollections.observableArrayList(param -> param.getConfiguration().getObservables())).getReadOnlyProperty();

    public JimixLayerModel() {
        try {
            blender = new SimpleObjectProperty<>(new JimixBlenderPlugin(new OverlayBlender()).createInstance());
        } catch (JimixPluginException e) {
            throw new RuntimeException(e);
        }
    }

    public JimixLayerModel(final Image image) {
        this();
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

    public double getOpacity() {
        return opacity.get();
    }

    public DoubleProperty opacityProperty() {
        return opacity;
    }

    public void setOpacity(double opacity) {
        this.opacity.set(opacity);
    }

    public boolean isVisibility() {
        return visibility.get();
    }

    public BooleanProperty visibilityProperty() {
        return visibility;
    }

    public void setVisibility(boolean visibility) {
        this.visibility.set(visibility);
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

    public JimixBlenderInstance getBlender() {
        return blender.get();
    }

    public ObjectProperty<JimixBlenderInstance> blenderProperty() {
        return blender;
    }

    public void setBlender(JimixBlenderInstance blender) {
        this.blender.set(blender);
    }

    public ObservableList<JimixElementModel> getElementList() {
        return elementList.get();
    }

    public ReadOnlyListProperty<JimixElementModel> elementListProperty() {
        return elementList;
    }

    public ObservableList<JimixFilterInstance> getFilterList() {
        return filterList.get();
    }

    public ReadOnlyListProperty<JimixFilterInstance> filterListProperty() {
        return filterList;
    }

    @Override
    public Observable[] getObservableValues() {
        return new Observable[] {
                name, opacity, visibility, mask, blender, elementList, filterList
        };
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
            list.add(param.dimensionProperty());
            list.add(param.visibilityProperty());
            list.add(param.mirrorHorizontalProperty());
            list.add(param.mirrorVerticalProperty());
            list.add(param.rotationProperty());
            if (param instanceof JimixImageElementModel) {
                list.add(((JimixImageElementModel) param).valueProperty());
            }

            return list.toArray(new Observable[list.size()]);
        }
    }
}

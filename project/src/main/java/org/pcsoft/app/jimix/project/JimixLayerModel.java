package org.pcsoft.app.jimix.project;

import javafx.beans.Observable;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.util.Callback;
import org.pcsoft.app.jimix.commons.exception.JimixPluginException;
import org.pcsoft.app.jimix.plugin.common.api.annotation.JimixProperty;
import org.pcsoft.app.jimix.plugin.common.api.annotation.JimixPropertyDoubleRestriction;
import org.pcsoft.app.jimix.plugin.manipulation.api.JimixBlender;
import org.pcsoft.app.jimix.plugin.manipulation.manager.type.JimixBlenderInstance;
import org.pcsoft.app.jimix.plugin.manipulation.manager.type.JimixBlenderPlugin;
import org.pcsoft.app.jimix.plugin.manipulation.manager.type.JimixFilterInstance;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class JimixLayerModel implements JimixModel<JimixLayerModel> {
    @JimixProperty(fieldType = String.class, name = "Name", description = "Name of layer", category = "Default")
    private final StringProperty name = new SimpleStringProperty("Layer");
    @JimixProperty(fieldType = Paint.class, name = "Background", description = "Paint for layer background", category = "View")
    private final ObjectProperty<Paint> background = new SimpleObjectProperty<>(Color.TRANSPARENT);
    @JimixProperty(fieldType = Double.class, name = "Opacity", description = "Opacity of layer", category = "View")
    @JimixPropertyDoubleRestriction(minValue = 0d, maxValue = 1d)
    private final DoubleProperty opacity = new SimpleDoubleProperty(1d);
    private final ObjectProperty<Image> mask = new SimpleObjectProperty<>();
    @JimixProperty(fieldType = JimixBlenderInstance.class, name = "Blender", description = "Blending between layers", category = "View")
    private final ObjectProperty<JimixBlenderInstance> blender;

    private final ReadOnlyListProperty<JimixElementModel> elementList =
            new ReadOnlyListWrapper<>(FXCollections.observableArrayList(new JimixElementObserverCallback())).getReadOnlyProperty();
    private final ReadOnlyListProperty<JimixFilterInstance> filterList =
            new ReadOnlyListWrapper<JimixFilterInstance>(FXCollections.observableArrayList(param -> param.getConfiguration().getObservables())).getReadOnlyProperty();

    public JimixLayerModel(final JimixBlenderInstance blender) {
        this.blender = new ReadOnlyObjectWrapper<>(blender);
    }

    public JimixLayerModel(final JimixBlender blender) {
        try {
            this.blender = new SimpleObjectProperty<>(new JimixBlenderPlugin(blender).createInstance());
        } catch (JimixPluginException e) {
            throw new RuntimeException(e);
        }
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

    public Paint getBackground() {
        return background.get();
    }

    public ObjectProperty<Paint> backgroundProperty() {
        return background;
    }

    public void setBackground(Paint background) {
        this.background.set(background);
    }

    @Override
    public JimixLayerModel copy() {
        final JimixLayerModel layerModel = new JimixLayerModel(this.blender.get().copy());
        layerModel.setName(this.name.getName());
        layerModel.setOpacity(this.opacity.get());
        layerModel.setBackground(this.background.get());
        layerModel.setMask(new WritableImage(this.mask.get().getPixelReader(), (int) this.mask.get().getWidth(), (int) this.mask.get().getHeight()));

        return layerModel;
    }

    @Override
    public Observable[] getObservableValues() {
        return new Observable[]{
                name, background, opacity, mask, blender, elementList, filterList
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
            list.add(param.mirrorHorizontalProperty());
            list.add(param.mirrorVerticalProperty());
            list.add(param.rotationProperty());
            list.addAll(Arrays.asList(param.getPluginElement().getObservables()));

            return list.toArray(new Observable[list.size()]);
        }
    }
}

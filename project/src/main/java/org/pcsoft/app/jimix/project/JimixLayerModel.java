package org.pcsoft.app.jimix.project;

import javafx.beans.Observable;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import java.util.stream.Collectors;

public final class JimixLayerModel implements JimixModel<JimixLayerModel> {
    @JimixProperty(fieldType = String.class, name = "Name", description = "Name of layer", category = "Default")
    private final StringProperty name = new SimpleStringProperty("Layer");
    @JimixProperty(fieldType = Paint.class, name = "Background", description = "Paint for layer background", category = "View")
    private final ObjectProperty<Paint> background = new SimpleObjectProperty<>(Color.TRANSPARENT);
    @JimixProperty(fieldType = Double.class, name = "Opacity", description = "Opacity of layer", category = "View")
    @JimixPropertyDoubleRestriction(minValue = 0d, maxValue = 1d)
    private final DoubleProperty opacity = new SimpleDoubleProperty(1d);
    @JimixProperty(fieldType = JimixBlenderInstance.class, name = "Blender", description = "Blending between layers", category = "View")
    private final ObjectProperty<JimixBlenderInstance> blender;

    private final ReadOnlyListProperty<JimixPictureElementModel> pictureElementList =
            new ReadOnlyListWrapper<JimixPictureElementModel>(FXCollections.observableArrayList(new JimixElementObserverCallback<>())).getReadOnlyProperty();
    private final ReadOnlyListProperty<JimixMaskElementModel> maskElementList =
            new ReadOnlyListWrapper<JimixMaskElementModel>(FXCollections.observableArrayList(new JimixElementObserverCallback<>())).getReadOnlyProperty();
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

    public JimixBlenderInstance getBlender() {
        return blender.get();
    }

    public ObjectProperty<JimixBlenderInstance> blenderProperty() {
        return blender;
    }

    public void setBlender(JimixBlenderInstance blender) {
        this.blender.set(blender);
    }

    public ObservableList<JimixPictureElementModel> getPictureElementList() {
        return pictureElementList.get();
    }

    public ReadOnlyListProperty<JimixPictureElementModel> pictureElementListProperty() {
        return pictureElementList;
    }

    public ObservableList<JimixMaskElementModel> getMaskElementList() {
        return maskElementList.get();
    }

    public ReadOnlyListProperty<JimixMaskElementModel> maskElementListProperty() {
        return maskElementList;
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
        layerModel.setName(this.name.get());
        layerModel.setOpacity(this.opacity.get());
        layerModel.setBackground(this.background.get());

        layerModel.pictureElementList.addAll(
                this.pictureElementList.stream()
                        .map(JimixPictureElementModel::copy)
                        .collect(Collectors.toList())
        );
        layerModel.maskElementList.addAll(
                this.maskElementList.stream()
                        .map(JimixMaskElementModel::copy)
                        .collect(Collectors.toList())
        );

        layerModel.filterList.addAll(
                this.filterList.stream()
                        .map(JimixFilterInstance::copy)
                        .collect(Collectors.toList())
        );

        return layerModel;
    }

    @Override
    public Observable[] getObservables() {
        return new Observable[]{
                name, background, opacity, blender, pictureElementList, maskElementList, filterList
        };
    }

    @Override
    public String toString() {
        return "JimixLayerModel{" +
                "name=" + name +
                '}';
    }

    private static final class JimixElementObserverCallback<T extends JimixElementModel> implements Callback<T, Observable[]> {
        @Override
        public Observable[] call(T param) {
            final List<Observable> list = new ArrayList<>();
            list.addAll(Arrays.asList(param.getObservables()));
            list.addAll(Arrays.asList(param.getPluginElement().getObservables()));

            return list.toArray(new Observable[list.size()]);
        }
    }
}

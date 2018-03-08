package org.pcsoft.app.jimix.plugin.impl.base.model;

import javafx.beans.Observable;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.image.Image;
import org.pcsoft.app.jimix.plugin.common.api.annotation.JimixProperty;

public class BoxPluginElement extends Plugin3DElement {
    @JimixProperty(fieldType = Double.class, name = "Width", description = "Box width", category = "Box")
    private final DoubleProperty width = new SimpleDoubleProperty(100d);
    @JimixProperty(fieldType = Double.class, name = "Height", description = "Box height", category = "Box")
    private final DoubleProperty height = new SimpleDoubleProperty(100d);
    @JimixProperty(fieldType = Double.class, name = "Depth", description = "Box depth", category = "Box")
    private final DoubleProperty depth = new SimpleDoubleProperty(100d);

    public double getWidth() {
        return width.get();
    }

    public DoubleProperty widthProperty() {
        return width;
    }

    public void setWidth(double width) {
        this.width.set(width);
    }

    public double getHeight() {
        return height.get();
    }

    public DoubleProperty heightProperty() {
        return height;
    }

    public void setHeight(double height) {
        this.height.set(height);
    }

    public double getDepth() {
        return depth.get();
    }

    public DoubleProperty depthProperty() {
        return depth;
    }

    public void setDepth(double depth) {
        this.depth.set(depth);
    }

    @Override
    protected Observable[] _getObservables() {
        return new Observable[]{
                width, height, depth
        };
    }

    @Override
    public Image getPreview() {
        return null;
    }

    @Override
    public ReadOnlyObjectProperty<Image> previewProperty() {
        return new ReadOnlyObjectWrapper<>(new Image(getClass().getResourceAsStream("/base/icons/ic_builder_box16.png"))).getReadOnlyProperty();
    }
}

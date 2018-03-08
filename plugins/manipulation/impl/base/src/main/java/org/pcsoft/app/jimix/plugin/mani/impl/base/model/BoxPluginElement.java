package org.pcsoft.app.jimix.plugin.mani.impl.base.model;

import javafx.beans.Observable;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.image.Image;
import org.pcsoft.app.jimix.plugin.common.api.type.JimixPluginElement;

import java.awt.*;

public class BoxPluginElement implements JimixPluginElement {
    private final DoubleProperty width = new SimpleDoubleProperty(100d);
    private final DoubleProperty height = new SimpleDoubleProperty(100d);
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
    public Observable[] getObservables() {
        return new Observable[]{
                width, height, depth
        };
    }

    @Override
    public Dimension getPreferedSize() {
        return new Dimension((int) width.get(), (int) height.get());
    }

    @Override
    public Image getPreview() {
        return null;
    }

    @Override
    public ReadOnlyObjectProperty<Image> previewProperty() {
        return new ReadOnlyObjectWrapper<Image>(null).getReadOnlyProperty();
    }
}

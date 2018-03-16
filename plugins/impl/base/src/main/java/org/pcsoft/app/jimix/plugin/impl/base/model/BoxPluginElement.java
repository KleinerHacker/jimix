package org.pcsoft.app.jimix.plugin.impl.base.model;

import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Point3D;
import javafx.scene.image.Image;
import javafx.scene.shape.Shape3D;
import javafx.scene.transform.Rotate;
import org.pcsoft.app.jimix.commons.type.JimixSnapshotParams;
import org.pcsoft.app.jimix.plugin.common.api.annotation.JimixProperty;
import org.pcsoft.app.jimix.plugin.common.api.type.JimixPlugin3DElement;
import org.pcsoft.app.jimix.plugin.impl.base.util.BoxPluginElementUtils;

public class BoxPluginElement extends JimixPlugin3DElement<BoxPluginElement> {
    @JimixProperty(fieldType = Double.class, name = "Width", description = "Box width", category = "Alignment")
    private final DoubleProperty width = new SimpleDoubleProperty(100d);
    @JimixProperty(fieldType = Double.class, name = "Height", description = "Box height", category = "Alignment")
    private final DoubleProperty height = new SimpleDoubleProperty(100d);
    @JimixProperty(fieldType = Double.class, name = "Depth", description = "Box depth", category = "Alignment")
    private final DoubleProperty depth = new SimpleDoubleProperty(100d);

    private final ObjectBinding<Image> preview;

    public BoxPluginElement() {
        preview = Bindings.createObjectBinding(
                () -> {
                    final Shape3D shape3D = BoxPluginElementUtils.buildShape(this);
                    shape3D.getTransforms().add(new Rotate(30, new Point3D(1, 0, 0)));
                    shape3D.getTransforms().add(new Rotate(-30, new Point3D(0, 1, 0)));
                    shape3D.getTransforms().add(new Rotate(5, new Point3D(0, 0, 1)));

                    return shape3D.snapshot(new JimixSnapshotParams(), null);
                }, getObservables()
        );
    }

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
        return preview.get();
    }

    @Override
    public ObjectBinding<Image> previewProperty() {
        return preview;
    }

    @Override
    protected BoxPluginElement _copy() {
        final BoxPluginElement pluginElement = new BoxPluginElement();
        pluginElement.setWidth(this.width.get());
        pluginElement.setHeight(this.height.get());
        pluginElement.setDepth(this.depth.get());

        return pluginElement;
    }
}

package org.pcsoft.app.jimix.plugin.impl.base.model;

import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Point3D;
import javafx.scene.image.Image;
import javafx.scene.shape.Shape3D;
import javafx.scene.transform.Rotate;
import org.pcsoft.app.jimix.commons.type.JimixSnapshotParams;
import org.pcsoft.app.jimix.plugin.common.api.annotation.JimixProperty;
import org.pcsoft.app.jimix.plugin.common.api.type.JimixPlugin3DElement;
import org.pcsoft.app.jimix.plugin.impl.base.util.CylinderPluginElementUtils;

public class CylinderPluginElement extends JimixPlugin3DElement<CylinderPluginElement> {
    @JimixProperty(fieldType = Double.class, name = "Radius", description = "Cylinder radius", category = "Alignment")
    private final DoubleProperty radius = new SimpleDoubleProperty(100d);
    @JimixProperty(fieldType = Double.class, name = "Height", description = "Cylinder height", category = "Alignment")
    private final DoubleProperty height = new SimpleDoubleProperty(100d);
    @JimixProperty(fieldType = Integer.class, name = "Divisions", description = "Cylinder's count of parts", category = "Style")
    private final IntegerProperty divisions = new SimpleIntegerProperty(64);

    private final ObjectBinding<Image> preview;

    public CylinderPluginElement() {
        preview = Bindings.createObjectBinding(
                () -> {
                    final Shape3D shape3D = CylinderPluginElementUtils.buildShape(this);
                    shape3D.getTransforms().add(new Rotate(30, new Point3D(1, 0, 0)));
                    shape3D.getTransforms().add(new Rotate(-30, new Point3D(0, 1, 0)));
                    shape3D.getTransforms().add(new Rotate(5, new Point3D(0, 0, 1)));

                    return shape3D.snapshot(new JimixSnapshotParams(), null);
                }, getObservables()
        );
    }

    public double getRadius() {
        return radius.get();
    }

    public DoubleProperty radiusProperty() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius.set(radius);
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

    public int getDivisions() {
        return divisions.get();
    }

    public IntegerProperty divisionsProperty() {
        return divisions;
    }

    public void setDivisions(int divisions) {
        this.divisions.set(divisions);
    }

    @Override
    protected Observable[] _getObservables() {
        return new Observable[]{
                radius, height, divisions
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
    protected CylinderPluginElement _copy() {
        final CylinderPluginElement pluginElement = new CylinderPluginElement();
        pluginElement.setRadius(this.radius.get());
        pluginElement.setHeight(this.height.get());
        pluginElement.setDivisions(this.divisions.get());

        return pluginElement;
    }
}

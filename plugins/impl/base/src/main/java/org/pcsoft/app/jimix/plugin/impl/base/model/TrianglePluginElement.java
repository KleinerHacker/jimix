package org.pcsoft.app.jimix.plugin.impl.base.model;

import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.image.Image;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Shape;
import org.pcsoft.app.jimix.commons.type.JimixSnapshotParams;
import org.pcsoft.app.jimix.plugin.common.api.annotation.JimixProperty;
import org.pcsoft.app.jimix.plugin.common.api.type.JimixPlugin2DElement;
import org.pcsoft.app.jimix.plugin.impl.base.util.TriangleUtils;

import java.awt.*;

public final class TrianglePluginElement extends JimixPlugin2DElement<TrianglePluginElement> {
    @JimixProperty(fieldType = Point.class, name = "Point 1", description = "Point 1")
    private final ObjectProperty<Point> point1 = new SimpleObjectProperty<>(new Point(0, 100));
    @JimixProperty(fieldType = Point.class, name = "Point 2", description = "Point 2")
    private final ObjectProperty<Point> point2 = new SimpleObjectProperty<>(new Point(100, 100));
    @JimixProperty(fieldType = Point.class, name = "Point 3", description = "Point 3")
    private final ObjectProperty<Point> point3 = new SimpleObjectProperty<>(new Point(50, 0));

    private final ObjectBinding<Image> preview;

    public TrianglePluginElement() {
        preview = Bindings.createObjectBinding(
                () -> {
                    if (getFill() == null)
                        return null;

                    final Shape shape = TriangleUtils.buildShape(point1.get(), point2.get(), point3.get());
                    shape.setFill(getFill());

                    return shape.snapshot(new JimixSnapshotParams(), null);
                }, fillProperty(), point1, point2, point3
        );
    }

    public TrianglePluginElement(final Paint paint) {
        this();
        this.setFill(paint);
    }

    public Point getPoint1() {
        return point1.get();
    }

    public ObjectProperty<Point> point1Property() {
        return point1;
    }

    public void setPoint1(Point point1) {
        this.point1.set(point1);
    }

    public Point getPoint2() {
        return point2.get();
    }

    public ObjectProperty<Point> point2Property() {
        return point2;
    }

    public void setPoint2(Point point2) {
        this.point2.set(point2);
    }

    public Point getPoint3() {
        return point3.get();
    }

    public ObjectProperty<Point> point3Property() {
        return point3;
    }

    public void setPoint3(Point point3) {
        this.point3.set(point3);
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
    protected Observable[] _getObservables() {
        return new Observable[]{
                point1, point2, point3
        };
    }

    @Override
    public TrianglePluginElement copy() {
        final TrianglePluginElement pluginElement = new TrianglePluginElement(this.getFill());
        pluginElement.setPoint1(this.point1.get());
        pluginElement.setPoint2(this.point2.get());
        pluginElement.setPoint3(this.point3.get());

        return pluginElement;
    }
}

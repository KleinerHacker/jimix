package org.pcsoft.app.jimix.plugin.impl.base.model;

import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.image.Image;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Shape;
import org.pcsoft.app.jimix.commons.type.JimixSnapshotParams;
import org.pcsoft.app.jimix.plugin.common.api.annotation.JimixProperty;
import org.pcsoft.app.jimix.plugin.common.api.annotation.JimixPropertyDoubleRestriction;
import org.pcsoft.app.jimix.plugin.common.api.type.JimixPlugin2DElement;
import org.pcsoft.app.jimix.plugin.impl.base.util.TrianglePluginElementUtils;

import java.awt.*;

public final class TrianglePluginElement extends JimixPlugin2DElement<TrianglePluginElement> {
    @JimixProperty(fieldType = Dimension.class, name = "Size", description = "Size")
    private final ObjectProperty<Dimension> size = new SimpleObjectProperty<>(new Dimension(100, 100));
    @JimixProperty(fieldType = Double.class, name = "Top Position (%)", description = "Position of top on X axis in percentage")
    @JimixPropertyDoubleRestriction(minValue = 0d, maxValue = 1d)
    private final DoubleProperty top = new SimpleDoubleProperty(0.5d);

    private final ObjectBinding<Image> preview;

    public TrianglePluginElement() {
        preview = Bindings.createObjectBinding(
                () -> {
                    if (getFill() == null)
                        return null;

                    final Shape shape = TrianglePluginElementUtils.buildShape(size.get(), top.get());
                    shape.setFill(getFill());

                    return shape.snapshot(new JimixSnapshotParams(), null);
                }, fillProperty(), size, top
        );
    }

    public TrianglePluginElement(final Paint paint) {
        this();
        this.setFill(paint);
    }

    public Dimension getSize() {
        return size.get();
    }

    public ObjectProperty<Dimension> sizeProperty() {
        return size;
    }

    public void setSize(Dimension size) {
        this.size.set(size);
    }

    public double getTop() {
        return top.get();
    }

    public DoubleProperty topProperty() {
        return top;
    }

    public void setTop(double top) {
        this.top.set(top);
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
                size, top
        };
    }

    @Override
    protected TrianglePluginElement _copy() {
        final TrianglePluginElement pluginElement = new TrianglePluginElement(this.getFill());
        pluginElement.setSize(this.size.get());
        pluginElement.setTop(this.top.get());

        return pluginElement;
    }
}

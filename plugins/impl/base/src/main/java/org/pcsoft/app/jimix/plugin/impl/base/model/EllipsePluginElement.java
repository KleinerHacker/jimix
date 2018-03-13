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
import org.pcsoft.app.jimix.plugin.impl.base.util.EllipseUtils;

import java.awt.*;

public final class EllipsePluginElement extends JimixPlugin2DElement<EllipsePluginElement> {
    @JimixProperty(fieldType = Dimension.class, name = "Size", description = "Size")
    private final ObjectProperty<Dimension> size = new SimpleObjectProperty<>(new Dimension(100, 100));

    private final ObjectBinding<Image> preview;

    public EllipsePluginElement() {
        preview = Bindings.createObjectBinding(
                () -> {
                    if (getFill() == null)
                        return null;

                    final Shape shape = EllipseUtils.buildShape(0, 0, size.get());
                    shape.setFill(getFill());

                    return shape.snapshot(new JimixSnapshotParams(), null);
                }, fillProperty(), size
        );
    }

    public EllipsePluginElement(final Paint paint) {
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
                size,
        };
    }

    @Override
    public EllipsePluginElement copy() {
        final EllipsePluginElement pluginElement = new EllipsePluginElement(this.getFill());
        pluginElement.setSize(this.size.get());

        return pluginElement;
    }
}
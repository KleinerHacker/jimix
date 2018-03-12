package org.pcsoft.app.jimix.core.plugin.builtin.model;

import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.image.Image;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import org.pcsoft.app.jimix.commons.exception.JimixPluginException;
import org.pcsoft.app.jimix.commons.type.JimixSnapshotParams;
import org.pcsoft.app.jimix.core.plugin.builtin.scaler.DefaultScaler;
import org.pcsoft.app.jimix.plugin.common.api.annotation.JimixProperty;
import org.pcsoft.app.jimix.plugin.common.api.type.JimixPlugin2DElement;
import org.pcsoft.app.jimix.plugin.manipulation.manager.type.JimixScalerInstance;
import org.pcsoft.app.jimix.plugin.manipulation.manager.type.JimixScalerPlugin;

import java.awt.*;

public final class RectanglePluginElement extends JimixPlugin2DElement<RectanglePluginElement> {
    private static final int MAX_WIDTH = 100;
    private static final int MAX_HEIGHT = 50;

    @JimixProperty(fieldType = Dimension.class, name = "Size", description = "Dimension")
    private final ObjectProperty<Dimension> size = new SimpleObjectProperty<>(new Dimension(100, 100));
    @JimixProperty(fieldType = Dimension.class, name = "Arc Size", description = "Arc size of rectangle corners")
    private final ObjectProperty<Dimension> arcSize = new SimpleObjectProperty<>(new Dimension(0, 0));
    private final ObjectBinding<Image> preview;
    private final ObjectProperty<JimixScalerInstance> scaler;

    public RectanglePluginElement() {
        try {
            scaler = new SimpleObjectProperty<>(new JimixScalerPlugin(new DefaultScaler()).createInstance());
        } catch (JimixPluginException e) {
            throw new RuntimeException(e);
        }

        preview = Bindings.createObjectBinding(
                () -> {
                    if (getFill() == null)
                        return null;

                    final int width, height;
                    if (size.get().width > size.get().height) {
                        width = MAX_WIDTH;
                        height = MAX_WIDTH * size.get().height / size.get().width;
                    } else {
                        height = MAX_HEIGHT;
                        width = MAX_HEIGHT * size.get().width / size.get().height;
                    }
                    return new Rectangle(width, height, getFill()).snapshot(new JimixSnapshotParams(), null);
                }, fillProperty(), size
        );
    }

    public RectanglePluginElement(final Paint paint) {
        this();
        this.setFill(paint);
    }

    public JimixScalerInstance getScaler() {
        return scaler.get();
    }

    public ObjectProperty<JimixScalerInstance> scalerProperty() {
        return scaler;
    }

    public void setScaler(JimixScalerInstance scaler) {
        this.scaler.set(scaler);
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

    public Dimension getArcSize() {
        return arcSize.get();
    }

    public ObjectProperty<Dimension> arcSizeProperty() {
        return arcSize;
    }

    public void setArcSize(Dimension arcSize) {
        this.arcSize.set(arcSize);
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
                scaler, size, arcSize
        };
    }

    @Override
    public RectanglePluginElement copy() {
        final RectanglePluginElement pluginElement = new RectanglePluginElement(this.getFill());
        pluginElement.setSize(this.size.get());
        pluginElement.setArcSize(this.arcSize.get());
        pluginElement.setScaler(this.scaler.get().copy());

        return pluginElement;
    }
}

package org.pcsoft.app.jimix.plugin.common.api.type;

import javafx.beans.Observable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import org.apache.commons.lang.ArrayUtils;
import org.pcsoft.app.jimix.plugin.common.api.annotation.JimixProperty;

public abstract class JimixPlugin2DElement<T extends JimixPlugin2DElement<T>> implements JimixPluginElement<T> {
    @JimixProperty(fieldType = Paint.class, name = "Fill", description = "Fill of shape")
    private final ObjectProperty<Paint> fill = new SimpleObjectProperty<>(Color.BLACK);
    @JimixProperty(fieldType = Paint.class, name = "Stroke", description = "Stroke of shape")
    private final ObjectProperty<Paint> stroke = new SimpleObjectProperty<>(Color.TRANSPARENT);

    public Paint getFill() {
        return fill.get();
    }

    public ObjectProperty<Paint> fillProperty() {
        return fill;
    }

    public void setFill(Paint fill) {
        this.fill.set(fill);
    }

    public Paint getStroke() {
        return stroke.get();
    }

    public ObjectProperty<Paint> strokeProperty() {
        return stroke;
    }

    public void setStroke(Paint stroke) {
        this.stroke.set(stroke);
    }

    @Override
    public final Observable[] getObservables() {
        return (Observable[]) ArrayUtils.addAll(
                new Observable[] {
                        fill, stroke
                },
                _getObservables()
        );
    }

    protected abstract Observable[] _getObservables();

    @Override
    public final T copy() {
        final T copy = _copy();
        copy.setFill(this.fill.get());
        copy.setStroke(this.stroke.get());

        return copy;
    }

    protected abstract T _copy();

    @Override
    public final JimixElementType getType() {
        return JimixElementType.Element2D;
    }
}

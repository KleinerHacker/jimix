package org.pcsoft.app.jimix.plugin.common.api.type;

import javafx.beans.Observable;
import javafx.beans.property.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;
import javafx.scene.shape.StrokeType;
import org.apache.commons.lang.ArrayUtils;
import org.pcsoft.app.jimix.plugin.common.api.annotation.JimixProperty;
import org.pcsoft.app.jimix.plugin.common.api.annotation.JimixPropertyDoubleRestriction;

public abstract class JimixPlugin2DElement<T extends JimixPlugin2DElement<T>> implements JimixPluginElement<T> {
    @JimixProperty(fieldType = Paint.class, name = "Fill", description = "Fill of shape", category = "Style")
    private final ObjectProperty<Paint> fill = new SimpleObjectProperty<>(Color.BLACK);
    @JimixProperty(fieldType = Paint.class, name = "Stroke", description = "Stroke of shape", category = "Style")
    private final ObjectProperty<Paint> stroke = new SimpleObjectProperty<>(Color.TRANSPARENT);
    @JimixProperty(fieldType = Integer.class, name = "Stroke Width", description = "Width of stroke", category = "Style")
    private final IntegerProperty strokeWidth = new SimpleIntegerProperty(1);
    @JimixProperty(fieldType = StrokeType.class, name = "Stroke Type", description = "Type of stroke", category = "Style")
    private final ObjectProperty<StrokeType> strokeType = new SimpleObjectProperty<>(StrokeType.CENTERED);
    @JimixProperty(fieldType = StrokeLineCap.class, name = "Stroke Cap", description = "Cap of stroke", category = "Style")
    private final ObjectProperty<StrokeLineCap> strokeLineCap = new SimpleObjectProperty<>(StrokeLineCap.ROUND);
    @JimixProperty(fieldType = StrokeLineJoin.class, name = "Stroke Join", description = "Join of stroke", category = "Style")
    private final ObjectProperty<StrokeLineJoin> strokeLineJoin = new SimpleObjectProperty<>(StrokeLineJoin.ROUND);
    @JimixProperty(fieldType = Double.class, name = "Stroke Miter Limit", description = "Miter Limit of stroke", category = "Style")
    @JimixPropertyDoubleRestriction(minValue = 1d, maxValue = 10d)
    private final DoubleProperty strokeMiterLimit = new SimpleDoubleProperty(10d);
    @JimixProperty(fieldType = Double.class, name = "Stroke Dash Offset", description = "Dash Offset of stroke", category = "Style")
    private final DoubleProperty strokeDashOffset = new SimpleDoubleProperty(0d);

    public JimixPlugin2DElement() {
    }

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

    public int getStrokeWidth() {
        return strokeWidth.get();
    }

    public IntegerProperty strokeWidthProperty() {
        return strokeWidth;
    }

    public void setStrokeWidth(int strokeWidth) {
        this.strokeWidth.set(strokeWidth);
    }

    public StrokeType getStrokeType() {
        return strokeType.get();
    }

    public ObjectProperty<StrokeType> strokeTypeProperty() {
        return strokeType;
    }

    public void setStrokeType(StrokeType strokeType) {
        this.strokeType.set(strokeType);
    }

    public StrokeLineCap getStrokeLineCap() {
        return strokeLineCap.get();
    }

    public ObjectProperty<StrokeLineCap> strokeLineCapProperty() {
        return strokeLineCap;
    }

    public void setStrokeLineCap(StrokeLineCap strokeLineCap) {
        this.strokeLineCap.set(strokeLineCap);
    }

    public StrokeLineJoin getStrokeLineJoin() {
        return strokeLineJoin.get();
    }

    public ObjectProperty<StrokeLineJoin> strokeLineJoinProperty() {
        return strokeLineJoin;
    }

    public void setStrokeLineJoin(StrokeLineJoin strokeLineJoin) {
        this.strokeLineJoin.set(strokeLineJoin);
    }

    public double getStrokeMiterLimit() {
        return strokeMiterLimit.get();
    }

    public DoubleProperty strokeMiterLimitProperty() {
        return strokeMiterLimit;
    }

    public void setStrokeMiterLimit(double strokeMiterLimit) {
        this.strokeMiterLimit.set(strokeMiterLimit);
    }

    public double getStrokeDashOffset() {
        return strokeDashOffset.get();
    }

    public DoubleProperty strokeDashOffsetProperty() {
        return strokeDashOffset;
    }

    public void setStrokeDashOffset(double strokeDashOffset) {
        this.strokeDashOffset.set(strokeDashOffset);
    }

    @Override
    public final Observable[] getObservables() {
        return (Observable[]) ArrayUtils.addAll(
                new Observable[] {
                        fill, stroke, strokeLineCap, strokeLineJoin, strokeType, strokeWidth, strokeDashOffset, strokeMiterLimit
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
        copy.setStrokeLineCap(this.strokeLineCap.get());
        copy.setStrokeLineJoin(this.strokeLineJoin.get());
        copy.setStrokeType(this.strokeType.get());
        copy.setStrokeWidth(this.strokeWidth.get());
        copy.setStrokeDashOffset(this.strokeDashOffset.get());
        copy.setStrokeMiterLimit(this.strokeMiterLimit.get());

        return copy;
    }

    protected abstract T _copy();

    @Override
    public final JimixElementType getType() {
        return JimixElementType.Element2D;
    }
}

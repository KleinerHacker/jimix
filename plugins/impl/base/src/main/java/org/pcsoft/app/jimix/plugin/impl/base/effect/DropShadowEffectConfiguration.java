package org.pcsoft.app.jimix.plugin.impl.base.effect;

import javafx.beans.Observable;
import javafx.beans.property.*;
import javafx.scene.effect.BlurType;
import javafx.scene.paint.Color;
import org.pcsoft.app.jimix.plugins.api.annotation.JimixProperty;
import org.pcsoft.app.jimix.plugins.api.annotation.JimixPropertyDoubleRestriction;
import org.pcsoft.app.jimix.plugins.api.config.JimixEffectConfiguration;

public class DropShadowEffectConfiguration implements JimixEffectConfiguration<DropShadowEffectConfiguration> {
    @JimixProperty(fieldType = BlurType.class, name = "Blur Type", description = "Blur Type", category = "Blur")
    private final ObjectProperty<BlurType> blurType = new SimpleObjectProperty<>(BlurType.GAUSSIAN);
    @JimixProperty(fieldType = Double.class, name = "Radius", description = "Radius", category = "Blur")
    @JimixPropertyDoubleRestriction(minValue = 0.1d, maxValue = 50d)
    private final DoubleProperty radius = new SimpleDoubleProperty(15d);
    @JimixProperty(fieldType = Double.class, name = "Spread", description = "Spread", category = "Shadow")
    @JimixPropertyDoubleRestriction(minValue = 0.00001d, maxValue = 1d)
    private final DoubleProperty spread = new SimpleDoubleProperty(0.01d);
    @JimixProperty(fieldType = Integer.class, name = "Offset X", description = "X", category = "Shadow")
    private final IntegerProperty offsetX = new SimpleIntegerProperty(5);
    @JimixProperty(fieldType = Integer.class, name = "Offset Y", description = "Y", category = "Shadow")
    private final IntegerProperty offsetY = new SimpleIntegerProperty(5);
    @JimixProperty(fieldType = Color.class, name = "Color", description = "Color", category = "Shadow")
    private final ObjectProperty<Color> color = new SimpleObjectProperty<>(Color.BLACK);

    public BlurType getBlurType() {
        return blurType.get();
    }

    public ObjectProperty<BlurType> blurTypeProperty() {
        return blurType;
    }

    public void setBlurType(BlurType blurType) {
        this.blurType.set(blurType);
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

    public double getSpread() {
        return spread.get();
    }

    public DoubleProperty spreadProperty() {
        return spread;
    }

    public void setSpread(double spread) {
        this.spread.set(spread);
    }

    public int getOffsetX() {
        return offsetX.get();
    }

    public IntegerProperty offsetXProperty() {
        return offsetX;
    }

    public void setOffsetX(int offsetX) {
        this.offsetX.set(offsetX);
    }

    public int getOffsetY() {
        return offsetY.get();
    }

    public IntegerProperty offsetYProperty() {
        return offsetY;
    }

    public void setOffsetY(int offsetY) {
        this.offsetY.set(offsetY);
    }

    public Color getColor() {
        return color.get();
    }

    public ObjectProperty<Color> colorProperty() {
        return color;
    }

    public void setColor(Color color) {
        this.color.set(color);
    }

    @Override
    public void update(DropShadowEffectConfiguration configuration) {
        this.blurType.set(configuration.blurType.get());
        this.radius.set(configuration.radius.get());
        this.color.set(configuration.color.get());
        this.spread.set(configuration.spread.get());
        this.offsetX.set(configuration.offsetX.get());
        this.offsetY.set(configuration.offsetY.get());
    }

    @Override
    public Observable[] getObservables() {
        return new Observable[] {
                blurType, radius, spread, offsetX, offsetY, color
        };
    }
}

package org.pcsoft.app.jimix.plugin.impl.base.manipulation.effect;

import javafx.beans.Observable;
import javafx.beans.property.*;
import javafx.scene.effect.BlurType;
import javafx.scene.paint.Color;
import org.pcsoft.app.jimix.plugin.common.api.annotation.JimixProperty;
import org.pcsoft.app.jimix.plugin.common.api.annotation.JimixPropertyDoubleRestriction;
import org.pcsoft.app.jimix.plugin.manipulation.api.config.JimixEffectConfiguration;

import java.io.*;

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
    public DropShadowEffectConfiguration copy() {
        final DropShadowEffectConfiguration configuration = new DropShadowEffectConfiguration();
        configuration.blurType.set(this.blurType.get());
        configuration.radius.set(this.radius.get());
        configuration.color.set(this.color.get());
        configuration.spread.set(this.spread.get());
        configuration.offsetX.set(this.offsetX.get());
        configuration.offsetY.set(this.offsetY.get());

        return configuration;
    }

    @Override
    public void save(ObjectOutputStream out) throws IOException {
        out.writeInt(this.blurType.get().ordinal());
        out.writeDouble(this.radius.get());
        out.writeDouble(this.color.get().getRed());
        out.writeDouble(this.color.get().getGreen());
        out.writeDouble(this.color.get().getBlue());
        out.writeDouble(this.color.get().getOpacity());
        out.writeDouble(this.spread.get());
        out.writeInt(this.offsetX.get());
        out.writeInt(this.offsetY.get());
    }

    @Override
    public void load(ObjectInputStream in) throws IOException {
        try {
            this.blurType.set(BlurType.values()[in.readInt()]);
        } catch (IOException e) {
            throw e;
        } catch (Exception e) {
            throw new IOException("Unable to read blur type");
        }
        this.radius.set(in.readDouble());
        this.color.set(new Color(in.readDouble(), in.readDouble(), in.readDouble(), in.readDouble()));
        this.spread.set(in.readDouble());
        this.offsetX.set(in.readInt());
        this.offsetY.set(in.readInt());
    }

    @Override
    public Observable[] getObservables() {
        return new Observable[]{
                blurType, radius, spread, offsetX, offsetY, color
        };
    }
}

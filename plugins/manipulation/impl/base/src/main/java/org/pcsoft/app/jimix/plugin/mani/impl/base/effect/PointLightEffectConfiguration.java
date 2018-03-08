package org.pcsoft.app.jimix.plugin.mani.impl.base.effect;

import javafx.beans.Observable;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.paint.Color;
import org.pcsoft.app.jimix.plugin.common.api.annotation.JimixProperty;
import org.pcsoft.app.jimix.plugin.common.api.annotation.JimixPropertyDoubleRestriction;
import org.pcsoft.app.jimix.plugin.mani.api.config.JimixEffectConfiguration;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class PointLightEffectConfiguration implements JimixEffectConfiguration<PointLightEffectConfiguration> {
    @JimixProperty(fieldType = Double.class, name = "X (%)", description = "X Position", category = "Position")
    @JimixPropertyDoubleRestriction(minValue = 0, maxValue = 1)
    private final DoubleProperty x = new SimpleDoubleProperty(0.5d);
    @JimixProperty(fieldType = Double.class, name = "Y (%)", description = "Y Position", category = "Position")
    @JimixPropertyDoubleRestriction(minValue = 0, maxValue = 1)
    private final DoubleProperty y = new SimpleDoubleProperty(0.5d);
    @JimixProperty(fieldType = Double.class, name = "Z (%)", description = "Z Position", category = "Position")
    @JimixPropertyDoubleRestriction(minValue = 0, maxValue = 1)
    private final DoubleProperty z = new SimpleDoubleProperty(0.5d);
    @JimixProperty(fieldType = Color.class, name = "Color", description = "Color of light", category = "Light")
    private final ObjectProperty<Color> color = new SimpleObjectProperty<>(Color.WHITE);

    public double getX() {
        return x.get();
    }

    public DoubleProperty xProperty() {
        return x;
    }

    public void setX(double x) {
        this.x.set(x);
    }

    public double getY() {
        return y.get();
    }

    public DoubleProperty yProperty() {
        return y;
    }

    public void setY(double y) {
        this.y.set(y);
    }

    public double getZ() {
        return z.get();
    }

    public DoubleProperty zProperty() {
        return z;
    }

    public void setZ(double z) {
        this.z.set(z);
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
    public Observable[] getObservables() {
        return new Observable[] {
                x, y, z, color
        };
    }

    @Override
    public void update(PointLightEffectConfiguration newConfiguration) {
        this.x.set(newConfiguration.x.get());
        this.y.set(newConfiguration.y.get());
        this.z.set(newConfiguration.z.get());
        this.color.set(newConfiguration.color.get());
    }

    @Override
    public PointLightEffectConfiguration copy() {
        final PointLightEffectConfiguration configuration = new PointLightEffectConfiguration();
        configuration.x.set(this.x.get());
        configuration.y.set(this.y.get());
        configuration.z.set(this.z.get());
        configuration.color.set(this.color.get());

        return configuration;
    }

    @Override
    public void save(ObjectOutputStream out) throws IOException {
        out.writeDouble(this.x.get());
        out.writeDouble(this.y.get());
        out.writeDouble(this.z.get());
        out.writeDouble(this.color.get().getRed());
        out.writeDouble(this.color.get().getGreen());
        out.writeDouble(this.color.get().getBlue());
        out.writeDouble(this.color.get().getOpacity());
    }

    @Override
    public void load(ObjectInputStream in) throws IOException {
        this.x.set(in.readDouble());
        this.y.set(in.readDouble());
        this.z.set(in.readDouble());
        this.color.set(new Color(in.readDouble(), in.readDouble(), in.readDouble(), in.readDouble()));
    }
}

package org.pcsoft.app.jimix.plugin.impl.base.manipulation.effect;

import javafx.beans.Observable;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.paint.Color;
import org.pcsoft.app.jimix.plugin.common.api.annotation.JimixProperty;
import org.pcsoft.app.jimix.plugin.common.api.annotation.JimixPropertyDoubleRestriction;
import org.pcsoft.app.jimix.plugin.manipulation.api.config.JimixEffectConfiguration;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class DistantLightEffectConfiguration implements JimixEffectConfiguration<DistantLightEffectConfiguration> {
    @JimixProperty(fieldType = Double.class, name = "Azimuth", description = "Azimuth", category = "Position")
    @JimixPropertyDoubleRestriction(minValue = 0, maxValue = 360)
    private final DoubleProperty azimuth = new SimpleDoubleProperty(45d);
    @JimixProperty(fieldType = Double.class, name = "Elevation", description = "Elevation", category = "Position")
    @JimixPropertyDoubleRestriction(minValue = 0, maxValue = 180)
    private final DoubleProperty elevation = new SimpleDoubleProperty(45d);
    @JimixProperty(fieldType = Color.class, name = "Color", description = "Color of light", category = "Light")
    private final ObjectProperty<Color> color = new SimpleObjectProperty<>(Color.WHITE);

    public double getAzimuth() {
        return azimuth.get();
    }

    public DoubleProperty azimuthProperty() {
        return azimuth;
    }

    public void setAzimuth(double azimuth) {
        this.azimuth.set(azimuth);
    }

    public double getElevation() {
        return elevation.get();
    }

    public DoubleProperty elevationProperty() {
        return elevation;
    }

    public void setElevation(double elevation) {
        this.elevation.set(elevation);
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
                azimuth, elevation, color
        };
    }

    @Override
    public void update(DistantLightEffectConfiguration newConfiguration) {
        this.azimuth.set(newConfiguration.azimuth.get());
        this.elevation.set(newConfiguration.elevation.get());
        this.color.set(newConfiguration.color.get());
    }

    @Override
    public DistantLightEffectConfiguration copy() {
        final DistantLightEffectConfiguration configuration = new DistantLightEffectConfiguration();
        configuration.azimuth.set(this.azimuth.get());
        configuration.elevation.set(this.elevation.get());
        configuration.color.set(this.color.get());

        return configuration;
    }

    @Override
    public void save(ObjectOutputStream out) throws IOException {
        out.writeDouble(this.azimuth.get());
        out.writeDouble(this.elevation.get());
        out.writeDouble(this.color.get().getRed());
        out.writeDouble(this.color.get().getGreen());
        out.writeDouble(this.color.get().getBlue());
        out.writeDouble(this.color.get().getOpacity());
    }

    @Override
    public void load(ObjectInputStream in) throws IOException {
        this.azimuth.set(in.readDouble());
        this.elevation.set(in.readDouble());
        this.color.set(new Color(in.readDouble(), in.readDouble(), in.readDouble(), in.readDouble()));
    }
}

package org.pcsoft.app.jimix.core.plugin.builtin.effect;

import javafx.beans.Observable;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import org.pcsoft.app.jimix.plugin.common.api.annotation.JimixProperty;
import org.pcsoft.app.jimix.plugin.common.api.annotation.JimixPropertyDoubleRestriction;
import org.pcsoft.app.jimix.plugin.manipulation.api.config.JimixEffectConfiguration;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class RotationEffectConfiguration implements JimixEffectConfiguration<RotationEffectConfiguration> {
    @JimixProperty(fieldType = Double.class, name = "X Rotation", description = "Rotation on X axis")
    @JimixPropertyDoubleRestriction(minValue = -180, maxValue = 180)
    private final DoubleProperty rotateX = new SimpleDoubleProperty(-30d);
    @JimixProperty(fieldType = Double.class, name = "Y Rotation", description = "Rotation on Y axis")
    @JimixPropertyDoubleRestriction(minValue = -180, maxValue = 180)
    private final DoubleProperty rotateY = new SimpleDoubleProperty(-30d);
    @JimixProperty(fieldType = Double.class, name = "Z Rotation", description = "Rotation on Z axis")
    @JimixPropertyDoubleRestriction(minValue = -180, maxValue = 180)
    private final DoubleProperty rotateZ = new SimpleDoubleProperty(30d);

    public double getRotateX() {
        return rotateX.get();
    }

    public DoubleProperty rotateXProperty() {
        return rotateX;
    }

    public void setRotateX(double rotateX) {
        this.rotateX.set(rotateX);
    }

    public double getRotateY() {
        return rotateY.get();
    }

    public DoubleProperty rotateYProperty() {
        return rotateY;
    }

    public void setRotateY(double rotateY) {
        this.rotateY.set(rotateY);
    }

    public double getRotateZ() {
        return rotateZ.get();
    }

    public DoubleProperty rotateZProperty() {
        return rotateZ;
    }

    public void setRotateZ(double rotateZ) {
        this.rotateZ.set(rotateZ);
    }

    @Override
    public Observable[] getObservables() {
        return new Observable[] {
                rotateX, rotateY, rotateZ
        };
    }

    @Override
    public void update(RotationEffectConfiguration newConfiguration) {
        this.rotateX.set(newConfiguration.rotateX.get());
        this.rotateY.set(newConfiguration.rotateY.get());
        this.rotateZ.set(newConfiguration.rotateZ.get());
    }

    @Override
    public RotationEffectConfiguration copy() {
        final RotationEffectConfiguration configuration = new RotationEffectConfiguration();
        configuration.rotateX.set(this.rotateX.get());
        configuration.rotateY.set(this.rotateY.get());
        configuration.rotateZ.set(this.rotateZ.get());

        return configuration;
    }

    @Override
    public void save(ObjectOutputStream out) throws IOException {
        out.writeDouble(this.rotateX.get());
        out.writeDouble(this.rotateY.get());
        out.writeDouble(this.rotateZ.get());
    }

    @Override
    public void load(ObjectInputStream in) throws IOException {
        this.rotateX.set(in.readDouble());
        this.rotateY.set(in.readDouble());
        this.rotateZ.set(in.readDouble());
    }
}

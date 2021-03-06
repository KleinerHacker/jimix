package org.pcsoft.app.jimix.plugin.impl.base.manipulation.filter;

import javafx.beans.Observable;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import org.pcsoft.app.jimix.plugin.common.api.annotation.JimixProperty;
import org.pcsoft.app.jimix.plugin.common.api.annotation.JimixPropertyDoubleRestriction;
import org.pcsoft.app.jimix.plugin.manipulation.api.config.JimixFilterConfiguration;

import java.io.*;

public class MotionBlurFilterConfiguration implements JimixFilterConfiguration<MotionBlurFilterConfiguration> {
    @JimixProperty(fieldType = double.class, name = "Radius", description = "Radius of blur", category = "View")
    @JimixPropertyDoubleRestriction(minValue = 0.1d, maxValue = 50d)
    private final DoubleProperty radius = new SimpleDoubleProperty(25d);
    @JimixProperty(fieldType = double.class, name = "Angle", description = "Angle of motion", category = "View")
    @JimixPropertyDoubleRestriction(minValue = -180, maxValue = 180)
    private final DoubleProperty angle = new SimpleDoubleProperty(15d);

    public double getRadius() {
        return radius.get();
    }

    /**
     * Radius of blur
     *
     * @return
     */
    public DoubleProperty radiusProperty() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius.set(radius);
    }

    public double getAngle() {
        return angle.get();
    }

    /**
     * Angle of motion
     *
     * @return
     */
    public DoubleProperty angleProperty() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle.set(angle);
    }

    @Override
    public void update(final MotionBlurFilterConfiguration newConfiguration) {
        this.angle.set(newConfiguration.angle.get());
        this.radius.set(newConfiguration.radius.get());
    }

    @Override
    public MotionBlurFilterConfiguration copy() {
        final MotionBlurFilterConfiguration configuration = new MotionBlurFilterConfiguration();
        configuration.angle.set(this.angle.get());
        configuration.radius.set(this.radius.get());

        return configuration;
    }

    @Override
    public void save(ObjectOutputStream out) throws IOException {
        out.writeDouble(this.angle.get());
        out.writeDouble(this.radius.get());
    }

    @Override
    public void load(ObjectInputStream in) throws IOException {
        this.angle.set(in.readDouble());
        this.radius.set(in.readDouble());
    }

    @Override
    public Observable[] getObservables() {
        return new Observable[]{
                radius, angle
        };
    }
}

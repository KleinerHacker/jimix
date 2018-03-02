package org.pcsoft.app.jimix.plugin.impl.base.filter;

import javafx.beans.Observable;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import org.pcsoft.app.jimix.plugins.api.annotation.JimixProperty;
import org.pcsoft.app.jimix.plugins.api.annotation.JimixPropertyDoubleRestriction;
import org.pcsoft.app.jimix.plugins.api.config.JimixFilterConfiguration;

public class MotionBlurConfiguration implements JimixFilterConfiguration<MotionBlurConfiguration> {
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
     * @return
     */
    public DoubleProperty angleProperty() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle.set(angle);
    }

    @Override
    public void update(final MotionBlurConfiguration newConfiguration) {
        this.angle.set(newConfiguration.angle.get());
        this.radius.set(newConfiguration.radius.get());
    }

    @Override
    public Observable[] getObservables() {
        return new Observable[] {
                radius, angle
        };
    }
}

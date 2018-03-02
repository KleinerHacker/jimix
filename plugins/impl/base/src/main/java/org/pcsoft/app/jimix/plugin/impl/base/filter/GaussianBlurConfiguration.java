package org.pcsoft.app.jimix.plugin.impl.base.filter;

import javafx.beans.Observable;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import org.pcsoft.app.jimix.plugins.api.annotation.JimixProperty;
import org.pcsoft.app.jimix.plugins.api.annotation.JimixPropertyDoubleRestriction;
import org.pcsoft.app.jimix.plugins.api.config.JimixFilterConfiguration;

public class GaussianBlurConfiguration implements JimixFilterConfiguration<GaussianBlurConfiguration> {
    @JimixProperty(fieldType = double.class, name = "Radius", description = "Radius of blur", category = "View")
    @JimixPropertyDoubleRestriction(minValue = 0.1d, maxValue = 25d)
    private final DoubleProperty radius = new SimpleDoubleProperty(5d);

    public double getRadius() {
        return radius.get();
    }

    public DoubleProperty radiusProperty() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius.set(radius);
    }

    @Override
    public void update(GaussianBlurConfiguration newConfiguration) {
        this.radius.set(newConfiguration.radius.get());
    }

    @Override
    public Observable[] getObservables() {
        return new Observable[] {
                radius
        };
    }
}

package org.pcsoft.app.jimix.plugin.impl.base.manipulation.filter;

import javafx.beans.Observable;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import org.pcsoft.app.jimix.plugin.common.api.annotation.JimixProperty;
import org.pcsoft.app.jimix.plugin.common.api.annotation.JimixPropertyDoubleRestriction;
import org.pcsoft.app.jimix.plugin.manipulation.api.config.JimixFilterConfiguration;

import java.io.*;

public class GaussianBlurFilterConfiguration implements JimixFilterConfiguration<GaussianBlurFilterConfiguration> {
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
    public void update(GaussianBlurFilterConfiguration newConfiguration) {
        this.radius.set(newConfiguration.radius.get());
    }

    @Override
    public GaussianBlurFilterConfiguration copy() {
        final GaussianBlurFilterConfiguration configuration = new GaussianBlurFilterConfiguration();
        configuration.radius.set(this.radius.get());

        return configuration;
    }

    @Override
    public void save(ObjectOutputStream out) throws IOException {
        out.writeDouble(this.radius.get());
    }

    @Override
    public void load(ObjectInputStream in) throws IOException {
        this.radius.set(in.readDouble());
    }

    @Override
    public Observable[] getObservables() {
        return new Observable[]{
                radius
        };
    }
}

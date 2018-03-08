package org.pcsoft.app.jimix.plugin.impl.base.manipulation.effect;

import javafx.beans.Observable;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import org.pcsoft.app.jimix.plugin.common.api.annotation.JimixProperty;
import org.pcsoft.app.jimix.plugin.common.api.annotation.JimixPropertyDoubleRestriction;
import org.pcsoft.app.jimix.plugin.manipulation.api.config.JimixEffectConfiguration;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class BloomEffectConfiguration implements JimixEffectConfiguration<BloomEffectConfiguration> {
    @JimixProperty(fieldType = Double.class, name = "Threshold", description = "Threshold")
    @JimixPropertyDoubleRestriction(minValue = 0.1, maxValue = 1)
    private final DoubleProperty threshold = new SimpleDoubleProperty(0.3d);

    public double getThreshold() {
        return threshold.get();
    }

    public DoubleProperty thresholdProperty() {
        return threshold;
    }

    public void setThreshold(double threshold) {
        this.threshold.set(threshold);
    }

    @Override
    public Observable[] getObservables() {
        return new Observable[] {
                threshold
        };
    }

    @Override
    public void update(BloomEffectConfiguration newConfiguration) {
        this.threshold.set(newConfiguration.threshold.get());
    }

    @Override
    public BloomEffectConfiguration copy() {
        final BloomEffectConfiguration configuration = new BloomEffectConfiguration();
        configuration.threshold.set(this.threshold.get());

        return configuration;
    }

    @Override
    public void save(ObjectOutputStream out) throws IOException {
        out.writeDouble(this.threshold.get());
    }

    @Override
    public void load(ObjectInputStream in) throws IOException {
        this.threshold.set(in.readDouble());
    }
}

package org.pcsoft.app.jimix.core.plugin.builtin.filter;

import javafx.beans.Observable;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import org.pcsoft.app.jimix.plugin.common.api.annotation.JimixProperty;
import org.pcsoft.app.jimix.plugin.common.api.annotation.JimixPropertyDoubleRestriction;
import org.pcsoft.app.jimix.plugin.manipulation.api.config.JimixFilterConfiguration;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class GrayScaleFilterConfiguration implements JimixFilterConfiguration<GrayScaleFilterConfiguration> {
    @JimixProperty(fieldType = Double.class, name = "Red Channel", description = "Red Channel", category = "Channels")
    @JimixPropertyDoubleRestriction(minValue = 0d, maxValue = 1d)
    private final DoubleProperty redChannel = new SimpleDoubleProperty(0.33d);
    @JimixProperty(fieldType = Double.class, name = "Green Channel", description = "Green Channel", category = "Channels")
    @JimixPropertyDoubleRestriction(minValue = 0d, maxValue = 1d)
    private final DoubleProperty greenChannel = new SimpleDoubleProperty(0.33d);
    @JimixProperty(fieldType = Double.class, name = "Blue Channel", description = "Blue Channel", category = "Channels")
    @JimixPropertyDoubleRestriction(minValue = 0d, maxValue = 1d)
    private final DoubleProperty blueChannel = new SimpleDoubleProperty(0.33d);

    public GrayScaleFilterConfiguration() {
    }

    GrayScaleFilterConfiguration(final double redChannel, final double greenChannel, final double blueChannel) {
        this.redChannel.set(redChannel);
        this.greenChannel.set(greenChannel);
        this.blueChannel.set(blueChannel);
    }

    public double getRedChannel() {
        return redChannel.get();
    }

    public DoubleProperty redChannelProperty() {
        return redChannel;
    }

    public void setRedChannel(double redChannel) {
        this.redChannel.set(redChannel);
    }

    public double getGreenChannel() {
        return greenChannel.get();
    }

    public DoubleProperty greenChannelProperty() {
        return greenChannel;
    }

    public void setGreenChannel(double greenChannel) {
        this.greenChannel.set(greenChannel);
    }

    public double getBlueChannel() {
        return blueChannel.get();
    }

    public DoubleProperty blueChannelProperty() {
        return blueChannel;
    }

    public void setBlueChannel(double blueChannel) {
        this.blueChannel.set(blueChannel);
    }

    @Override
    public Observable[] getObservables() {
        return new Observable[] {
                redChannel, greenChannel, blueChannel
        };
    }

    @Override
    public void update(GrayScaleFilterConfiguration newConfiguration) {
        this.redChannel.set(newConfiguration.redChannel.get());
        this.greenChannel.set(newConfiguration.greenChannel.get());
        this.blueChannel.set(newConfiguration.blueChannel.get());
    }

    @Override
    public GrayScaleFilterConfiguration copy() {
        final GrayScaleFilterConfiguration configuration = new GrayScaleFilterConfiguration();
        configuration.redChannel.set(this.redChannel.get());
        configuration.greenChannel.set(this.greenChannel.get());
        configuration.blueChannel.set(this.blueChannel.get());

        return configuration;
    }

    @Override
    public void save(ObjectOutputStream out) throws IOException {
        out.writeDouble(this.redChannel.get());
        out.writeDouble(this.greenChannel.get());
        out.writeDouble(this.blueChannel.get());
    }

    @Override
    public void load(ObjectInputStream in) throws IOException {
        this.redChannel.set(in.readDouble());
        this.greenChannel.set(in.readDouble());
        this.blueChannel.set(in.readDouble());
    }
}

package org.pcsoft.app.jimix.core.plugin.builtin.filter;

import javafx.beans.Observable;
import javafx.beans.property.*;
import javafx.scene.paint.Color;
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
    @JimixProperty(fieldType = Color.class, name = "Target Color", description = "Target color value", category = "Design")
    private final ObjectProperty<Color> targetColor = new SimpleObjectProperty<>(Color.WHITE);
    @JimixProperty(fieldType = Boolean.class, name = "Use As Opacity", description = "Use as opacity value", category = "Design")
    private final BooleanProperty useAsOpacity = new SimpleBooleanProperty(false);

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

    public Color getTargetColor() {
        return targetColor.get();
    }

    public ObjectProperty<Color> targetColorProperty() {
        return targetColor;
    }

    public void setTargetColor(Color targetColor) {
        this.targetColor.set(targetColor);
    }

    public boolean isUseAsOpacity() {
        return useAsOpacity.get();
    }

    public BooleanProperty useAsOpacityProperty() {
        return useAsOpacity;
    }

    public void setUseAsOpacity(boolean useAsOpacity) {
        this.useAsOpacity.set(useAsOpacity);
    }

    @Override
    public Observable[] getObservables() {
        return new Observable[] {
                redChannel, greenChannel, blueChannel, targetColor, useAsOpacity
        };
    }

    @Override
    public void update(GrayScaleFilterConfiguration newConfiguration) {
        this.redChannel.set(newConfiguration.redChannel.get());
        this.greenChannel.set(newConfiguration.greenChannel.get());
        this.blueChannel.set(newConfiguration.blueChannel.get());
        this.targetColor.set(newConfiguration.targetColor.get());
        this.useAsOpacity.set(newConfiguration.useAsOpacity.get());
    }

    @Override
    public GrayScaleFilterConfiguration copy() {
        final GrayScaleFilterConfiguration configuration = new GrayScaleFilterConfiguration();
        configuration.redChannel.set(this.redChannel.get());
        configuration.greenChannel.set(this.greenChannel.get());
        configuration.blueChannel.set(this.blueChannel.get());
        configuration.targetColor.set(this.targetColor.get());
        configuration.useAsOpacity.set(this.useAsOpacity.get());

        return configuration;
    }

    @Override
    public void save(ObjectOutputStream out) throws IOException {
        out.writeDouble(this.redChannel.get());
        out.writeDouble(this.greenChannel.get());
        out.writeDouble(this.blueChannel.get());
        out.writeDouble(this.targetColor.get().getRed());
        out.writeDouble(this.targetColor.get().getGreen());
        out.writeDouble(this.targetColor.get().getBlue());
        out.writeDouble(this.targetColor.get().getOpacity());
        out.writeBoolean(this.useAsOpacity.get());
    }

    @Override
    public void load(ObjectInputStream in) throws IOException {
        this.redChannel.set(in.readDouble());
        this.greenChannel.set(in.readDouble());
        this.blueChannel.set(in.readDouble());
        this.targetColor.set(new Color(in.readDouble(), in.readDouble(), in.readDouble(), in.readDouble()));
        this.useAsOpacity.set(in.readBoolean());
    }
}

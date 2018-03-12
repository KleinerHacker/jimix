package org.pcsoft.app.jimix.core.plugin.builtin.filter;

import org.pcsoft.app.jimix.plugin.manipulation.api.JimixFilterBase;
import org.pcsoft.app.jimix.plugin.manipulation.api.annotation.JimixFilterDescriptor;
import org.pcsoft.app.jimix.plugin.manipulation.api.type.*;

import java.awt.*;

@JimixFilterDescriptor(name = "Gray Scale", description = "Gray Scale Filter",
        type = JimixFilterType.ColorFilter, configurationClass = GrayScaleFilterConfiguration.class)
public class GrayScaleFilter extends JimixFilterBase<GrayScaleFilterConfiguration> {
    @Override
    protected void apply(JimixPixelReader pixelReader, JimixPixelWriter pixelWriter, GrayScaleFilterConfiguration configuration, JimixSource source) {
        for (int i = 0; i < pixelReader.getLength(); i++) {
            final Color readColor = new Color(pixelReader.getPixel(i));
            final int gray = (int) Math.min(255d, (double) readColor.getRed() * configuration.getRedChannel() +
                    (double) readColor.getGreen() * configuration.getGreenChannel() +
                    (double) readColor.getBlue() * configuration.getBlueChannel());
            final Color writeColor = new Color(gray, gray, gray, readColor.getAlpha());
            pixelWriter.setPixel(i, writeColor.getRGB());
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public JimixFilterVariant<GrayScaleFilterConfiguration>[] getVariants() {
        return new JimixFilterVariant[]{
                JimixFilterVariant.createBuiltin("Default", new GrayScaleFilterConfiguration()),
                JimixFilterVariant.createBuiltin("Red Only", new GrayScaleFilterConfiguration(1d, 0d, 0d)),
                JimixFilterVariant.createBuiltin("Green Only", new GrayScaleFilterConfiguration(0d, 1d, 0d)),
                JimixFilterVariant.createBuiltin("Blue Only", new GrayScaleFilterConfiguration(0d, 0d, 1d)),
                JimixFilterVariant.createBuiltin("Yellow Only", new GrayScaleFilterConfiguration(0.5d, 0.5d, 0d)),
                JimixFilterVariant.createBuiltin("Magenta Only", new GrayScaleFilterConfiguration(0d, 0.5d, 0.5d)),
                JimixFilterVariant.createBuiltin("Turkey Only", new GrayScaleFilterConfiguration(0.5d, 0d, 0.5d)),
        };
    }
}

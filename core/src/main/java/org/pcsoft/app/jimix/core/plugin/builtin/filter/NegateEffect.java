package org.pcsoft.app.jimix.core.plugin.builtin.filter;

import org.pcsoft.app.jimix.plugins.api.JimixFilter;
import org.pcsoft.app.jimix.plugins.api.annotation.JimixFilterDescriptor;
import org.pcsoft.app.jimix.plugins.api.type.JimixPixelReader;
import org.pcsoft.app.jimix.plugins.api.type.JimixPixelWriter;
import org.pcsoft.app.jimix.plugins.api.type.JimixSource;

import java.awt.*;

@JimixFilterDescriptor(name = "Negative", description = "Negate", iconPath = "/icons/ic_filter_negative16.png",
        usableForMasks = true, configurationClass = NegateEffectConfiguration.class)
public class NegateEffect implements JimixFilter<NegateEffectConfiguration> {
    @Override
    public void apply(JimixPixelReader pixelReader, JimixPixelWriter pixelWriter, NegateEffectConfiguration configuration, JimixSource applySource) {
        for (int i = 0; i < pixelReader.getLength(); i++) {
            final Color readColor = new Color(pixelReader.getPixel(i));

            final int red = 255 - readColor.getRed();
            final int green = 255 - readColor.getGreen();
            final int blue = 255 - readColor.getBlue();

            final int writeColor = new Color(red, green, blue, readColor.getAlpha()).getRGB();
            pixelWriter.setPixel(i, writeColor);
        }
    }
}

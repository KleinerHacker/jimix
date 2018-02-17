package org.pcsoft.app.jimix.core.plugin.builtin.filter;

import org.pcsoft.app.jimix.plugins.api.JimixFilter;
import org.pcsoft.app.jimix.plugins.api.annotation.JimixFilterDescriptor;
import org.pcsoft.app.jimix.plugins.api.type.JimixPixelReader;
import org.pcsoft.app.jimix.plugins.api.type.JimixPixelWriter;
import org.pcsoft.app.jimix.plugins.api.type.JimixSource;

@JimixFilterDescriptor(name = "Negative", description = "Negate", usableForMasks = true, configurationClass = NegateEffectConfiguration.class)
public class NegateEffect implements JimixFilter<NegateEffectConfiguration> {
    @Override
    public void apply(JimixPixelReader pixelReader, JimixPixelWriter pixelWriter, NegateEffectConfiguration configuration, JimixSource applySource) {
        for (int i = 0; i < pixelReader.getLength(); i++) {
            final int readColor = pixelReader.getPixel(i);
            final int writeColor = Integer.MAX_VALUE - readColor;
            pixelWriter.setPixel(i, writeColor);
        }
    }
}

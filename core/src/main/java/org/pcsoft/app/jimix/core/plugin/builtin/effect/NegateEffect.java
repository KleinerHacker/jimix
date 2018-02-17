package org.pcsoft.app.jimix.core.plugin.builtin.effect;

import org.pcsoft.app.jimix.plugins.api.JimixEffect;
import org.pcsoft.app.jimix.plugins.api.annotation.JimixEffectDescriptor;
import org.pcsoft.app.jimix.plugins.api.type.JimixApplySource;
import org.pcsoft.app.jimix.plugins.api.type.JimixPixelReader;
import org.pcsoft.app.jimix.plugins.api.type.JimixPixelWriter;

@JimixEffectDescriptor(name = "Negative", description = "Negate", usableForMasks = true, configurationClass = NegateEffectConfiguration.class)
public class NegateEffect implements JimixEffect<NegateEffectConfiguration> {
    @Override
    public void apply(JimixPixelReader pixelReader, JimixPixelWriter pixelWriter, NegateEffectConfiguration configuration, JimixApplySource applySource) {
        for (int i = 0; i < pixelReader.getLength(); i++) {
            final int readColor = pixelReader.getPixel(i);
            final int writeColor = Integer.MAX_VALUE - readColor;
            pixelWriter.setPixel(i, writeColor);
        }
    }
}

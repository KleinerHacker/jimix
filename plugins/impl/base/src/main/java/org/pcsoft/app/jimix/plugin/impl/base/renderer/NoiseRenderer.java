package org.pcsoft.app.jimix.plugin.impl.base.renderer;

import org.pcsoft.app.jimix.plugins.api.JimixRenderer;
import org.pcsoft.app.jimix.plugins.api.annotation.JimixRendererDescriptor;
import org.pcsoft.app.jimix.plugins.api.type.JimixSource;
import org.pcsoft.app.jimix.plugins.api.type.JimixPixelWriter;

import java.util.Random;

@JimixRendererDescriptor(name = "Noise", description = "Noise creation", usableForMasks = true, configurationClass = NoiseRendererConfiguration.class)
public class NoiseRenderer implements JimixRenderer<NoiseRendererConfiguration> {
    @Override
    public void apply(JimixPixelWriter pixelWriter, NoiseRendererConfiguration configuration, JimixSource source) {
        final Random random;
        if (configuration.getRandomBaseValue() != null) {
            random = new Random(configuration.getRandomBaseValue());
        } else {
            random = new Random();
        }

        for (int i = 0; i < pixelWriter.getLength(); i++) {
            final int color = random.nextInt(); //TODO: Colored / Mask
            pixelWriter.setPixel(i, color);
        }
    }
}

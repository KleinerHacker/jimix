package org.pcsoft.app.jimix.plugin.impl.base.renderer;

import org.pcsoft.app.jimix.plugins.api.JimixRendererBase;
import org.pcsoft.app.jimix.plugins.api.annotation.JimixRendererDescriptor;
import org.pcsoft.app.jimix.plugins.api.type.JimixPixelWriter;
import org.pcsoft.app.jimix.plugins.api.type.JimixSource;

import java.util.Random;

@JimixRendererDescriptor(name = "plugin.renderer.noise.title", description = "plugin.renderer.noise.description", resourceBundle = "languages/plugin",
        usableForMasks = true, configurationClass = NoiseRendererConfiguration.class)
public class NoiseRenderer extends JimixRendererBase<NoiseRendererConfiguration> {
    @Override
    protected void apply(JimixPixelWriter pixelWriter, NoiseRendererConfiguration configuration, JimixSource source) {
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

package org.pcsoft.app.jimix.plugin.impl.base.filter;

import org.pcsoft.app.jimix.plugins.api.JimixFilter;
import org.pcsoft.app.jimix.plugins.api.annotation.JimixFilterDescriptor;
import org.pcsoft.app.jimix.plugins.api.type.JimixSource;
import org.pcsoft.app.jimix.plugins.api.type.JimixPixelReader;
import org.pcsoft.app.jimix.plugins.api.type.JimixPixelWriter;

import java.awt.*;

@JimixFilterDescriptor(name = "plugin.filter.scrim_diffuser.title", description = "plugin.filter.scrim_diffuser.description", resourceBundle = "languages/plugin",
        usableForMasks = true, configurationClass = ScrimDiffuserConfiguration.class)
public class ScrimDiffuserEffect implements JimixFilter<ScrimDiffuserConfiguration> {
    @Override
    public void apply(JimixPixelReader pixelReader, JimixPixelWriter pixelWriter, ScrimDiffuserConfiguration configuration, JimixSource source) {
        for (int y = 0; y < pixelReader.getHeight(); y++) {
            for (int x = 0; x < pixelReader.getWidth(); x++) {
                final int startX = Math.max(0, x - configuration.getRadius());
                final int startY = Math.max(0, y - configuration.getRadius());
                final int endX = Math.min(pixelReader.getWidth() - 1, x + configuration.getRadius());
                final int endY = Math.min(pixelReader.getHeight() - 1, y + configuration.getRadius());

                int r = 0, g = 0, b = 0, a = 0;
                for (int yy = startY; yy <= endY; yy++) {
                    for (int xx = startX; xx <= endX; xx++) {
                        final int pixel = pixelReader.getPixel(xx, yy);
                        final Color color = new Color(pixel, true);
                        a += color.getAlpha();
                        r += color.getRed();
                        g += color.getGreen();
                        b += color.getBlue();
                    }
                }

                final int count = (endX - startX + 1) * (endY - startY + 1);
                a /= count;
                r /= count;
                g /= count;
                b /= count;

                a = Math.min(255, a);
                r = Math.min(255, r);
                g = Math.min(255, g);
                b = Math.min(255, b);

                final int color = new Color(r, g, b, a).getRGB();

                pixelWriter.setPixel(x, y, color);
            }
        }
    }
}

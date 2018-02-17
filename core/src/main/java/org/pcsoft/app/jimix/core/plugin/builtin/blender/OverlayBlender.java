package org.pcsoft.app.jimix.core.plugin.builtin.blender;

import org.pcsoft.app.jimix.plugins.api.JimixBlender;
import org.pcsoft.app.jimix.plugins.api.annotation.JimixBlenderDescriptor;
import org.pcsoft.app.jimix.plugins.api.type.JimixPixelReader;
import org.pcsoft.app.jimix.plugins.api.type.JimixPixelWriter;

import java.awt.*;

@JimixBlenderDescriptor(name = "Overlay", description = "Overlay layers (default)")
public class OverlayBlender implements JimixBlender {
    @Override
    public void apply(JimixPixelReader groundPixelReader, JimixPixelReader layerPixelReader, JimixPixelWriter pixelWriter) {
        for (int i = 0; i < groundPixelReader.getLength(); i++) {
            final Color groundColor = new Color(groundPixelReader.getPixel(i), true);
            final Color layerColor = new Color(layerPixelReader.getPixel(i), true);

            final float groundRed = (float)groundColor.getRed() / 255f;
            final float groundGreen = (float)groundColor.getGreen() / 255f;
            final float groundBlue = (float)groundColor.getBlue() / 255f;

            final float layerRed = (float)groundColor.getRed() / 255f;
            final float layerGreen = (float)groundColor.getGreen() / 255f;
            final float layerBlue = (float)groundColor.getBlue() / 255f;

            final float layerAlpha = (float) layerColor.getAlpha() / 255f;
            final float groundAlpha = 1f - layerAlpha;

            final Color mergeColor = new Color(
                    groundRed * groundAlpha + layerRed * layerAlpha,
                    groundGreen * groundAlpha + layerGreen * layerAlpha,
                    groundBlue * groundAlpha + layerBlue * layerAlpha,
                    (float)groundColor.getAlpha() / 255f * groundAlpha
            );

            pixelWriter.setPixel(i, mergeColor.getRGB());
        }
    }
}

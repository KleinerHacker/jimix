package org.pcsoft.app.jimix.core.plugin.builtin.blender;

import org.pcsoft.app.jimix.plugins.api.JimixBlender;
import org.pcsoft.app.jimix.plugins.api.annotation.JimixBlenderDescriptor;
import org.pcsoft.app.jimix.plugins.api.type.JimixPixelReader;
import org.pcsoft.app.jimix.plugins.api.type.JimixPixelWriter;

import java.awt.*;

@JimixBlenderDescriptor(name = "Overlay", description = "Overlay levels (default)")
public class OverlayBlender implements JimixBlender {
    @Override
    public void apply(JimixPixelReader groundPixelReader, JimixPixelReader levelPixelReader, JimixPixelWriter pixelWriter) {
        for (int i = 0; i < groundPixelReader.getLength(); i++) {
            final Color groundColor = new Color(groundPixelReader.getPixel(i), true);
            final Color levelColor = new Color(levelPixelReader.getPixel(i), true);

            final float groundRed = (float)groundColor.getRed() / 255f;
            final float groundGreen = (float)groundColor.getGreen() / 255f;
            final float groundBlue = (float)groundColor.getBlue() / 255f;

            final float levelRed = (float)groundColor.getRed() / 255f;
            final float levelGreen = (float)groundColor.getGreen() / 255f;
            final float levelBlue = (float)groundColor.getBlue() / 255f;

            final float levelAlpha = (float) levelColor.getAlpha() / 255f;
            final float groundAlpha = 1f - levelAlpha;

            final Color mergeColor = new Color(
                    groundRed * groundAlpha + levelRed * levelAlpha,
                    groundGreen * groundAlpha + levelGreen * levelAlpha,
                    groundBlue * groundAlpha + levelBlue * levelAlpha,
                    (float)groundColor.getAlpha() / 255f * groundAlpha
            );

            pixelWriter.setPixel(i, mergeColor.getRGB());
        }
    }
}

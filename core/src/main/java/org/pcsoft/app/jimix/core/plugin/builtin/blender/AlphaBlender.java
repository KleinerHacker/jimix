package org.pcsoft.app.jimix.core.plugin.builtin.blender;

import org.pcsoft.app.jimix.plugin.manipulation.api.JimixBlenderBase;
import org.pcsoft.app.jimix.plugin.manipulation.api.annotation.JimixBlenderDescriptor;
import org.pcsoft.app.jimix.plugin.manipulation.api.type.JimixPixelReader;
import org.pcsoft.app.jimix.plugin.manipulation.api.type.JimixPixelWriter;

import java.awt.*;

@JimixBlenderDescriptor(name = "Alpha Blend", description = "Alpha over blend", group = "Color")
public class AlphaBlender extends JimixBlenderBase {
    @Override
    protected void apply(JimixPixelReader groundPixelReader, JimixPixelReader layerPixelReader, JimixPixelWriter pixelWriter, double opacity) {
        for (int i = 0; i < groundPixelReader.getLength(); i++) {
            final Color groundColor = new Color(groundPixelReader.getPixel(i));
            final Color layerColor = new Color(layerPixelReader.getPixel(i));
            final double alphaValue = layerColor.getAlpha() / 255d;
            final Color targetColor = new Color(
                    (int)(groundColor.getRed() * (alphaValue - 1d) + layerColor.getRed() * alphaValue),
                    (int)(groundColor.getGreen() * (alphaValue - 1d) + layerColor.getGreen() * alphaValue),
                    (int)(groundColor.getBlue() * (alphaValue - 1d) + layerColor.getBlue() * alphaValue),
                    layerColor.getAlpha()
            );
            pixelWriter.setPixel(i, targetColor.getRGB());
        }
    }
}

package org.pcsoft.app.jimix.core.plugin.builtin.blender;

import org.pcsoft.app.jimix.plugins.api.JimixBlender;
import org.pcsoft.app.jimix.plugins.api.annotation.JimixBlenderDescriptor;
import org.pcsoft.app.jimix.plugins.api.type.JimixPixelReader;
import org.pcsoft.app.jimix.plugins.api.type.JimixPixelWriter;

@JimixBlenderDescriptor(name = "Add", description = "Add pictures", iconPath = "/icons/ic_blender_add16.png")
public class AddBlender implements JimixBlender {
    @Override
    public void apply(JimixPixelReader groundPixelReader, JimixPixelReader layerPixelReader, JimixPixelWriter pixelWriter) {
        for (int i = 0; i < pixelWriter.getLength(); i++) {
            final int color = (int) Math.min((long) groundPixelReader.getPixel(i) + (long) layerPixelReader.getPixel(i), (long) Integer.MAX_VALUE);
            pixelWriter.setPixel(i, color);
        }
    }
}

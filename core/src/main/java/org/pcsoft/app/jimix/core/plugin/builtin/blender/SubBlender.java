package org.pcsoft.app.jimix.core.plugin.builtin.blender;

import org.pcsoft.app.jimix.plugins.api.JimixBlender;
import org.pcsoft.app.jimix.plugins.api.annotation.JimixBlenderDescriptor;
import org.pcsoft.app.jimix.plugins.api.type.JimixPixelReader;
import org.pcsoft.app.jimix.plugins.api.type.JimixPixelWriter;

@JimixBlenderDescriptor(name = "Subtract", description = "Subtract pictures", iconPath = "/icons/ic_blender_subtract16.png")
public class SubBlender implements JimixBlender {
    @Override
    public void apply(JimixPixelReader groundPixelReader, JimixPixelReader layerPixelReader, JimixPixelWriter pixelWriter) {
        for (int i = 0; i < pixelWriter.getLength(); i++) {
            final int color = (int) Math.max((long) groundPixelReader.getPixel(i) - (long) layerPixelReader.getPixel(i), 0L);
            pixelWriter.setPixel(i, color);
        }
    }
}

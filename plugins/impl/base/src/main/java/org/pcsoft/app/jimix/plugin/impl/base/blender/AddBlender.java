package org.pcsoft.app.jimix.plugin.impl.base.blender;

import org.pcsoft.app.jimix.plugins.api.JimixBlender;
import org.pcsoft.app.jimix.plugins.api.annotation.JimixBlenderDescriptor;
import org.pcsoft.app.jimix.plugins.api.type.JimixPixelReader;
import org.pcsoft.app.jimix.plugins.api.type.JimixPixelWriter;

@JimixBlenderDescriptor(name = "Add", description = "Add pictures")
public class AddBlender implements JimixBlender {
    @Override
    public void apply(JimixPixelReader pixelReader1, JimixPixelReader pixelReader2, JimixPixelWriter pixelWriter) {
        for (int i=0; i<pixelWriter.getLength(); i++) {
            final int color = (int) Math.min((long)pixelReader1.getPixel(i) + (long)pixelReader2.getPixel(i), (long)Integer.MAX_VALUE);
            pixelWriter.setPixel(i, color);
        }
    }
}

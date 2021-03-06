package org.pcsoft.app.jimix.core.plugin.builtin.filter;


import org.pcsoft.app.jimix.plugin.manipulation.api.JimixFilterBase;
import org.pcsoft.app.jimix.plugin.manipulation.api.annotation.JimixFilterDescriptor;
import org.pcsoft.app.jimix.plugin.manipulation.api.type.JimixFilterVariant;
import org.pcsoft.app.jimix.plugin.manipulation.api.type.JimixPixelReader;
import org.pcsoft.app.jimix.plugin.manipulation.api.type.JimixPixelWriter;
import org.pcsoft.app.jimix.plugin.manipulation.api.type.JimixSource;

import java.awt.*;

@JimixFilterDescriptor(name = "Negative", description = "Negate", iconPath = "/builtin/icons/ic_filter_negative16.png",
        usableForMasks = true, configurationClass = NegateFilterConfiguration.class)
public class NegateFilter extends JimixFilterBase<NegateFilterConfiguration> {
    @Override
    protected void apply(JimixPixelReader pixelReader, JimixPixelWriter pixelWriter, NegateFilterConfiguration configuration, JimixSource applySource) {
        for (int i = 0; i < pixelReader.getLength(); i++) {
            final Color readColor = new Color(pixelReader.getPixel(i));

            final int red = 255 - readColor.getRed();
            final int green = 255 - readColor.getGreen();
            final int blue = 255 - readColor.getBlue();

            final int writeColor = new Color(red, green, blue, readColor.getAlpha()).getRGB();
            pixelWriter.setPixel(i, writeColor);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public JimixFilterVariant<NegateFilterConfiguration>[] getVariants() {
        return new JimixFilterVariant[] {
                JimixFilterVariant.createBuiltin("Default", new NegateFilterConfiguration())
        };
    }
}

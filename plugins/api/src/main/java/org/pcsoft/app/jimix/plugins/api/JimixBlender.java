package org.pcsoft.app.jimix.plugins.api;

import org.pcsoft.app.jimix.plugins.api.type.JimixPixelReader;
import org.pcsoft.app.jimix.plugins.api.type.JimixPixelWriter;

public interface JimixBlender {
    void apply(JimixPixelReader groundPixelReader, JimixPixelReader levelPixelReader, JimixPixelWriter pixelWriter);
}

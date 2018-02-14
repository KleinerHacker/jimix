package org.pcsoft.app.jimix.plugins.api;

import org.pcsoft.app.jimix.plugins.api.type.JimixPixelReader;
import org.pcsoft.app.jimix.plugins.api.type.JimixPixelWriter;

public interface JimixBlender {
    void apply(final JimixPixelReader pixelReader1, JimixPixelReader pixelReader2, JimixPixelWriter pixelWriter);
}

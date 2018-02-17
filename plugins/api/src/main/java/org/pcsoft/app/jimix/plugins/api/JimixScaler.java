package org.pcsoft.app.jimix.plugins.api;

import org.pcsoft.app.jimix.plugins.api.type.JimixSource;
import org.pcsoft.app.jimix.plugins.api.type.JimixPixelReader;
import org.pcsoft.app.jimix.plugins.api.type.JimixPixelWriter;

public interface JimixScaler {
    void apply(final JimixPixelReader pixelReader, final JimixPixelWriter pixelWriter, final JimixSource source);
}

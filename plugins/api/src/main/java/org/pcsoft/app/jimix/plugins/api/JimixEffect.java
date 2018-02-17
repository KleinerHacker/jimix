package org.pcsoft.app.jimix.plugins.api;

import org.pcsoft.app.jimix.plugins.api.config.JimixEffectConfiguration;
import org.pcsoft.app.jimix.plugins.api.type.JimixPixelReader;
import org.pcsoft.app.jimix.plugins.api.type.JimixPixelWriter;

public interface JimixEffect<T extends JimixEffectConfiguration> {
    void apply(final JimixPixelReader pixelReader, final JimixPixelWriter pixelWriter, final T configuration);
}

package org.pcsoft.app.jimix.plugins.api;

import org.pcsoft.app.jimix.plugins.api.config.JimixFilterConfiguration;
import org.pcsoft.app.jimix.plugins.api.type.JimixSource;
import org.pcsoft.app.jimix.plugins.api.type.JimixPixelReader;
import org.pcsoft.app.jimix.plugins.api.type.JimixPixelWriter;

public interface JimixFilter<T extends JimixFilterConfiguration> {
    void apply(final JimixPixelReader pixelReader, final JimixPixelWriter pixelWriter, final T configuration, final JimixSource source);
}

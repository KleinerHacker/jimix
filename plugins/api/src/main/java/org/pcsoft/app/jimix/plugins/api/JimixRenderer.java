package org.pcsoft.app.jimix.plugins.api;

import org.pcsoft.app.jimix.plugins.api.config.JimixRendererConfiguration;
import org.pcsoft.app.jimix.plugins.api.type.JimixApplySource;
import org.pcsoft.app.jimix.plugins.api.type.JimixPixelWriter;

public interface JimixRenderer<T extends JimixRendererConfiguration> {
    void apply(final JimixPixelWriter pixelWriter, final T configuration, final JimixApplySource applySource);
}

package org.pcsoft.app.jimix.plugins.api;

import javafx.scene.image.Image;
import org.pcsoft.app.jimix.plugins.api.config.JimixRendererConfiguration;
import org.pcsoft.app.jimix.plugins.api.type.JimixSource;

public interface JimixRenderer<T extends JimixRendererConfiguration> {
    Image apply(final int width, final int height, final T configuration, final JimixSource source) throws Exception;
}

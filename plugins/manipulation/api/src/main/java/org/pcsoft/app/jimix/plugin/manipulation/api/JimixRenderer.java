package org.pcsoft.app.jimix.plugin.manipulation.api;

import javafx.scene.image.Image;
import org.pcsoft.app.jimix.plugin.manipulation.api.config.JimixRendererConfiguration;
import org.pcsoft.app.jimix.plugin.manipulation.api.type.JimixSource;

public interface JimixRenderer<T extends JimixRendererConfiguration> {
    Image apply(final int width, final int height, final T configuration, final JimixSource source) throws Exception;
}

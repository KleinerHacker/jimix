package org.pcsoft.app.jimix.plugins.api;

import javafx.scene.image.Image;
import org.pcsoft.app.jimix.plugins.api.config.JimixEffectConfiguration;

public interface JimixEffect<T extends JimixEffectConfiguration> {
    Image apply(final Image image, final T configuration) throws Exception;
}

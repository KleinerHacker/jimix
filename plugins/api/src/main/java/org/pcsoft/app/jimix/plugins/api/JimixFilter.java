package org.pcsoft.app.jimix.plugins.api;

import javafx.scene.image.Image;
import org.pcsoft.app.jimix.plugins.api.config.JimixFilterConfiguration;
import org.pcsoft.app.jimix.plugins.api.type.JimixSource;

public interface JimixFilter<T extends JimixFilterConfiguration> {
    Image apply(final Image image, final T configuration, final JimixSource source) throws Exception;
}

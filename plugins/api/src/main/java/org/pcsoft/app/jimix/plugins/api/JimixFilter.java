package org.pcsoft.app.jimix.plugins.api;

import javafx.scene.image.Image;
import org.pcsoft.app.jimix.plugins.api.config.JimixFilterConfiguration;
import org.pcsoft.app.jimix.plugins.api.type.JimixFilterVariant;
import org.pcsoft.app.jimix.plugins.api.type.JimixSource;

/**
 * Represent a filter plugin interface for all filter implementations
 * @param <T> Concrete type of filter configuration
 */
public interface JimixFilter<T extends JimixFilterConfiguration> {
    /**
     * Apply the filter on the given image
     * @param image Image to apply filter on
     * @param configuration Configuration with parameters for concrete filter instance
     * @param source Type of source image
     * @return The image with applied filter
     * @throws Exception
     */
    Image apply(final Image image, final T configuration, final JimixSource source) throws Exception;

    /**
     * Returns the builtin variants for this filter. <b>No Resource Bundle is used for name.</b>
     * @return
     */
    JimixFilterVariant<T>[] getVariants();
}

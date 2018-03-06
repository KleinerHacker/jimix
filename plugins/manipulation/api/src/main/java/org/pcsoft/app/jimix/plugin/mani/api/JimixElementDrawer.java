package org.pcsoft.app.jimix.plugin.mani.api;

import javafx.scene.image.Image;
import org.pcsoft.app.jimix.plugin.mani.api.type.JimixPluginElement;

/**
 * Represent a drawer implementation for a special {@link JimixPluginElement}
 * @param <T>
 */
public interface JimixElementDrawer<T extends JimixPluginElement> {
    /**
     * Draw the {@link JimixPluginElement} into an image with given dimensions
     * @param pluginElement element to draw into image
     * @param width width setup
     * @param height height setup
     * @return
     */
    Image draw(final T pluginElement, final int width, final int height);
}

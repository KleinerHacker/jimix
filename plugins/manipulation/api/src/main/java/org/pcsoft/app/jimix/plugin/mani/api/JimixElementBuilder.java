package org.pcsoft.app.jimix.plugin.mani.api;

import javafx.scene.Node;
import org.pcsoft.app.jimix.plugin.common.api.type.JimixPluginElement;

/**
 * Represent a builder implementation for a special {@link JimixPluginElement}
 *
 * @param <T>
 */
public interface JimixElementBuilder<T extends JimixPluginElement> {
    /**
     * Build the {@link JimixPluginElement} and return it to add it to a group
     *
     * @param pluginElement element to build
     * @param x             setup
     * @param y             setup
     * @param width         width setup
     * @param height        height setup
     * @return
     */
    Node buildNode(final T pluginElement, final int x, final int y, final int width, final int height);
}

package org.pcsoft.app.jimix.plugin.manipulation.api;

import javafx.scene.Node;
import org.pcsoft.app.jimix.plugin.common.api.type.JimixElementType;
import org.pcsoft.app.jimix.plugin.common.api.type.JimixPlugin2DElement;

/**
 * Represent a builder implementation for a special {@link JimixPlugin2DElement}
 *
 * @param <T>
 */
public interface Jimix2DElementBuilder<T extends JimixPlugin2DElement> extends JimixElementBuilder {
    /**
     * Build the {@link JimixPlugin2DElement} and return it to add it to a group
     *
     * @param pluginElement element to build
     * @param x             setup
     * @param y             setup
     * @return
     */
    Node buildNode(final T pluginElement, final int x, final int y);

    @Override
    default JimixElementType getType() {
        return JimixElementType.Element2D;
    }
}

package org.pcsoft.app.jimix.plugin.manipulation.api;

import javafx.scene.Node;
import org.pcsoft.app.jimix.plugin.common.api.type.JimixElementType;
import org.pcsoft.app.jimix.plugin.common.api.type.JimixPlugin3DElement;

/**
 * Represent a builder implementation for a special {@link JimixPlugin3DElement}
 *
 * @param <T>
 */
public interface Jimix3DElementBuilder<T extends JimixPlugin3DElement> extends JimixElementBuilder {
    /**
     * Build the {@link JimixPlugin3DElement} and return it to add it to a group
     *
     * @param pluginElement element to build
     * @param x             setup
     * @param y             setup
     * @param width         width setup
     * @param height        height setup
     * @return
     */
    Node buildNode(final T pluginElement, final int x, final int y, final int width, final int height);

    @Override
    default JimixElementType getType() {
        return JimixElementType.Element3D;
    }
}

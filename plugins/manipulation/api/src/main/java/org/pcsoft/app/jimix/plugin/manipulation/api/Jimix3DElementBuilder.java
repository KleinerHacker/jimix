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
     * @return
     */
    Node buildNode(final T pluginElement);

    @Override
    default JimixElementType getType() {
        return JimixElementType.Element3D;
    }
}

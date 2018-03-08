package org.pcsoft.app.jimix.plugin.mani.api;

import javafx.scene.Node;
import org.pcsoft.app.jimix.plugin.mani.api.config.JimixEffectConfiguration;
import org.pcsoft.app.jimix.plugin.mani.api.type.JimixEffectVariant;

/**
 * Represent an effect. It is <b>not</b> a filter.
 *
 * @param <T> Special type of effect configuration
 */
public interface JimixEffect<T extends JimixEffectConfiguration> {
    /**
     * Apply the effect on given image. To build effect render effect for image on given graphics context <b>without</b> drawing image itself.
     *
     * @param node          Node to apply effect into graphic context.
     * @param x             Left position to apply node effect to
     * @param y             Top position to apply node effect to
     * @param width         Width of node
     * @param height        Height of node
     * @param configuration Configuration for effect
     * @return New node instance with applyied effect. Can be the same instance like node
     * @throws Exception
     */
    Node apply(final Node node, final int x, final int y, final int width, final int height, final T configuration) throws Exception;

    /**
     * Returns the builtin variants for this effect. <b>No Resource Bundle is used for name.</b>
     *
     * @return
     */
    JimixEffectVariant<T>[] getVariants();
}

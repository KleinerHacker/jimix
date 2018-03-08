package org.pcsoft.app.jimix.plugin.manipulation.api;

import javafx.scene.Node;
import org.pcsoft.app.jimix.plugin.manipulation.api.config.JimixEffectConfiguration;
import org.pcsoft.app.jimix.plugin.manipulation.api.type.JimixEffectType;

/**
 * Represent an effect for 2D objects. It is <b>not</b> a filter. In case of using {@link org.pcsoft.app.jimix.plugin.common.api.type.JimixPlugin3DElement}
 * it will be applied after a snapshot was taken from it.
 *
 * @param <T> Special type of effect configuration
 */
public interface Jimix2DEffect<T extends JimixEffectConfiguration<T>> extends JimixEffect<T> {
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

    @Override
    default JimixEffectType getType() {
        return JimixEffectType.Effect2D;
    }
}

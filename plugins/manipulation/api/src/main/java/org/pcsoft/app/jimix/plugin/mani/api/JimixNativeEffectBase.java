package org.pcsoft.app.jimix.plugin.mani.api;

import javafx.scene.Node;
import javafx.scene.effect.Blend;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.Effect;
import org.pcsoft.app.jimix.plugin.mani.api.config.JimixEffectConfiguration;

/**
 * Abstract base class for usage of a native JavaFX effect. Use JavaFX Blend effect to overlay effects
 *
 * @param <T>
 */
public abstract class JimixNativeEffectBase<T extends JimixEffectConfiguration<T>> implements JimixEffect<T> {
    @Override
    public Node apply(Node node, int x, int y, int width, int height, T configuration) throws Exception {
        final Effect effect = getNativeEffect(configuration, x, y, width, height);
        final Effect currentEffect = node.getEffect();

        if (currentEffect == null) {
            node.setEffect(effect);
        } else {
            final Blend blend = new Blend(getBlendMode(configuration), currentEffect, effect);
            node.setEffect(blend);
        }

        return node;
    }

    /**
     * Returns the native JavaFX effect to work with
     *
     * @param configuration
     * @param x             X position on project area to draw image
     * @param y             Y position on project area to draw image
     * @param width         With of image area (image width)
     * @param height        Height of image area (image height)
     * @return
     */
    protected abstract Effect getNativeEffect(T configuration, int x, int y, int width, int height);

    /**
     * Returns the blend mode to use for this effect appending
     *
     * @param configuration
     * @return
     */
    protected abstract BlendMode getBlendMode(T configuration);
}

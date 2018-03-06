package org.pcsoft.app.jimix.plugin.mani.api;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
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
     * @param image         Image to apply and draw effect into graphic context.
     * @param x             Left position to draw image effect to
     * @param y             Top position to draw image effect to
     * @param gc            Graphic context to draw image effect
     * @param configuration Configuration for effect
     * @throws Exception
     */
    void apply(final Image image, final int x, final int y, final GraphicsContext gc, final T configuration) throws Exception;

    /**
     * Returns the builtin variants for this effect. <b>No Resource Bundle is used for name.</b>
     * @return
     */
    JimixEffectVariant<T>[] getVariants();
}

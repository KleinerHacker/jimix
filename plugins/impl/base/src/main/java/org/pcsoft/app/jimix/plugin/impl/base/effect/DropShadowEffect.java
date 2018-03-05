package org.pcsoft.app.jimix.plugin.impl.base.effect;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import org.pcsoft.app.jimix.plugins.api.JimixEffect;
import org.pcsoft.app.jimix.plugins.api.annotation.JimixEffectDescriptor;
import org.pcsoft.app.jimix.plugins.api.type.JimixEffectVariant;

import java.util.ResourceBundle;

@JimixEffectDescriptor(name = "plugin.effect.drop_shadow.title", description = "plugin.effect.drop_shadow.description", group = "plugin.effect.group.shadow",
        resourceBundle = "base.language.plugin", configurationClass = DropShadowEffectConfiguration.class)
public class DropShadowEffect implements JimixEffect<DropShadowEffectConfiguration> {
    @Override
    public void apply(Image image, int x, int y, GraphicsContext gc, DropShadowEffectConfiguration configuration) throws Exception {
        gc.applyEffect(new DropShadow(
                configuration.getBlurType(),
                configuration.getColor(),
                configuration.getRadius(),
                configuration.getSpread(),
                configuration.getOffsetX(),
                configuration.getOffsetY()
        ));
    }

    @SuppressWarnings("unchecked")
    @Override
    public JimixEffectVariant<DropShadowEffectConfiguration>[] getVariants() {
        return new JimixEffectVariant[] {
                new JimixEffectVariant(
                        ResourceBundle.getBundle("base/language/plugin").getString("plugin.effect.variant.default"),
                        new DropShadowEffectConfiguration()
                )
        };
    }
}

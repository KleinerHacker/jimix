package org.pcsoft.app.jimix.plugin.impl.base.manipulation.effect;

import javafx.scene.effect.BlendMode;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import org.pcsoft.app.jimix.plugin.manipulation.api.JimixNativeEffectBase;
import org.pcsoft.app.jimix.plugin.manipulation.api.annotation.JimixEffectDescriptor;
import org.pcsoft.app.jimix.plugin.manipulation.api.type.JimixEffectVariant;

import java.util.ResourceBundle;

@JimixEffectDescriptor(name = "plugin.effect.drop_shadow.title", description = "plugin.effect.drop_shadow.description", group = "plugin.effect.group.shadow",
        resourceBundle = "base.language.plugin", configurationClass = DropShadowEffectConfiguration.class)
public class DropShadowEffect extends JimixNativeEffectBase<DropShadowEffectConfiguration> {
    @Override
    protected Effect getNativeEffect(DropShadowEffectConfiguration configuration, int x, int y, int width, int height) {
        return new DropShadow(
                configuration.getBlurType(),
                configuration.getColor(),
                configuration.getRadius(),
                configuration.getSpread(),
                configuration.getOffsetX(),
                configuration.getOffsetY()
        );
    }

    @SuppressWarnings("unchecked")
    @Override
    public JimixEffectVariant<DropShadowEffectConfiguration>[] getVariants() {
        return new JimixEffectVariant[] {
                JimixEffectVariant.createBuiltin(
                        ResourceBundle.getBundle("base/language/plugin").getString("plugin.effect.variant.default"),
                        new DropShadowEffectConfiguration()
                )
        };
    }

    @Override
    protected BlendMode getBlendMode(DropShadowEffectConfiguration configuration) {
        return BlendMode.DARKEN;
    }
}

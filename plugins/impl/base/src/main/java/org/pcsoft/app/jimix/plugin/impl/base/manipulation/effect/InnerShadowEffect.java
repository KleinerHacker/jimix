package org.pcsoft.app.jimix.plugin.impl.base.manipulation.effect;

import javafx.scene.effect.BlendMode;
import javafx.scene.effect.Effect;
import javafx.scene.effect.InnerShadow;
import org.pcsoft.app.jimix.plugin.manipulation.api.JimixNativeEffectBase;
import org.pcsoft.app.jimix.plugin.manipulation.api.annotation.JimixEffectDescriptor;
import org.pcsoft.app.jimix.plugin.manipulation.api.type.JimixEffectVariant;

import java.util.ResourceBundle;

@JimixEffectDescriptor(name = "plugin.effect.inner_shadow.title", description = "plugin.effect.inner_shadow.description", group = "plugin.effect.group.shadow",
        resourceBundle = "base.language.plugin", configurationClass = InnerShadowEffectConfiguration.class)
public class InnerShadowEffect extends JimixNativeEffectBase<InnerShadowEffectConfiguration> {
    @Override
    protected Effect getNativeEffect(InnerShadowEffectConfiguration configuration, int x, int y, int width, int height) {
        return new InnerShadow(
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
    public JimixEffectVariant<InnerShadowEffectConfiguration>[] getVariants() {
        return new JimixEffectVariant[] {
                JimixEffectVariant.createBuiltin(
                        ResourceBundle.getBundle("base/language/plugin").getString("plugin.effect.variant.default"),
                        new InnerShadowEffectConfiguration()
                )
        };
    }

    @Override
    protected BlendMode getBlendMode(InnerShadowEffectConfiguration configuration) {
        return BlendMode.DARKEN;
    }
}

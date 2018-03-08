package org.pcsoft.app.jimix.plugin.impl.base.manipulation.effect;

import javafx.scene.effect.BlendMode;
import javafx.scene.effect.Effect;
import javafx.scene.effect.Reflection;
import org.pcsoft.app.jimix.plugin.manipulation.api.JimixNativeEffectBase;
import org.pcsoft.app.jimix.plugin.manipulation.api.annotation.JimixEffectDescriptor;
import org.pcsoft.app.jimix.plugin.manipulation.api.type.JimixEffectVariant;

@JimixEffectDescriptor(name = "Reflection", description = "Reflection Effect",
        configurationClass = ReflectionEffectConfiguration.class)
public class ReflectionEffect extends JimixNativeEffectBase<ReflectionEffectConfiguration> {
    @Override
    protected Effect getNativeEffect(ReflectionEffectConfiguration configuration, int x, int y, int width, int height) {
        return new Reflection(
                configuration.getOffset(),
                configuration.getFraction(),
                configuration.getTopOpacity(),
                configuration.getBottomOpacity()
        );
    }

    @SuppressWarnings("unchecked")
    @Override
    public JimixEffectVariant<ReflectionEffectConfiguration>[] getVariants() {
        return new JimixEffectVariant[]{
                JimixEffectVariant.createBuiltin(
                        "Default",
                        new ReflectionEffectConfiguration()
                )
        };
    }

    @Override
    protected BlendMode getBlendMode(ReflectionEffectConfiguration configuration) {
        return BlendMode.DARKEN;
    }
}

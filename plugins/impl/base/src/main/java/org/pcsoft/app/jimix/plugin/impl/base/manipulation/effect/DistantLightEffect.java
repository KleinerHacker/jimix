package org.pcsoft.app.jimix.plugin.impl.base.manipulation.effect;

import javafx.scene.effect.BlendMode;
import javafx.scene.effect.Effect;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import org.pcsoft.app.jimix.plugin.manipulation.api.JimixNativeEffectBase;
import org.pcsoft.app.jimix.plugin.manipulation.api.annotation.JimixEffectDescriptor;
import org.pcsoft.app.jimix.plugin.manipulation.api.type.JimixEffectVariant;

import java.util.ResourceBundle;

@JimixEffectDescriptor(name = "plugin.effect.distant_light.title", description = "plugin.effect.distant_light.description", group = "plugin.effect.group.lighting",
        resourceBundle = "base.language.plugin", configurationClass = DistantLightEffectConfiguration.class)
public class DistantLightEffect extends JimixNativeEffectBase<DistantLightEffectConfiguration> {
    @Override
    protected Effect getNativeEffect(DistantLightEffectConfiguration configuration, int x, int y, int width, int height) {
        return new Lighting(new Light.Distant(
                configuration.getAzimuth(),
                configuration.getElevation(),
                configuration.getColor()
        ));
    }

    @SuppressWarnings("unchecked")
    @Override
    public JimixEffectVariant<DistantLightEffectConfiguration>[] getVariants() {
        return new JimixEffectVariant[] {
                JimixEffectVariant.createBuiltin(
                        ResourceBundle.getBundle("base/language/plugin").getString("plugin.effect.variant.default"),
                        new DistantLightEffectConfiguration()
                )
        };
    }

    @Override
    protected BlendMode getBlendMode(DistantLightEffectConfiguration configuration) {
        return BlendMode.DARKEN;
    }
}

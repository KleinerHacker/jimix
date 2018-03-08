package org.pcsoft.app.jimix.plugin.mani.impl.base.effect;

import javafx.scene.effect.BlendMode;
import javafx.scene.effect.Effect;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import org.pcsoft.app.jimix.plugin.mani.api.JimixNativeEffectBase;
import org.pcsoft.app.jimix.plugin.mani.api.annotation.JimixEffectDescriptor;
import org.pcsoft.app.jimix.plugin.mani.api.type.JimixEffectVariant;

import java.util.ResourceBundle;

@JimixEffectDescriptor(name = "plugin.effect.spot_light.title", description = "plugin.effect.spot_light.description", group = "plugin.effect.group.lighting",
        resourceBundle = "base.language.plugin", configurationClass = SpotLightEffectConfiguration.class)
public class SpotLightEffect extends JimixNativeEffectBase<SpotLightEffectConfiguration> {
    @Override
    protected Effect getNativeEffect(SpotLightEffectConfiguration configuration, int x, int y, int width, int height) {
        return new Lighting(new Light.Spot(
                configuration.getX() * width, //%
                configuration.getY() * height, //%
                configuration.getZ() * Math.max(width, height), //%
                configuration.getSpecularExponent(),
                configuration.getColor()
        ));
    }

    @SuppressWarnings("unchecked")
    @Override
    public JimixEffectVariant<SpotLightEffectConfiguration>[] getVariants() {
        return new JimixEffectVariant[]{
                JimixEffectVariant.createBuiltin(
                        ResourceBundle.getBundle("base/language/plugin").getString("plugin.effect.variant.default"),
                        new SpotLightEffectConfiguration()
                )
        };
    }

    @Override
    protected BlendMode getBlendMode(SpotLightEffectConfiguration configuration) {
        return BlendMode.DARKEN;
    }
}

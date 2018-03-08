package org.pcsoft.app.jimix.plugin.impl.base.manipulation.effect;

import javafx.scene.effect.BlendMode;
import javafx.scene.effect.Effect;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import org.pcsoft.app.jimix.plugin.manipulation.api.JimixNativeEffectBase;
import org.pcsoft.app.jimix.plugin.manipulation.api.annotation.JimixEffectDescriptor;
import org.pcsoft.app.jimix.plugin.manipulation.api.type.JimixEffectVariant;

import java.util.ResourceBundle;

@JimixEffectDescriptor(name = "plugin.effect.point_light.title", description = "plugin.effect.point_light.description", group = "plugin.effect.group.lighting",
        resourceBundle = "base.language.plugin", configurationClass = PointLightEffectConfiguration.class)
public class PointLightEffect extends JimixNativeEffectBase<PointLightEffectConfiguration> {
    @Override
    protected Effect getNativeEffect(PointLightEffectConfiguration configuration, int x, int y, int width, int height) {
        return new Lighting(new Light.Point(
                configuration.getX() * width, //%
                configuration.getY() * height, //%
                configuration.getZ() * Math.max(width, height), //%
                configuration.getColor()
        ));
    }

    @SuppressWarnings("unchecked")
    @Override
    public JimixEffectVariant<PointLightEffectConfiguration>[] getVariants() {
        return new JimixEffectVariant[]{
                JimixEffectVariant.createBuiltin(
                        ResourceBundle.getBundle("base/language/plugin").getString("plugin.effect.variant.default"),
                        new PointLightEffectConfiguration()
                )
        };
    }

    @Override
    protected BlendMode getBlendMode(PointLightEffectConfiguration configuration) {
        return BlendMode.DARKEN;
    }
}

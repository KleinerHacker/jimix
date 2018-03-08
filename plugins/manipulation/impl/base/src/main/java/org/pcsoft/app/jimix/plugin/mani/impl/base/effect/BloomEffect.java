package org.pcsoft.app.jimix.plugin.mani.impl.base.effect;

import javafx.scene.effect.BlendMode;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.Effect;
import org.pcsoft.app.jimix.plugin.mani.api.JimixNativeEffectBase;
import org.pcsoft.app.jimix.plugin.mani.api.annotation.JimixEffectDescriptor;
import org.pcsoft.app.jimix.plugin.mani.api.type.JimixEffectVariant;

import java.util.ResourceBundle;

@JimixEffectDescriptor(name = "plugin.effect.bloom.title", description = "plugin.effect.bloom.description",
        resourceBundle = "base.language.plugin", configurationClass = BloomEffectConfiguration.class)
public class BloomEffect extends JimixNativeEffectBase<BloomEffectConfiguration> {
    @Override
    protected Effect getNativeEffect(BloomEffectConfiguration configuration, int x, int y, int width, int height) {
        return new Bloom(configuration.getThreshold());
    }

    @SuppressWarnings("unchecked")
    @Override
    public JimixEffectVariant<BloomEffectConfiguration>[] getVariants() {
        return new JimixEffectVariant[] {
                JimixEffectVariant.createBuiltin(
                        ResourceBundle.getBundle("base/language/plugin").getString("plugin.effect.variant.default"),
                        new BloomEffectConfiguration()
                )
        };
    }

    @Override
    protected BlendMode getBlendMode(BloomEffectConfiguration configuration) {
        return BlendMode.DARKEN;
    }
}

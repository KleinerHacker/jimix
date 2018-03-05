package org.pcsoft.app.jimix.plugin.impl.base.effect;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.Reflection;
import javafx.scene.image.Image;
import org.pcsoft.app.jimix.plugins.api.JimixEffect;
import org.pcsoft.app.jimix.plugins.api.annotation.JimixEffectDescriptor;
import org.pcsoft.app.jimix.plugins.api.type.JimixEffectVariant;

import java.util.ResourceBundle;

@JimixEffectDescriptor(name = "Reflection", description = "Reflection Effect",
        configurationClass = ReflectionEffectConfiguration.class)
public class ReflectionEffect implements JimixEffect<ReflectionEffectConfiguration> {
    @Override
    public void apply(Image image, int x, int y, GraphicsContext gc, ReflectionEffectConfiguration configuration) throws Exception {
        gc.setEffect(new Reflection(configuration.getOffset(), configuration.getFraction(), configuration.getTopOpacity(), configuration.getBottomOpacity()));
        gc.drawImage(image, x, y);
    }

    @SuppressWarnings("unchecked")
    @Override
    public JimixEffectVariant<ReflectionEffectConfiguration>[] getVariants() {
        return new JimixEffectVariant[] {
                new JimixEffectVariant(
                        ResourceBundle.getBundle("base/language/plugin").getString("plugin.effect.variant.default"),
                        new ReflectionEffectConfiguration()
                )
        };
    }
}

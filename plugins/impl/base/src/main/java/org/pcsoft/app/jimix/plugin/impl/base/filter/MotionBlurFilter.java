package org.pcsoft.app.jimix.plugin.impl.base.filter;

import javafx.scene.canvas.Canvas;
import javafx.scene.effect.MotionBlur;
import javafx.scene.image.Image;
import org.pcsoft.app.jimix.commons.type.TransparentSnapshotParams;
import org.pcsoft.app.jimix.plugins.api.JimixFilter;
import org.pcsoft.app.jimix.plugins.api.annotation.JimixFilterDescriptor;
import org.pcsoft.app.jimix.plugins.api.type.JimixFilterVariant;
import org.pcsoft.app.jimix.plugins.api.type.JimixSource;

import java.util.ResourceBundle;

@JimixFilterDescriptor(name = "plugin.filter.motion_blur.title", description = "plugin.filter.motion_blur.description", group = "plugin.filter.group.diffuser",
        resourceBundle = "base.language.plugin", usableForMasks = true, configurationClass = MotionBlurFilterConfiguration.class)
public class MotionBlurFilter implements JimixFilter<MotionBlurFilterConfiguration> {
    @Override
    public Image apply(Image image, MotionBlurFilterConfiguration configuration, JimixSource source) throws Exception {
        final Canvas canvas = new Canvas(image.getWidth(), image.getHeight());
        canvas.setEffect(new MotionBlur(configuration.getAngle(), configuration.getRadius()));
        canvas.getGraphicsContext2D().drawImage(image, 0, 0);

        return canvas.snapshot(new TransparentSnapshotParams(), null);
    }

    @SuppressWarnings("unchecked")
    @Override
    public JimixFilterVariant<MotionBlurFilterConfiguration>[] getVariants() {
        return new JimixFilterVariant[] {
                new JimixFilterVariant<>(
                        ResourceBundle.getBundle("base/language/plugin").getString("plugin.filter.variant.default"),
                        new MotionBlurFilterConfiguration()
                )
        };
    }
}

package org.pcsoft.app.jimix.plugin.impl.base.manipulation.filter;

import javafx.scene.canvas.Canvas;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import org.pcsoft.app.jimix.commons.type.JimixSnapshotParams;
import org.pcsoft.app.jimix.plugin.manipulation.api.JimixFilter;
import org.pcsoft.app.jimix.plugin.manipulation.api.annotation.JimixFilterDescriptor;
import org.pcsoft.app.jimix.plugin.manipulation.api.type.JimixFilterVariant;
import org.pcsoft.app.jimix.plugin.manipulation.api.type.JimixSource;

import java.util.ResourceBundle;

@JimixFilterDescriptor(name = "plugin.filter.gaussian_blur.title", description = "plugin.filter.gaussian_blur.description", group = "plugin.filter.group.diffuser",
        resourceBundle = "base.language.plugin", usableForMasks = true, configurationClass = GaussianBlurFilterConfiguration.class)
public class GaussianBlurFilter implements JimixFilter<GaussianBlurFilterConfiguration> {
    @Override
    public Image apply(Image image, GaussianBlurFilterConfiguration configuration, JimixSource source) throws Exception {
        final Canvas canvas = new Canvas(image.getWidth(), image.getHeight());
        canvas.setEffect(new GaussianBlur(configuration.getRadius()));
        canvas.getGraphicsContext2D().drawImage(image, 0, 0);

        return canvas.snapshot(new JimixSnapshotParams(), null);
    }

    @SuppressWarnings("unchecked")
    @Override
    public JimixFilterVariant<GaussianBlurFilterConfiguration>[] getVariants() {
        return new JimixFilterVariant[] {
                JimixFilterVariant.createBuiltin(
                        ResourceBundle.getBundle("base/language/plugin").getString("plugin.filter.variant.default"),
                        new GaussianBlurFilterConfiguration()
                )
        };
    }
}

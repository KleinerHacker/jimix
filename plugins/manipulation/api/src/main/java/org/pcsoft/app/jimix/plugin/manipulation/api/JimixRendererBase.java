package org.pcsoft.app.jimix.plugin.manipulation.api;

import javafx.scene.image.Image;
import org.pcsoft.app.jimix.plugin.manipulation.api.config.JimixRendererConfiguration;
import org.pcsoft.app.jimix.plugin.manipulation.api.type.JimixPixelWriter;
import org.pcsoft.app.jimix.plugin.manipulation.api.type.JimixSource;
import org.pcsoft.app.jimix.plugin.manipulation.api.type.impl.JimixPixelWriterImpl;

public abstract class JimixRendererBase<T extends JimixRendererConfiguration> implements JimixRenderer<T> {
    @Override
    public final Image apply(int width, int height, T configuration, JimixSource source) {
        final JimixPixelWriterImpl pixelWriter = new JimixPixelWriterImpl(width, height);

        apply(pixelWriter, configuration, source);

        return pixelWriter.buildImage();
    }

    protected abstract void apply(final JimixPixelWriter pixelWriter, final T configuration, final JimixSource source);
}

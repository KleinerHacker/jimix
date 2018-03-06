package org.pcsoft.app.jimix.plugin.mani.api;

import javafx.scene.image.Image;
import org.pcsoft.app.jimix.plugin.mani.api.config.JimixRendererConfiguration;
import org.pcsoft.app.jimix.plugin.mani.api.type.JimixPixelWriter;
import org.pcsoft.app.jimix.plugin.mani.api.type.JimixSource;
import org.pcsoft.app.jimix.plugin.mani.api.type.impl.JimixPixelWriterImpl;

public abstract class JimixRendererBase<T extends JimixRendererConfiguration> implements JimixRenderer<T> {
    @Override
    public final Image apply(int width, int height, T configuration, JimixSource source) {
        final JimixPixelWriterImpl pixelWriter = new JimixPixelWriterImpl(width, height);

        apply(pixelWriter, configuration, source);

        return pixelWriter.buildImage();
    }

    protected abstract void apply(final JimixPixelWriter pixelWriter, final T configuration, final JimixSource source);
}

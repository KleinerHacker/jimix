package org.pcsoft.app.jimix.plugins.api;

import javafx.scene.image.Image;
import org.pcsoft.app.jimix.plugins.api.config.JimixEffectConfiguration;
import org.pcsoft.app.jimix.plugins.api.type.JimixPixelReader;
import org.pcsoft.app.jimix.plugins.api.type.JimixPixelWriter;
import org.pcsoft.app.jimix.plugins.api.type.impl.JimixPixelReaderImpl;
import org.pcsoft.app.jimix.plugins.api.type.impl.JimixPixelWriterImpl;

public abstract class JimixEffectBase<T extends JimixEffectConfiguration> implements JimixEffect<T> {
    @Override
    public final Image apply(Image image, T configuration) {
        final JimixPixelReaderImpl pixelReader = new JimixPixelReaderImpl(image.getPixelReader(), (int) image.getWidth(), (int) image.getHeight());
        final JimixPixelWriterImpl pixelWriter = new JimixPixelWriterImpl((int) image.getWidth(), (int) image.getHeight());

        apply(pixelReader, pixelWriter, configuration);

        return pixelWriter.buildImage();
    }

    protected abstract void apply(JimixPixelReader pixelReader, JimixPixelWriter pixelWriter, T configuration);
}

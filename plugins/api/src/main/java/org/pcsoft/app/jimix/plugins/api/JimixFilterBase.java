package org.pcsoft.app.jimix.plugins.api;

import javafx.scene.image.Image;
import org.pcsoft.app.jimix.plugins.api.config.JimixFilterConfiguration;
import org.pcsoft.app.jimix.plugins.api.type.JimixPixelReader;
import org.pcsoft.app.jimix.plugins.api.type.JimixPixelWriter;
import org.pcsoft.app.jimix.plugins.api.type.JimixSource;
import org.pcsoft.app.jimix.plugins.api.type.impl.JimixPixelReaderImpl;
import org.pcsoft.app.jimix.plugins.api.type.impl.JimixPixelWriterImpl;

public abstract class JimixFilterBase<T extends JimixFilterConfiguration> implements JimixFilter<T> {
    @Override
    public final Image apply(Image image, T configuration, JimixSource source) {
        final JimixPixelReaderImpl pixelReader = new JimixPixelReaderImpl(image.getPixelReader(), (int) image.getWidth(), (int) image.getHeight());
        final JimixPixelWriterImpl pixelWriter = new JimixPixelWriterImpl((int) image.getWidth(), (int) image.getHeight());

        apply(pixelReader, pixelWriter, configuration, source);

        return pixelWriter.buildImage();
    }

    protected abstract void apply(final JimixPixelReader pixelReader, final JimixPixelWriter pixelWriter, final T configuration, final JimixSource source);
}

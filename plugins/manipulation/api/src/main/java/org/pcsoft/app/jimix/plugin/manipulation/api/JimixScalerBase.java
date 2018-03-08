package org.pcsoft.app.jimix.plugin.manipulation.api;

import javafx.scene.image.Image;
import org.pcsoft.app.jimix.plugin.manipulation.api.type.JimixPixelReader;
import org.pcsoft.app.jimix.plugin.manipulation.api.type.JimixPixelWriter;
import org.pcsoft.app.jimix.plugin.manipulation.api.type.JimixSource;
import org.pcsoft.app.jimix.plugin.manipulation.api.type.impl.JimixPixelReaderImpl;
import org.pcsoft.app.jimix.plugin.manipulation.api.type.impl.JimixPixelWriterImpl;

public abstract class JimixScalerBase implements JimixScaler {
    @Override
    public final Image apply(Image image, int targetWidth, int targetHeight, JimixSource source) {
        final JimixPixelReaderImpl pixelReader = new JimixPixelReaderImpl(image.getPixelReader(), (int) image.getWidth(), (int) image.getHeight());
        final JimixPixelWriterImpl pixelWriter = new JimixPixelWriterImpl(targetWidth, targetHeight);

        apply(pixelReader, pixelWriter, source);

        return pixelWriter.buildImage();
    }

    protected abstract void apply(final JimixPixelReader pixelReader, final JimixPixelWriter pixelWriter, final JimixSource source);
}

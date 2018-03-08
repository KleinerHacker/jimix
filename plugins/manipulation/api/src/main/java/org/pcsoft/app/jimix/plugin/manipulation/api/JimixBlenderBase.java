package org.pcsoft.app.jimix.plugin.manipulation.api;

import javafx.scene.image.Image;
import org.pcsoft.app.jimix.plugin.manipulation.api.type.JimixPixelReader;
import org.pcsoft.app.jimix.plugin.manipulation.api.type.JimixPixelWriter;
import org.pcsoft.app.jimix.plugin.manipulation.api.type.impl.JimixPixelReaderImpl;
import org.pcsoft.app.jimix.plugin.manipulation.api.type.impl.JimixPixelWriterImpl;

public abstract class JimixBlenderBase implements JimixBlender {
    @Override
    public final Image apply(Image groundImage, Image layerImage, double opacity) {
        final JimixPixelReaderImpl groundPixelReader = new JimixPixelReaderImpl(groundImage.getPixelReader(), (int) groundImage.getWidth(), (int) groundImage.getHeight());
        final JimixPixelReaderImpl layerPixelReader = new JimixPixelReaderImpl(layerImage.getPixelReader(), (int) layerImage.getWidth(), (int) layerImage.getHeight());
        final JimixPixelWriterImpl pixelWriter = new JimixPixelWriterImpl((int) groundImage.getWidth(), (int) groundImage.getHeight());

        apply(groundPixelReader, layerPixelReader, pixelWriter, opacity);

        return pixelWriter.buildImage();
    }

    protected abstract void apply(JimixPixelReader groundPixelReader, JimixPixelReader layerPixelReader, JimixPixelWriter pixelWriter, double opacity);
}

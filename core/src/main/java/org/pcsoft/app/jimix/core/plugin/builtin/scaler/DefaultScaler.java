package org.pcsoft.app.jimix.core.plugin.builtin.scaler;

import javafx.scene.image.PixelFormat;
import javafx.scene.image.WritableImage;
import org.pcsoft.app.jimix.core.plugin.type.JimixPixelReaderImpl;
import org.pcsoft.app.jimix.core.plugin.type.JimixPixelWriterImpl;
import org.pcsoft.app.jimix.plugins.api.JimixScaler;
import org.pcsoft.app.jimix.plugins.api.annotation.JimixScalerDescriptor;
import org.pcsoft.app.jimix.plugins.api.type.JimixSource;
import org.pcsoft.app.jimix.plugins.api.type.JimixPixelReader;
import org.pcsoft.app.jimix.plugins.api.type.JimixPixelWriter;

@JimixScalerDescriptor(name = "Default", description = "Default JavaFX image scaling algorithm")
public class DefaultScaler implements JimixScaler {
    @Override
    public void apply(JimixPixelReader pixelReader, JimixPixelWriter pixelWriter, JimixSource source) {
        final WritableImage image = new WritableImage(pixelWriter.getWidth(), pixelWriter.getHeight());
        image.getPixelWriter().setPixels(0, 0, pixelReader.getWidth(), pixelReader.getHeight(), PixelFormat.getIntArgbInstance(),
                ((JimixPixelReaderImpl)pixelReader).getPixels(), 0, pixelReader.getWidth());

        final WritableImage scaledImage = new WritableImage(image.getPixelReader(), 0, 0, pixelWriter.getWidth(), pixelWriter.getHeight());
        final int[] buffer = new int[(int)scaledImage.getWidth() * (int)scaledImage.getHeight()];
        scaledImage.getPixelReader().getPixels(0, 0, (int)scaledImage.getWidth(), (int)scaledImage.getHeight(), PixelFormat.getIntArgbInstance(), buffer,
                0, (int)scaledImage.getWidth());
        ((JimixPixelWriterImpl)pixelWriter).setPixels(buffer);
    }
}

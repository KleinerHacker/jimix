package org.pcsoft.app.jimix.core.plugin.type;

import javafx.scene.image.Image;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import org.pcsoft.app.jimix.plugins.api.type.JimixPixelWriter;

public class JimixPixelWriterImpl extends JimixPixelReaderImpl implements JimixPixelWriter {
    public JimixPixelWriterImpl(int width, int height) {
        super(new int[width * height], width, height);
    }

    protected JimixPixelWriterImpl(PixelReader pixelReader, int width, int height) {
        super(pixelReader, width, height);
    }

    @Override
    public void setPixel(int index, int color) {
        if (index < 0 || index >= pixels.length)
            throw new IndexOutOfBoundsException("Index " + index + " is out of pixel bounds");

        pixels[index] = color;
    }

    @Override
    public void setPixel(int x, int y, int color) {
        if (x < 0 || x >= width)
            throw new IndexOutOfBoundsException("Value of X " + x + " is out of image bounds");
        if (y < 0 || y >= height)
            throw new IndexOutOfBoundsException("Value of Y " + y + " is out of image bounds");

        final int index = y * width + x;
        setPixel(index, color);
    }

    public Image buildImage() {
        final WritableImage image = new WritableImage(width, height);
        image.getPixelWriter().setPixels(0, 0, width, height, PixelFormat.getIntArgbInstance(), pixels, 0, width);

        return image;
    }
}
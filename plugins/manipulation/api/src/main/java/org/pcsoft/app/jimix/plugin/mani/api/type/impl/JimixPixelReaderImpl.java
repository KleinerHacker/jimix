package org.pcsoft.app.jimix.plugin.mani.api.type.impl;

import javafx.scene.image.PixelFormat;
import javafx.scene.image.PixelReader;
import org.pcsoft.app.jimix.plugin.mani.api.type.JimixPixelReader;

public class JimixPixelReaderImpl extends JimixPixelBase implements JimixPixelReader {
    public JimixPixelReaderImpl(PixelReader pixelReader, int width, int height) {
        super(new int[width * height], width, height);
        pixelReader.getPixels(0, 0, width, height, PixelFormat.getIntArgbInstance(), pixels, 0, width);
    }

    protected JimixPixelReaderImpl(int[] pixels, int width, int height) {
        super(pixels, width, height);
    }

    /**
     * Returns the pixel from the simple pixel array. This is a fast access
     * @param index
     * @return
     */
    @Override
    public int getPixel(int index) {
        if (index < 0 || index >= pixels.length)
            throw new IndexOutOfBoundsException("Index " + index + " is out of pixel bounds");

        return pixels[index];
    }

    @Override
    public int getPixel(int x, int y) {
        if (x < 0 || x >= width)
            throw new IndexOutOfBoundsException("Value of X " + x + " is out of image bounds");
        if (y < 0 || y >= height)
            throw new IndexOutOfBoundsException("Value of Y " + y + " is out of image bounds");

        final int index = y * width + x;
        return getPixel(index);
    }

    /**
     * Only for internal use
     * @return
     */
    public int[] getPixels() {
        return pixels;
    }
}

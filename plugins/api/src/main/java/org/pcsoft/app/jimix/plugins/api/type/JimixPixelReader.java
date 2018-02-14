package org.pcsoft.app.jimix.plugins.api.type;

public interface JimixPixelReader extends JimixPixel {
    int getPixel(int index);
    int getPixel(int x, int y);
}

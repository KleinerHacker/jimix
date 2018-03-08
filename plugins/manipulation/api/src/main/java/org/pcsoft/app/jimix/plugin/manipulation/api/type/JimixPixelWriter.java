package org.pcsoft.app.jimix.plugin.manipulation.api.type;

public interface JimixPixelWriter extends JimixPixelReader {
    void setPixel(int index, int color);
    void setPixel(int pixel,  int y, int color);
}

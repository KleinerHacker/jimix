package org.pcsoft.app.jimix.plugin.mani.api.type.impl;

import org.pcsoft.app.jimix.plugin.mani.api.type.JimixPixel;

public abstract class JimixPixelBase implements JimixPixel {
    protected final int[] pixels;
    protected final int width, height;

    protected JimixPixelBase(int[] pixels, int width, int height) {
        this.pixels = pixels;
        this.width = width;
        this.height = height;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public int getLength() {
        return pixels.length;
    }
}

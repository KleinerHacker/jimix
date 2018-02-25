package org.pcsoft.app.jimix.plugins.api;

import javafx.scene.image.Image;

import java.io.File;
import java.io.IOException;

public interface JimixFileTypeProvider {
    boolean acceptFile(final byte[] magicBytes);

    void save(final Image image, final File file) throws IOException;
    Image load(final File file) throws IOException;
}

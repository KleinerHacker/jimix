package org.pcsoft.app.jimix.plugin.system.api;

import javafx.scene.image.Image;

import java.io.File;
import java.io.IOException;

public interface JimixImageFileTypeProvider extends JimixFileTypeProvider {
    void save(final Image image, final File file) throws IOException;
    Image load(final File file) throws IOException;
}

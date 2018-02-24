package org.pcsoft.app.jimix.plugins.api;

import javafx.scene.image.Image;
import org.pcsoft.app.jimix.plugins.api.type.JimixSource;

public interface JimixScaler {
    Image apply(final Image image, final int targetWidth, int targetHeight, final JimixSource source) throws Exception;
}

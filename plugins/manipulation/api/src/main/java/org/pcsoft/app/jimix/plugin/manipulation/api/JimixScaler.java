package org.pcsoft.app.jimix.plugin.manipulation.api;

import javafx.scene.image.Image;
import org.pcsoft.app.jimix.plugin.manipulation.api.type.JimixSource;

public interface JimixScaler {
    Image apply(final Image image, final int targetWidth, int targetHeight, final JimixSource source) throws Exception;
}

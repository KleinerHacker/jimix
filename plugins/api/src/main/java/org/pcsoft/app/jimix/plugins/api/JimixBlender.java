package org.pcsoft.app.jimix.plugins.api;

import javafx.scene.image.Image;

public interface JimixBlender {
    Image apply(final Image groundImage, final Image layerImage, final double opacity) throws Exception;
}

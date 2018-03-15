package org.pcsoft.app.jimix.plugin.impl.base.util;

import javafx.scene.shape.Box;
import javafx.scene.shape.Shape3D;
import org.pcsoft.app.jimix.plugin.common.api.util.Shape3DPluginElementUtils;
import org.pcsoft.app.jimix.plugin.impl.base.model.BoxPluginElement;

public final class BoxPluginElementUtils {
    public static Shape3D buildShape(BoxPluginElement element) {
        final Shape3D shape3D = new Box(element.getWidth(), element.getHeight(), element.getDepth());
        Shape3DPluginElementUtils.buildShape(shape3D, element);
        shape3D.setTranslateX(element.getWidth() / 2);
        shape3D.setTranslateY(element.getHeight() / 2);
        shape3D.setTranslateZ(element.getDepth() / 2);

        return shape3D;
    }

    private BoxPluginElementUtils() {
    }
}

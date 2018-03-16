package org.pcsoft.app.jimix.core.util;

import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import org.pcsoft.app.jimix.core.plugin.builtin.model.RectanglePluginElement;
import org.pcsoft.app.jimix.plugin.common.api.util.Shape2DPluginElementUtils;

public final class RectanglePluginElementUtils {
    public static Shape buildShape(RectanglePluginElement element) {
        return buildShape(element, null, null);
    }

    public static Shape buildShape(RectanglePluginElement element, Integer width, Integer height) {
        final Rectangle rectangle = new Rectangle(0, 0, width == null ? element.getSize().width : width, height == null ? element.getSize().height : height);
        Shape2DPluginElementUtils.buildShape(rectangle, element);
        rectangle.setArcWidth(element.getArcSize().width);
        rectangle.setArcHeight(element.getArcSize().height);

        return rectangle;
    }

    private RectanglePluginElementUtils() {
    }
}

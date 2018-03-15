package org.pcsoft.app.jimix.core.util;

import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import org.pcsoft.app.jimix.core.plugin.builtin.model.RectanglePluginElement;
import org.pcsoft.app.jimix.plugin.common.api.util.Shape2DPluginElementUtils;

public final class RectanglePluginElementUtils {
    public static Shape buildShape(int x, int y, RectanglePluginElement element) {
        return buildShape(x, y, element, null, null);
    }

    public static Shape buildShape(int x, int y, RectanglePluginElement element, Integer width, Integer height) {
        final Rectangle rectangle = new Rectangle(x, y, width == null ? element.getSize().width : width, height == null ? element.getSize().height : height);
        Shape2DPluginElementUtils.buildShape(rectangle, element);
        rectangle.setArcWidth(element.getArcSize().width);
        rectangle.setArcHeight(element.getArcSize().height);

        return rectangle;
    }

    private RectanglePluginElementUtils() {
    }
}

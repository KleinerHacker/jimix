package org.pcsoft.app.jimix.plugin.common.api.util;

import javafx.scene.shape.Shape;
import org.pcsoft.app.jimix.plugin.common.api.type.JimixPlugin2DElement;

public final class Shape2DPluginElementUtils {
    public static void buildShape(final Shape shape, final JimixPlugin2DElement element) {
        shape.setFill(element.getFill());
        shape.setStroke(element.getStroke());
        shape.setStrokeType(element.getStrokeType());
        shape.setStrokeLineCap(element.getStrokeLineCap());
        shape.setStrokeLineJoin(element.getStrokeLineJoin());
        shape.setStrokeWidth(element.getStrokeWidth());
        shape.setStrokeDashOffset(element.getStrokeDashOffset());
        shape.setStrokeMiterLimit(element.getStrokeMiterLimit());
    }

    private Shape2DPluginElementUtils() {
    }
}

package org.pcsoft.app.jimix.plugin.impl.base.util;

import javafx.scene.shape.SVGPath;
import javafx.scene.shape.Shape;
import org.pcsoft.app.jimix.plugin.common.api.util.Shape2DPluginElementUtils;
import org.pcsoft.app.jimix.plugin.impl.base.model.TrianglePluginElement;

import java.awt.*;

public final class TrianglePluginElementUtils {
    public static Shape buildShape(TrianglePluginElement element) {
        final SVGPath svgPath = new SVGPath();
        Shape2DPluginElementUtils.buildShape(svgPath, element);
        svgPath.setContent(buildSVGPathContent(element.getSize(), element.getTop()));

        return svgPath;
    }

    private static String buildSVGPathContent(Dimension size, double top) {
        return "M" + buildSVGPathPoint(0, size.height) +
                " L" + buildSVGPathPoint(size.width, size.height) +
                " L" + buildSVGPathPoint((int) (size.width * top), 0) +
                " Z";
    }

    private static String buildSVGPathPoint(int x, int y) {
        return x + "," + y;
    }

    private TrianglePluginElementUtils() {
    }
}

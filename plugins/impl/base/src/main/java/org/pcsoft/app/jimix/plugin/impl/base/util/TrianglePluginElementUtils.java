package org.pcsoft.app.jimix.plugin.impl.base.util;

import javafx.scene.shape.SVGPath;
import javafx.scene.shape.Shape;

import java.awt.*;

public final class TrianglePluginElementUtils {
    public static Shape buildShape(Dimension size, double top) {
        final SVGPath svgPath = new SVGPath();
        svgPath.setContent(buildSVGPathContent(size, top));

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

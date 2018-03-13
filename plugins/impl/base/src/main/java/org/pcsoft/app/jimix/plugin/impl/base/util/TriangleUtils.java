package org.pcsoft.app.jimix.plugin.impl.base.util;

import javafx.scene.shape.SVGPath;
import javafx.scene.shape.Shape;

import java.awt.*;

public final class TriangleUtils {
    public static Shape buildShape(Point p1, Point p2, Point p3) {
        final SVGPath svgPath = new SVGPath();
        svgPath.setContent(buildSVGPathContent(p1, p2, p3));

        return svgPath;
    }

    public static String buildSVGPathContent(Point p1, Point p2, Point p3) {
        return "M" + buildSVGPathPoint(p1) + " L" + buildSVGPathPoint(p2) + " L" + buildSVGPathPoint(p3) + " Z";
    }

    private static String buildSVGPathPoint(Point p) {
        return p.x + "," + p.y;
    }

    private TriangleUtils() {
    }
}

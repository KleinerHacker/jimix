package org.pcsoft.app.jimix.plugin.impl.base.util;

import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Shape;

import java.awt.*;

public final class EllipsePluginElementUtils {
    public static Shape buildShape(int x, int y, Dimension size) {
        return new Ellipse(x, y, size.width, size.height);
    }

    private EllipsePluginElementUtils() {
    }
}

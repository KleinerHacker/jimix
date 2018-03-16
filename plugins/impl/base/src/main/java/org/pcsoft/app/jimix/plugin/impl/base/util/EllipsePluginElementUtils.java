package org.pcsoft.app.jimix.plugin.impl.base.util;

import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Shape;
import org.pcsoft.app.jimix.plugin.common.api.util.Shape2DPluginElementUtils;
import org.pcsoft.app.jimix.plugin.impl.base.model.EllipsePluginElement;

public final class EllipsePluginElementUtils {
    public static Shape buildShape(EllipsePluginElement element) {
        final Ellipse ellipse = new Ellipse(0, 0, element.getSize().width, element.getSize().height);
        Shape2DPluginElementUtils.buildShape(ellipse, element);

        return ellipse;
    }

    private EllipsePluginElementUtils() {
    }
}

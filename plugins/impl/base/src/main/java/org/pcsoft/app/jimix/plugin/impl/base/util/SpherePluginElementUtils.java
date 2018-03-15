package org.pcsoft.app.jimix.plugin.impl.base.util;

import javafx.scene.shape.Shape3D;
import javafx.scene.shape.Sphere;
import org.pcsoft.app.jimix.plugin.common.api.util.Shape3DPluginElementUtils;
import org.pcsoft.app.jimix.plugin.impl.base.model.SpherePluginElement;

public final class SpherePluginElementUtils {
    public static Shape3D buildShape(SpherePluginElement element) {
        final Shape3D shape3D = new Sphere(element.getRadius(), element.getDivisions());
        Shape3DPluginElementUtils.buildShape(shape3D, element);
        shape3D.setTranslateX(element.getRadius() / 2);
        shape3D.setTranslateZ(element.getRadius() / 2);
        shape3D.setTranslateY(element.getRadius() / 2);

        return shape3D;
    }

    private SpherePluginElementUtils() {
    }
}

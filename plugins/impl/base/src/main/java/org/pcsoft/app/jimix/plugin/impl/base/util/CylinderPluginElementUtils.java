package org.pcsoft.app.jimix.plugin.impl.base.util;

import javafx.scene.shape.Cylinder;
import javafx.scene.shape.Shape3D;
import org.pcsoft.app.jimix.plugin.common.api.util.Shape3DPluginElementUtils;
import org.pcsoft.app.jimix.plugin.impl.base.model.CylinderPluginElement;

public final class CylinderPluginElementUtils {
    public static Shape3D buildShape(CylinderPluginElement element) {
        final Shape3D shape3D = new Cylinder(element.getRadius(), element.getHeight(), element.getDivisions());
        Shape3DPluginElementUtils.buildShape(shape3D, element);
        shape3D.setTranslateX(element.getRadius() / 2);
        shape3D.setTranslateZ(element.getRadius() / 2);
        shape3D.setTranslateY(element.getHeight() / 2);

        return shape3D;
    }

    private CylinderPluginElementUtils() {
    }
}

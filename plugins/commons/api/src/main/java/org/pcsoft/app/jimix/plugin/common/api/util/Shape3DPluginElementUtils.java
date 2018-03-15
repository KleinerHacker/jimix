package org.pcsoft.app.jimix.plugin.common.api.util;

import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Shape3D;
import org.pcsoft.app.jimix.plugin.common.api.type.JimixPlugin3DElement;

public final class Shape3DPluginElementUtils {
    public static void buildShape(final Shape3D shape, final JimixPlugin3DElement element) {
        final PhongMaterial material = new PhongMaterial();
        material.setDiffuseColor(element.getDiffuseColor());
        material.setDiffuseMap(element.getDiffuseImage());
        shape.setMaterial(material);

        shape.setDrawMode(element.getDrawMode());
        shape.setCullFace(element.getCullFace());
    }

    private Shape3DPluginElementUtils() {
    }
}

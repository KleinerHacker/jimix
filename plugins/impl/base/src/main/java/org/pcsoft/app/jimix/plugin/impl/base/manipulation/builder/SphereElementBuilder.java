package org.pcsoft.app.jimix.plugin.impl.base.manipulation.builder;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Shape3D;
import javafx.scene.shape.Sphere;
import org.pcsoft.app.jimix.plugin.impl.base.model.SpherePluginElement;
import org.pcsoft.app.jimix.plugin.manipulation.api.Jimix3DElementBuilder;
import org.pcsoft.app.jimix.plugin.manipulation.api.annotation.JimixElementBuilderDescriptor;

@JimixElementBuilderDescriptor(name = "Sphere", description = "3D Sphere object", group = "3D Objects", iconPath = "/base/icons/ic_element_sphere16.png",
        manualAddable = true, elementModelClass = SpherePluginElement.class)
public class SphereElementBuilder implements Jimix3DElementBuilder<SpherePluginElement> {
    @Override
    public Node buildNode(SpherePluginElement pluginElement) {
        final Group group = new Group();

        final Shape3D shape3D = new Sphere(pluginElement.getRadius(), pluginElement.getDivisions());
        shape3D.setMaterial(new PhongMaterial(Color.WHITE));
        shape3D.setTranslateX(pluginElement.getRadius() / 2);
        shape3D.setTranslateY(pluginElement.getRadius() / 2);
        shape3D.setDrawMode(pluginElement.getDrawMode());
        group.getChildren().add(shape3D);

        return group;
    }
}

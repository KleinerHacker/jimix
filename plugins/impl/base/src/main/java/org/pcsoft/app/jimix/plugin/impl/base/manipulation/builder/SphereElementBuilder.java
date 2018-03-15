package org.pcsoft.app.jimix.plugin.impl.base.manipulation.builder;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.shape.Shape3D;
import org.pcsoft.app.jimix.plugin.impl.base.model.SpherePluginElement;
import org.pcsoft.app.jimix.plugin.impl.base.util.SpherePluginElementUtils;
import org.pcsoft.app.jimix.plugin.manipulation.api.Jimix3DElementBuilder;
import org.pcsoft.app.jimix.plugin.manipulation.api.annotation.JimixElementBuilderDescriptor;

@JimixElementBuilderDescriptor(name = "Sphere", description = "3D Sphere object", group = "3D Objects", iconPath = "/base/icons/ic_element_sphere16.png",
        manualAddable = true, elementModelClass = SpherePluginElement.class)
public class SphereElementBuilder implements Jimix3DElementBuilder<SpherePluginElement> {
    @Override
    public Node buildNode(SpherePluginElement pluginElement) {
        final Group group = new Group();

        final Shape3D shape3D = SpherePluginElementUtils.buildShape(pluginElement);
        group.getChildren().add(shape3D);

        return group;
    }
}

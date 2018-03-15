package org.pcsoft.app.jimix.plugin.impl.base.manipulation.builder;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.shape.Shape3D;
import org.pcsoft.app.jimix.plugin.impl.base.model.CylinderPluginElement;
import org.pcsoft.app.jimix.plugin.impl.base.util.CylinderPluginElementUtils;
import org.pcsoft.app.jimix.plugin.manipulation.api.Jimix3DElementBuilder;
import org.pcsoft.app.jimix.plugin.manipulation.api.annotation.JimixElementBuilderDescriptor;

@JimixElementBuilderDescriptor(name = "Cylinder", description = "3D Cylinder object", group = "3D Objects", iconPath = "/base/icons/ic_element_cylinder16.png",
        manualAddable = true, elementModelClass = CylinderPluginElement.class)
public class CylinderElementBuilder implements Jimix3DElementBuilder<CylinderPluginElement> {
    @Override
    public Node buildNode(CylinderPluginElement pluginElement) {
        final Group group = new Group();

        final Shape3D shape3D = CylinderPluginElementUtils.buildShape(pluginElement);
        group.getChildren().add(shape3D);

        return group;
    }
}

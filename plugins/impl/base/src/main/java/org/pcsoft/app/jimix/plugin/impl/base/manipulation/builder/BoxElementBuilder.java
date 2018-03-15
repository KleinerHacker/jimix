package org.pcsoft.app.jimix.plugin.impl.base.manipulation.builder;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.shape.Shape3D;
import org.pcsoft.app.jimix.plugin.impl.base.model.BoxPluginElement;
import org.pcsoft.app.jimix.plugin.impl.base.util.BoxPluginElementUtils;
import org.pcsoft.app.jimix.plugin.manipulation.api.Jimix3DElementBuilder;
import org.pcsoft.app.jimix.plugin.manipulation.api.annotation.JimixElementBuilderDescriptor;

@JimixElementBuilderDescriptor(name = "Box", description = "3D Box object", group = "3D Objects", iconPath = "/base/icons/ic_element_box16.png",
        manualAddable = true, elementModelClass = BoxPluginElement.class)
public class BoxElementBuilder implements Jimix3DElementBuilder<BoxPluginElement> {
    @Override
    public Node buildNode(BoxPluginElement pluginElement) {
        final Group group = new Group();

        final Shape3D shape3D = BoxPluginElementUtils.buildShape(pluginElement);
        group.getChildren().add(shape3D);

        return group;
    }
}

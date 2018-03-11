package org.pcsoft.app.jimix.plugin.impl.base.manipulation.builder;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Shape3D;
import org.pcsoft.app.jimix.plugin.impl.base.model.BoxPluginElement;
import org.pcsoft.app.jimix.plugin.manipulation.api.Jimix3DElementBuilder;
import org.pcsoft.app.jimix.plugin.manipulation.api.annotation.JimixElementBuilderDescriptor;

@JimixElementBuilderDescriptor(name = "Box", description = "3D Box object", group = "3D Objects", iconPath = "/base/icons/ic_element_box16.png",
        manualAddable = true, elementModelClass = BoxPluginElement.class)
public class BoxElementBuilder implements Jimix3DElementBuilder<BoxPluginElement> {
    @Override
    public Node buildNode(BoxPluginElement pluginElement) {
        final Group group = new Group();

        final Shape3D shape3D = new Box(pluginElement.getWidth(), pluginElement.getHeight(), pluginElement.getDepth());
        shape3D.setMaterial(new PhongMaterial(Color.WHITE));
        shape3D.setTranslateX(pluginElement.getWidth() / 2);
        shape3D.setTranslateY(pluginElement.getHeight() / 2);
        shape3D.setDrawMode(pluginElement.getDrawMode());
        group.getChildren().add(shape3D);

        return group;
    }
}

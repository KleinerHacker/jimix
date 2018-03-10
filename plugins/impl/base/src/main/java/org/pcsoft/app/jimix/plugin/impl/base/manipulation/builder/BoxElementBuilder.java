package org.pcsoft.app.jimix.plugin.impl.base.manipulation.builder;

import javafx.scene.AmbientLight;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.DrawMode;
import org.pcsoft.app.jimix.plugin.impl.base.model.BoxPluginElement;
import org.pcsoft.app.jimix.plugin.manipulation.api.Jimix3DElementBuilder;
import org.pcsoft.app.jimix.plugin.manipulation.api.annotation.JimixElementBuilderDescriptor;

@JimixElementBuilderDescriptor(name = "Box", description = "3D Box object", group = "3D Objects", iconPath = "/base/icons/ic_builder_box16.png",
        manualAddable = true, elementModelClass = BoxPluginElement.class)
public class BoxElementBuilder implements Jimix3DElementBuilder<BoxPluginElement> {
    @Override
    public Node buildNode(BoxPluginElement pluginElement) {
        final Group group = new Group();

        final Box box = new Box(pluginElement.getWidth(), pluginElement.getHeight(), pluginElement.getDepth());
        box.setMaterial(new PhongMaterial(Color.WHITE));
        box.setTranslateX(pluginElement.getWidth() / 2);
        box.setTranslateY(pluginElement.getHeight() / 2);
        box.setDrawMode(DrawMode.FILL);
        group.getChildren().add(box);

        final AmbientLight light = new AmbientLight(Color.WHITE);
//        light.setTranslateX(box.getWidth() * pluginElement.getLightPositionX() * 1.2d - box.getWidth() * 0.1);
//        light.setTranslateX(box.getHeight() * pluginElement.getLightPositionY() * 1.2d - box.getHeight() * 0.1);
//        light.setTranslateX(box.getDepth() * pluginElement.getLightPositionZ() * 1.2d - box.getDepth() * 0.1);
        light.setTranslateX(pluginElement.getLightPositionX());
        light.setTranslateX(pluginElement.getLightPositionY());
        light.setTranslateX(pluginElement.getLightPositionZ());
        group.getChildren().add(light);
        group.getChildren().add(new AmbientLight(Color.WHITE));

        return group;
    }
}

package org.pcsoft.app.jimix.plugin.impl.base.manipulation.builder;

import javafx.scene.Node;
import org.pcsoft.app.jimix.plugin.impl.base.model.TrianglePluginElement;
import org.pcsoft.app.jimix.plugin.impl.base.util.TrianglePluginElementUtils;
import org.pcsoft.app.jimix.plugin.manipulation.api.Jimix2DElementBuilder;
import org.pcsoft.app.jimix.plugin.manipulation.api.annotation.JimixElementBuilderDescriptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@JimixElementBuilderDescriptor(name = "Triangle", description = "Add a triangle element", group = "2D", iconPath = "/base/icons/ic_element_triangle16.png",
        elementModelClass = TrianglePluginElement.class, manualAddable = true)
public class TriangleElementBuilder implements Jimix2DElementBuilder<TrianglePluginElement> {
    private static final Logger LOGGER = LoggerFactory.getLogger(TrianglePluginElement.class);

    @Override
    public Node buildNode(TrianglePluginElement elementModel) {
        return TrianglePluginElementUtils.buildShape(elementModel);
    }
}

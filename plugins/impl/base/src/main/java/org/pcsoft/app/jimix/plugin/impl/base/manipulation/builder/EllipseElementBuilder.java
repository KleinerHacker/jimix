package org.pcsoft.app.jimix.plugin.impl.base.manipulation.builder;

import javafx.scene.Node;
import javafx.scene.shape.Shape;
import org.pcsoft.app.jimix.plugin.impl.base.model.EllipsePluginElement;
import org.pcsoft.app.jimix.plugin.impl.base.util.EllipsePluginElementUtils;
import org.pcsoft.app.jimix.plugin.manipulation.api.Jimix2DElementBuilder;
import org.pcsoft.app.jimix.plugin.manipulation.api.annotation.JimixElementBuilderDescriptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@JimixElementBuilderDescriptor(name = "Ellipse", description = "Add an ellipse element", group = "2D", iconPath = "/base/icons/ic_element_circle16.png",
        elementModelClass = EllipsePluginElement.class, manualAddable = true)
public class EllipseElementBuilder implements Jimix2DElementBuilder<EllipsePluginElement> {
    private static final Logger LOGGER = LoggerFactory.getLogger(EllipsePluginElement.class);

    @Override
    public Node buildNode(EllipsePluginElement elementModel, final int x, final int y) {
        final Shape shape = EllipsePluginElementUtils.buildShape(x, y, elementModel.getSize());
        shape.setFill(elementModel.getFill());

        return shape;
    }
}

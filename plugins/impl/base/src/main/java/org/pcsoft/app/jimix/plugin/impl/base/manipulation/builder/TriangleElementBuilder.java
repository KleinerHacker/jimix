package org.pcsoft.app.jimix.plugin.impl.base.manipulation.builder;

import javafx.scene.Node;
import javafx.scene.shape.Shape;
import org.pcsoft.app.jimix.plugin.impl.base.model.TrianglePluginElement;
import org.pcsoft.app.jimix.plugin.impl.base.util.TriangleUtils;
import org.pcsoft.app.jimix.plugin.manipulation.api.Jimix2DElementBuilder;
import org.pcsoft.app.jimix.plugin.manipulation.api.annotation.JimixElementBuilderDescriptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@JimixElementBuilderDescriptor(name = "Triangle", description = "Add a triangle element", group = "2D", iconPath = "/base/icons/ic_element_triangle16.png",
        elementModelClass = TrianglePluginElement.class, manualAddable = true)
public class TriangleElementBuilder implements Jimix2DElementBuilder<TrianglePluginElement> {
    private static final Logger LOGGER = LoggerFactory.getLogger(TrianglePluginElement.class);

    @Override
    public Node buildNode(TrianglePluginElement elementModel, final int x, final int y) {
        /*JimixScalerInstance scaler = elementModel.getScaler();
        if (scaler == null) {
            LOGGER.warn("No scaler set for image element, use default");
            try {
                scaler = new JimixScalerPlugin(new DefaultScaler()).createInstance(); //Default as fallback
            } catch (JimixPluginException e) {
                throw new RuntimeException(e);
            }
        }

        Image scaledImage;
        try {
            scaledImage = scaler.apply(elementModel.getValue(), width, height, JimixSource.Picture);
        } catch (JimixPluginExecutionException e) {
            LOGGER.error("unable to scale image", e);
            scaledImage = elementModel.getValue(); //Ignore scaling, use builtin JavaFX Scaling
        } */

        final Shape shape = TriangleUtils.buildShape(elementModel.getPoint1(), elementModel.getPoint2(), elementModel.getPoint3());
        shape.setTranslateX(x);
        shape.setTranslateY(y);
        shape.setFill(elementModel.getFill());

        return shape;
    }
}

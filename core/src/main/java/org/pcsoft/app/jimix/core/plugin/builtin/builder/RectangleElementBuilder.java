package org.pcsoft.app.jimix.core.plugin.builtin.builder;

import javafx.scene.Node;
import org.pcsoft.app.jimix.core.plugin.builtin.model.RectanglePluginElement;
import org.pcsoft.app.jimix.core.util.RectanglePluginElementUtils;
import org.pcsoft.app.jimix.plugin.manipulation.api.Jimix2DElementBuilder;
import org.pcsoft.app.jimix.plugin.manipulation.api.annotation.JimixElementBuilderDescriptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@JimixElementBuilderDescriptor(name = "Rectangle", description = "Add a rectangle element", group = "2D", iconPath = "/builtin/icons/ic_element_rect16.png",
        elementModelClass = RectanglePluginElement.class, manualAddable = true)
public class RectangleElementBuilder implements Jimix2DElementBuilder<RectanglePluginElement> {
    private static final Logger LOGGER = LoggerFactory.getLogger(RectangleElementBuilder.class);

    @Override
    public Node buildNode(RectanglePluginElement elementModel) {
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

        return RectanglePluginElementUtils.buildShape(elementModel);
    }
}

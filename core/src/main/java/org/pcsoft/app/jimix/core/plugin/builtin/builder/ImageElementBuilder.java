package org.pcsoft.app.jimix.core.plugin.builtin.builder;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import org.pcsoft.app.jimix.commons.exception.JimixPluginException;
import org.pcsoft.app.jimix.commons.exception.JimixPluginExecutionException;
import org.pcsoft.app.jimix.core.plugin.builtin.model.ImagePluginElement;
import org.pcsoft.app.jimix.core.plugin.builtin.scaler.DefaultScaler;
import org.pcsoft.app.jimix.plugin.mani.api.JimixElementBuilder;
import org.pcsoft.app.jimix.plugin.mani.api.annotation.JimixElementBuilderDescriptor;
import org.pcsoft.app.jimix.plugin.mani.api.type.JimixSource;
import org.pcsoft.app.jimix.plugin.mani.manager.type.JimixScalerInstance;
import org.pcsoft.app.jimix.plugin.mani.manager.type.JimixScalerPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@JimixElementBuilderDescriptor(name = "Image", description = "Add an image element",
        elementModelClass = ImagePluginElement.class)
public class ImageElementBuilder implements JimixElementBuilder<ImagePluginElement> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ImageElementBuilder.class);

    @Override
    public Node buildNode(ImagePluginElement elementModel, final int x, final int y, final int width, final int height) {
        JimixScalerInstance scaler = elementModel.getScaler();
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
        }

        final Rectangle rectangle = new Rectangle(x, y, width, height);
        rectangle.setFill(new ImagePattern(scaledImage));

        return rectangle;
    }
}

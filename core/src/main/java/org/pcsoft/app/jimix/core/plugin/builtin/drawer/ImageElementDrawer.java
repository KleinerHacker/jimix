package org.pcsoft.app.jimix.core.plugin.builtin.drawer;

import javafx.scene.image.Image;
import org.pcsoft.app.jimix.commons.exception.JimixPluginException;
import org.pcsoft.app.jimix.commons.exception.JimixPluginExecutionException;
import org.pcsoft.app.jimix.core.plugin.builtin.model.JimixImagePluginElement;
import org.pcsoft.app.jimix.core.plugin.builtin.scaler.DefaultScaler;
import org.pcsoft.app.jimix.plugins.manager.type.JimixScalerInstance;
import org.pcsoft.app.jimix.plugins.manager.type.JimixScalerPlugin;
import org.pcsoft.app.jimix.plugins.api.JimixElementDrawer;
import org.pcsoft.app.jimix.plugins.api.annotation.JimixElementDrawerDescriptor;
import org.pcsoft.app.jimix.plugins.api.type.JimixSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@JimixElementDrawerDescriptor(elementModelClass = JimixImagePluginElement.class)
public class ImageElementDrawer implements JimixElementDrawer<JimixImagePluginElement> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ImageElementDrawer.class);

    @Override
    public Image draw(JimixImagePluginElement elementModel, final int width, final int height) {
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

        return scaledImage;
    }
}

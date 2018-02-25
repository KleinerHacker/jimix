package org.pcsoft.app.jimix.core.util;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import org.pcsoft.app.jimix.commons.exception.JimixPluginException;
import org.pcsoft.app.jimix.commons.exception.JimixPluginExecutionException;
import org.pcsoft.app.jimix.core.plugin.builtin.blender.OverlayBlender;
import org.pcsoft.app.jimix.core.plugin.builtin.model.JimixImageElementModel;
import org.pcsoft.app.jimix.core.plugin.builtin.scaler.DefaultScaler;
import org.pcsoft.app.jimix.core.plugin.type.*;
import org.pcsoft.app.jimix.core.project.JimixElement;
import org.pcsoft.app.jimix.core.project.JimixLayer;
import org.pcsoft.app.jimix.core.project.JimixProject;
import org.pcsoft.app.jimix.plugins.api.model.JimixElementModel;
import org.pcsoft.app.jimix.plugins.api.type.JimixSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ImageBuilder {
    private static final Logger LOGGER = LoggerFactory.getLogger(ImageBuilder.class);
    private static final ImageBuilder instance = new ImageBuilder();

    public static ImageBuilder getInstance() {
        return instance;
    }

    private ImageBuilder() {
    }

    public Image buildProjectImage(final JimixProject project) {
        Image image = new WritableImage(project.getModel().getWidth(), project.getModel().getHeight());
        for (final JimixLayer layer : project.getLayerList()) {
            if (!layer.getModel().isVisibility())
                continue;

            JimixBlenderInstance blender = layer.getModel().getBlender();
            if (blender == null) {
                LOGGER.warn("No blender set for layer " + layer.getUuid() + ", use default");
                try {
                    blender = new JimixBlenderPlugin(new OverlayBlender()).createInstance(); //Default as fallback
                } catch (JimixPluginException e) {
                    throw new RuntimeException(e);
                }
            }
            final Image layerImage = layer.getResultImage();

            try {
                image = blender.apply(image, layerImage, layer.getModel().getOpacity());
            } catch (JimixPluginExecutionException e) {
                LOGGER.error("Unable to run blender", e);
            }
        }

        return image;
    }

    public Image buildLayerImage(final JimixLayer layer) {
        final WritableImage image = new WritableImage(layer.getProject().getModel().getWidth(), layer.getProject().getModel().getHeight());

        final Canvas canvas = new Canvas(layer.getProject().getModel().getWidth(), layer.getProject().getModel().getHeight());
        final GraphicsContext gc = canvas.getGraphicsContext2D();
        for (final JimixElement element : layer.getElementList()) {
            if (!element.getModel().isVisibility())
                continue;

            gc.setGlobalAlpha(element.getModel().getOpacity());

            final JimixElementModel model = element.getModel();
            if (model instanceof JimixImageElementModel) {
                final JimixImageElementModel jimixImageElementModel = (JimixImageElementModel) model;
                JimixScalerInstance scaler = jimixImageElementModel.getScaler();
                if (scaler == null) {
                    LOGGER.warn("No scaler set for image element " + element.getUuid() + ", use default");
                    try {
                        scaler = new JimixScalerPlugin(new DefaultScaler()).createInstance(); //Default as fallback
                    } catch (JimixPluginException e) {
                        throw new RuntimeException(e);
                    }
                }

                Image scaledImage;
                try {
                    scaledImage = scaler.apply(jimixImageElementModel.getValue(), model.getWidth(), model.getHeight(), JimixSource.Picture);
                } catch (JimixPluginExecutionException e) {
                    LOGGER.error("unable to scale image", e);
                    scaledImage = jimixImageElementModel.getValue(); //Ignore scaling, use builtin JavaFX Scaling
                }
                gc.drawImage(scaledImage, jimixImageElementModel.getX(), jimixImageElementModel.getY(),
                        jimixImageElementModel.getWidth(), jimixImageElementModel.getHeight());
            }
        }
        final SnapshotParameters snapshotParameters = new SnapshotParameters();
        snapshotParameters.setFill(Color.TRANSPARENT);
        canvas.snapshot(snapshotParameters, image);

        Image resultImage = image;
        for (final JimixFilterInstance filterInstance : layer.getModel().getFilterList()) {
            try {
                resultImage = filterInstance.apply(resultImage, JimixSource.Picture);
            } catch (JimixPluginExecutionException e) {
                LOGGER.error("Unable to run filter", e);
            }
        }

        return resultImage;
    }
}

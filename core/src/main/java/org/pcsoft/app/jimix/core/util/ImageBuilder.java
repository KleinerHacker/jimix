package org.pcsoft.app.jimix.core.util;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import org.pcsoft.app.jimix.commons.exception.JimixPluginException;
import org.pcsoft.app.jimix.commons.exception.JimixPluginExecutionException;
import org.pcsoft.app.jimix.core.plugin.PluginManager;
import org.pcsoft.app.jimix.core.plugin.builtin.blender.OverlayBlender;
import org.pcsoft.app.jimix.core.plugin.type.JimixBlenderInstance;
import org.pcsoft.app.jimix.core.plugin.type.JimixFilterInstance;
import org.pcsoft.app.jimix.core.project.*;
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
            JimixBlenderInstance blender = PluginManager.getInstance().getBlender(layer.getModel().getBlender());
            if (blender == null) {
                LOGGER.warn("Unable to get blender " + layer.getModel().getBlender() + ", use default instead");
                try {
                    blender = new JimixBlenderInstance(new OverlayBlender()); //Default as fallback
                } catch (JimixPluginException e) {
                    throw new RuntimeException(e);
                }
            }
            final Image layerImage = layer.getResultImage();

            /*final JimixPixelWriterImpl pixelWriter = new JimixPixelWriterImpl(project.getModel().getWidth(), project.getModel().getHeight());
            blender.apply(
                    new JimixPixelReaderImpl(image.getPixelReader(), (int) image.getWidth(), (int) image.getHeight()),
                    new JimixPixelReaderImpl(layerImage.getPixelReader(), (int) layerImage.getWidth(), (int) layerImage.getHeight()),
                    pixelWriter
            );

            image = pixelWriter.buildImage();*/
            image = layerImage;
        }

        return image;
    }

    public Image buildLayerImage(final JimixLayer layer) {
        final WritableImage image = new WritableImage(layer.getProject().getModel().getWidth(), layer.getProject().getModel().getHeight());

        final Canvas canvas = new Canvas(layer.getProject().getModel().getWidth(), layer.getProject().getModel().getHeight());
        final GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        for (final JimixElement element : layer.getElementList()) {
            final JimixElementModel model = element.getModel();
            if (model instanceof JimixImageElementModel) {
                graphicsContext.drawImage(((JimixImageElementModel) model).getValue(), element.getModel().getX(), element.getModel().getY(),
                        element.getModel().getWidth(), element.getModel().getHeight());
            }
        }
        canvas.snapshot(new SnapshotParameters(), image);

        Image resultImage = image;
        for (final String filterClassName : layer.getModel().getFilterList()) {
            final JimixFilterInstance jimixFilterInstance = PluginManager.getInstance().getFilter(filterClassName);
            if (jimixFilterInstance == null) {
                LOGGER.warn("Unable to find filter " + filterClassName + ", ignore");
                continue;
            }

            try {
                resultImage = jimixFilterInstance.apply(resultImage, JimixSource.Picture);
            } catch (JimixPluginExecutionException e) {
                LOGGER.error("Unable to run filter", e);
            }
        }

        return resultImage;
    }
}

package org.pcsoft.app.jimix.core.image;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import org.pcsoft.app.jimix.commons.exception.JimixPluginException;
import org.pcsoft.app.jimix.core.plugin.PluginManager;
import org.pcsoft.app.jimix.core.plugin.builtin.blender.OverlayBlender;
import org.pcsoft.app.jimix.core.plugin.type.JimixBlenderInstance;
import org.pcsoft.app.jimix.core.plugin.type.JimixPixelReaderImpl;
import org.pcsoft.app.jimix.core.plugin.type.JimixPixelWriterImpl;
import org.pcsoft.app.jimix.core.project.*;
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
        for (final JimixLevel level : project.getLevelList()) {
            JimixBlenderInstance blender = PluginManager.getInstance().getBlender(level.getModel().getBlender());
            if (blender == null) {
                LOGGER.warn("Unable to get blender " + level.getModel().getBlender() + ", use default instead");
                try {
                    blender = new JimixBlenderInstance(new OverlayBlender()); //Default as fallback
                } catch (JimixPluginException e) {
                    throw new RuntimeException(e);
                }
            }
            final Image levelImage = level.getResultImage();

            /*final JimixPixelWriterImpl pixelWriter = new JimixPixelWriterImpl(project.getModel().getWidth(), project.getModel().getHeight());
            blender.apply(
                    new JimixPixelReaderImpl(image.getPixelReader(), (int) image.getWidth(), (int) image.getHeight()),
                    new JimixPixelReaderImpl(levelImage.getPixelReader(), (int) levelImage.getWidth(), (int) levelImage.getHeight()),
                    pixelWriter
            );

            image = pixelWriter.buildImage();*/
            image = levelImage;
        }

        return image;
    }

    public Image buildLevelImage(final JimixLevel level) {
        final WritableImage image = new WritableImage(level.getProject().getModel().getWidth(), level.getProject().getModel().getHeight());

        final Canvas canvas = new Canvas(level.getProject().getModel().getWidth(), level.getProject().getModel().getHeight());
        final GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        graphicsContext.setFill(Color.RED);
        graphicsContext.fillRect(0, 0, 100, 100);
        for (final JimixElement element : level.getElementList()) {
            final JimixElementModel model = element.getModel();
            if (model instanceof JimixImageElementModel) {
                graphicsContext.drawImage(((JimixImageElementModel) model).getValue(), element.getModel().getX(), element.getModel().getY(),
                        element.getModel().getWidth(), element.getModel().getHeight());
            }
        }
        canvas.snapshot(new SnapshotParameters(), image);

        return image;
    }
}

package org.pcsoft.app.jimix.core.util;

import javafx.geometry.Point3D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import org.pcsoft.app.jimix.commons.exception.JimixPluginException;
import org.pcsoft.app.jimix.commons.exception.JimixPluginExecutionException;
import org.pcsoft.app.jimix.commons.type.TransparentSnapshotParams;
import org.pcsoft.app.jimix.core.plugin.builtin.blender.OverlayBlender;
import org.pcsoft.app.jimix.core.project.JimixElement;
import org.pcsoft.app.jimix.core.project.JimixLayer;
import org.pcsoft.app.jimix.core.project.JimixProject;
import org.pcsoft.app.jimix.plugin.mani.api.type.JimixSource;
import org.pcsoft.app.jimix.plugin.mani.manager.ManipulationPluginManager;
import org.pcsoft.app.jimix.plugin.mani.manager.type.*;
import org.pcsoft.app.jimix.project.JimixElementModel;
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
            if (!element.getModel().isVisibility()) {
                LOGGER.trace("element " + element.getUuid() + " invisible");
                continue;
            }

            drawElement(element, gc);
        }
        canvas.snapshot(new TransparentSnapshotParams(), image);

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

    public Image buildElementImage(final JimixElement element) {
        final Canvas canvas = new Canvas(element.getModel().getWidth(), element.getModel().getHeight());
        drawElement(element, canvas.getGraphicsContext2D());

        return canvas.snapshot(new TransparentSnapshotParams(), null);
    }

    private void drawElement(JimixElement element, GraphicsContext gc) {
        gc.setGlobalAlpha(element.getModel().getOpacity());

        final JimixElementModel model = element.getModel();
        final JimixElementDrawerPlugin elementDrawer = ManipulationPluginManager.getInstance().getElementDrawer(model.getPluginElement().getClass());
        if (elementDrawer == null) {
            LOGGER.error("unable to draw element " + element.getUuid() + ": no element drawer found, skip");
            return;
        }

        try {
            final Image elementImage = elementDrawer.draw(model.getPluginElement(), model.getWidth(), model.getHeight());
            final Image elementImageWithViewSettings = drawImageWithViewSettings(element, elementImage);
            final WritableImage elementImageWithEffects = drawImageWithEffects(element, gc.getCanvas().getWidth(), gc.getCanvas().getHeight(), elementImageWithViewSettings);

            //Draw result image (original project size)
            gc.drawImage(elementImageWithEffects, 0, 0);
        } catch (JimixPluginExecutionException e) {
            LOGGER.error("unable to draw element " + element.getUuid() + ": execution error of plugin, skip", e);
        }
    }

    private WritableImage drawImageWithEffects(JimixElement element, double width, double height, Image elementImageWithViewSettings) throws JimixPluginExecutionException {
        //Draw image with effects (original project size)
        final Canvas effectCanvas = new Canvas(width, height);
        final GraphicsContext effectGc = effectCanvas.getGraphicsContext2D();
        effectGc.drawImage(elementImageWithViewSettings, element.getModel().getX(), element.getModel().getY());
        //Draw effects on / outside image
        for (final JimixEffectInstance instance : element.getModel().getEffectList()) {
            instance.apply(elementImageWithViewSettings, element.getModel().getX(), element.getModel().getY(), effectGc);
        }
        return effectCanvas.snapshot(new TransparentSnapshotParams(), null);
    }

    private Image drawImageWithViewSettings(JimixElement element, Image elementImage) {
        final Canvas elementCanvas = new Canvas(elementImage.getWidth(), elementImage.getHeight());
        final GraphicsContext elementGc = elementCanvas.getGraphicsContext2D();
        if (element.getModel().isMirrorHorizontal()) {
            elementCanvas.setScaleX(-1);
        }
        if (element.getModel().isMirrorVertical()) {
            elementCanvas.setScaleY(-1);
        }
        if (element.getModel().getRotation() != 0d) {
            elementCanvas.setRotate(element.getModel().getRotation());
            elementCanvas.setRotationAxis(new Point3D(0, 0, 1));
        }
        elementGc.drawImage(elementImage, 0, 0, element.getModel().getWidth(), element.getModel().getHeight());
        return elementCanvas.snapshot(new TransparentSnapshotParams(), null);
    }
}

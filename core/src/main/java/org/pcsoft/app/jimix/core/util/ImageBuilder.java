package org.pcsoft.app.jimix.core.util;

import javafx.geometry.Point3D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
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

        final Pane pane = new Pane();
        for (final JimixElement element : layer.getElementList()) {
            if (!element.getModel().isVisibility()) {
                LOGGER.trace("element " + element.getUuid() + " invisible");
                continue;
            }

            buildElementNode(element, pane);
        }
        pane.snapshot(new TransparentSnapshotParams(), image);

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
        final Pane pane = new Pane();
        buildElementNode(element, pane);

        return pane.snapshot(new TransparentSnapshotParams(), null);
    }

    private void buildElementNode(JimixElement element, Pane pane) {
        final JimixElementModel model = element.getModel();
        final JimixElementBuilderPlugin elementBuilder = ManipulationPluginManager.getInstance().getElementBuilder(model.getPluginElement().getClass());
        if (elementBuilder == null) {
            LOGGER.error("unable to build element " + element.getUuid() + ": no element builder found, skip");
            return;
        }

        try {
            final Node elementNode = elementBuilder.buildNode(model.getPluginElement(), model.getX(), model.getY(), model.getWidth(), model.getHeight());
            elementNode.setOpacity(model.getOpacity());
            applyViewSettings(element, elementNode);
            final Node effectNode = applyEffects(element, elementNode);

            //Draw result image (original project size)
            pane.getChildren().add(effectNode);
        } catch (JimixPluginExecutionException e) {
            LOGGER.error("unable to draw element " + element.getUuid() + ": execution error of plugin, skip", e);
        }
    }

    private Node applyEffects(JimixElement element, Node elementNode) throws JimixPluginExecutionException {
        //Draw effects on / outside image
        for (final JimixEffectInstance instance : element.getModel().getEffectList()) {
            elementNode = instance.apply(elementNode, element.getModel().getX(), element.getModel().getY(), element.getModel().getWidth(), element.getModel().getHeight());
        }
        return elementNode;
    }

    private void applyViewSettings(JimixElement element, Node elementNode) {
        if (element.getModel().isMirrorHorizontal()) {
            elementNode.setScaleX(-1);
        }
        if (element.getModel().isMirrorVertical()) {
            elementNode.setScaleY(-1);
        }
        if (element.getModel().getRotation() != 0d) {
            elementNode.setRotate(element.getModel().getRotation());
            elementNode.setRotationAxis(new Point3D(0, 0, 1));
        }
    }
}

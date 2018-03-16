package org.pcsoft.app.jimix.core.util;

import javafx.geometry.Point3D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import org.pcsoft.app.jimix.commons.exception.JimixPluginException;
import org.pcsoft.app.jimix.commons.exception.JimixPluginExecutionException;
import org.pcsoft.app.jimix.commons.type.JimixSnapshotParams;
import org.pcsoft.app.jimix.core.plugin.builtin.blender.OverlayBlender;
import org.pcsoft.app.jimix.core.project.JimixElement;
import org.pcsoft.app.jimix.core.project.JimixLayer;
import org.pcsoft.app.jimix.core.project.JimixPictureElement;
import org.pcsoft.app.jimix.core.project.JimixProject;
import org.pcsoft.app.jimix.plugin.common.api.type.JimixPlugin2DElement;
import org.pcsoft.app.jimix.plugin.common.api.type.JimixPlugin3DElement;
import org.pcsoft.app.jimix.plugin.manipulation.api.type.JimixSource;
import org.pcsoft.app.jimix.plugin.manipulation.manager.ManipulationPluginManager;
import org.pcsoft.app.jimix.plugin.manipulation.manager.type.*;
import org.pcsoft.app.jimix.project.JimixPictureElementModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;

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
            if (!layer.getVisible())
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
            final Image layerImage = layer.getResultPicture();

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

        final AnchorPane pane = new AnchorPane();
        //Elements
        for (final JimixPictureElement element : layer.getPictureElementList()) {
            if (!element.getVisible()) {
                LOGGER.trace("picture element " + element.getUuid() + " invisible");
                continue;
            }

            buildPictureElementNode(element, pane);
        }
        pane.snapshot(new JimixSnapshotParams(layer.getModel().getBackground(), new Dimension(layer.getProject().getModel().getWidth(), layer.getProject().getModel().getHeight())), image);

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

    public Image buildPictureElementImage(final JimixPictureElement element) {
        final AnchorPane pane = new AnchorPane();
        buildPictureElementNode(element, pane);

        return pane.snapshot(new JimixSnapshotParams(), null);
    }

    private void buildPictureElementNode(JimixPictureElement element, AnchorPane pane) {
        final JimixPictureElementModel model = element.getModel();
        final JimixElementBuilderPlugin elementBuilder = ManipulationPluginManager.getInstance().getElementBuilder(model.getPluginElement().getClass());
        if (elementBuilder == null) {
            LOGGER.error("unable to build picture element " + element.getUuid() + ": no element builder found, skip");
            return;
        }

        try {
            final Node elementNode;
            if (elementBuilder instanceof Jimix2DElementBuilderPlugin) {
                final Node tmpNode = ((Jimix2DElementBuilderPlugin) elementBuilder).buildNode((JimixPlugin2DElement) model.getPluginElement());
                AnchorPane.setLeftAnchor(tmpNode, (double)model.getX());
                AnchorPane.setTopAnchor(tmpNode, (double)model.getY());
                applyViewSettings(element, tmpNode);
                elementNode = applyEffects(element, tmpNode);
            } else if (elementBuilder instanceof Jimix3DElementBuilderPlugin) {
                Node tmpNode = ((Jimix3DElementBuilderPlugin) elementBuilder).buildNode((JimixPlugin3DElement) model.getPluginElement());
                tmpNode = apply3DEffects(element, tmpNode); //Run 3D effects first on original 3D object
                final Image tmpImage = tmpNode.snapshot(new JimixSnapshotParams(), null); //Create temporary image from 3D object
                final ImageView tmpImageNode = new ImageView(tmpImage); //Temporary node
                //tmpImageNode.setTranslateX(model.getX());
                //tmpImageNode.setTranslateY(model.getY());
                AnchorPane.setLeftAnchor(tmpImageNode, (double)model.getX());
                AnchorPane.setTopAnchor(tmpImageNode, (double)model.getY());
                applyViewSettings(element, tmpImageNode);
                elementNode = apply2DEffects(element, tmpImageNode);
            } else
                throw new RuntimeException("Unknown element builder: " + elementBuilder.getClass().getName());

            //Draw result image (original project size)
            pane.getChildren().add(elementNode);
        } catch (JimixPluginExecutionException e) {
            LOGGER.error("unable to draw picture element " + element.getUuid() + ": execution error of plugin, skip", e);
        }
    }

    private Node applyEffects(JimixPictureElement element, Node elementNode) throws JimixPluginExecutionException {
        //Draw effects on / outside image
        for (final JimixEffectInstance instance : element.getModel().getEffectList()) {
            if (instance instanceof Jimix2DEffectInstance) {
                elementNode = ((Jimix2DEffectInstance) instance).apply(elementNode,
                        element.getModel().getX(), element.getModel().getY());
            } else if (instance instanceof Jimix3DEffectInstance) {
                elementNode = ((Jimix3DEffectInstance) instance).apply(elementNode,
                        element.getModel().getX(), element.getModel().getY());
            } else
                throw new RuntimeException();
        }
        return elementNode;
    }

    private Node apply2DEffects(JimixPictureElement element, Node elementNode) throws JimixPluginExecutionException {
        //Draw effects on / outside image
        for (final JimixEffectInstance instance : element.getModel().getEffectList()) {
            if (instance instanceof Jimix2DEffectInstance) {
                elementNode = ((Jimix2DEffectInstance) instance).apply(elementNode,
                        element.getModel().getX(), element.getModel().getY());
            }
        }
        return elementNode;
    }

    private Node apply3DEffects(JimixPictureElement element, Node elementNode) throws JimixPluginExecutionException {
        //Draw effects on / outside image
        for (final JimixEffectInstance instance : element.getModel().getEffectList()) {
            if (instance instanceof Jimix3DEffectInstance) {
                elementNode = ((Jimix3DEffectInstance) instance).apply(elementNode,
                        element.getModel().getX(), element.getModel().getY());
            }
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
        elementNode.setOpacity(element.getModel().getOpacity());
    }
}

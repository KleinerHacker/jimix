package org.pcsoft.app.jimix.core.util;

import javafx.geometry.Point3D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import org.apache.commons.lang.ArrayUtils;
import org.pcsoft.app.jimix.commons.exception.JimixPluginException;
import org.pcsoft.app.jimix.commons.exception.JimixPluginExecutionException;
import org.pcsoft.app.jimix.commons.type.TransparentSnapshotParams;
import org.pcsoft.app.jimix.core.plugin.builtin.blender.OverlayBlender;
import org.pcsoft.app.jimix.core.project.JimixElement;
import org.pcsoft.app.jimix.core.project.JimixLayer;
import org.pcsoft.app.jimix.core.project.JimixProject;
import org.pcsoft.app.jimix.plugin.common.api.type.JimixPlugin2DElement;
import org.pcsoft.app.jimix.plugin.common.api.type.JimixPlugin3DElement;
import org.pcsoft.app.jimix.plugin.manipulation.api.type.JimixEffectType;
import org.pcsoft.app.jimix.plugin.manipulation.api.type.JimixSource;
import org.pcsoft.app.jimix.plugin.manipulation.manager.ManipulationPluginManager;
import org.pcsoft.app.jimix.plugin.manipulation.manager.type.*;
import org.pcsoft.app.jimix.project.JimixElementModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

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
            final Node elementNode;
            if (elementBuilder instanceof Jimix2DElementBuilderPlugin) {
                final Node tmpNode = ((Jimix2DElementBuilderPlugin) elementBuilder).buildNode((JimixPlugin2DElement) model.getPluginElement(),
                        model.getX(), model.getY(), model.getWidth(), model.getHeight());
                applyViewSettings(element, tmpNode);
                elementNode = applyEffects(element, tmpNode);
            } else if (elementBuilder instanceof Jimix3DElementBuilderPlugin) {
                Node tmpNode = ((Jimix3DElementBuilderPlugin) elementBuilder).buildNode((JimixPlugin3DElement) model.getPluginElement(),
                        model.getX(), model.getY(), model.getWidth(), model.getHeight());
                tmpNode = apply3DEffects(element, tmpNode); //Run 3D effects first on original 3D object
                final Image tmpImage = tmpNode.snapshot(new TransparentSnapshotParams(), null); //Create temporary image from 3D object
                final ImageView tmpImageNode = new ImageView(tmpImage); //Temporary node
                applyViewSettings(element, tmpImageNode);
                elementNode = apply2DEffects(element, tmpImageNode);
            } else
                throw new RuntimeException("Unknown element builder: " + elementBuilder.getClass().getName());

            //Draw result image (original project size)
            pane.getChildren().add(elementNode);
        } catch (JimixPluginExecutionException e) {
            LOGGER.error("unable to draw element " + element.getUuid() + ": execution error of plugin, skip", e);
        }
    }

    private Node applyEffects(JimixElement element, Node elementNode) throws JimixPluginExecutionException {
        //Draw effects on / outside image
        for (final JimixEffectInstance instance : element.getModel().getEffectList()) {
            if (instance instanceof Jimix2DEffectInstance) {
                elementNode = ((Jimix2DEffectInstance) instance).apply(elementNode,
                        element.getModel().getX(), element.getModel().getY(), element.getModel().getWidth(), element.getModel().getHeight());
            } else if (instance instanceof Jimix3DEffectInstance) {
                elementNode = ((Jimix3DEffectInstance) instance).apply(elementNode,
                        element.getModel().getX(), element.getModel().getY(), element.getModel().getWidth(), element.getModel().getHeight());
            } else
                throw new RuntimeException();
        }
        return elementNode;
    }

    private Node apply2DEffects(JimixElement element, Node elementNode) throws JimixPluginExecutionException {
        //Draw effects on / outside image
        for (final JimixEffectInstance instance : element.getModel().getEffectList()) {
            if (instance instanceof Jimix2DEffectInstance) {
                elementNode = ((Jimix2DEffectInstance) instance).apply(elementNode,
                        element.getModel().getX(), element.getModel().getY(), element.getModel().getWidth(), element.getModel().getHeight());
            }
        }
        return elementNode;
    }

    private Node apply3DEffects(JimixElement element, Node elementNode) throws JimixPluginExecutionException {
        //Draw effects on / outside image
        for (final JimixEffectInstance instance : element.getModel().getEffectList()) {
            if (instance instanceof Jimix3DEffectInstance) {
                elementNode = ((Jimix3DEffectInstance) instance).apply(elementNode,
                        element.getModel().getX(), element.getModel().getY(), element.getModel().getWidth(), element.getModel().getHeight());
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
    }
}

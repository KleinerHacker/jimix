package org.pcsoft.app.jimix.core.util;

import javafx.geometry.Point3D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import org.pcsoft.app.jimix.commons.exception.JimixPluginException;
import org.pcsoft.app.jimix.commons.exception.JimixPluginExecutionException;
import org.pcsoft.app.jimix.commons.type.JimixSnapshotParams;
import org.pcsoft.app.jimix.core.plugin.builtin.blender.OverlayBlender;
import org.pcsoft.app.jimix.core.plugin.builtin.filter.GrayScaleFilter;
import org.pcsoft.app.jimix.core.plugin.builtin.filter.GrayScaleFilterConfiguration;
import org.pcsoft.app.jimix.core.project.*;
import org.pcsoft.app.jimix.plugin.common.api.type.JimixPlugin2DElement;
import org.pcsoft.app.jimix.plugin.common.api.type.JimixPlugin3DElement;
import org.pcsoft.app.jimix.plugin.manipulation.api.type.JimixSource;
import org.pcsoft.app.jimix.plugin.manipulation.manager.ManipulationPluginManager;
import org.pcsoft.app.jimix.plugin.manipulation.manager.type.*;
import org.pcsoft.app.jimix.project.JimixElementModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.util.List;

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

    public Image buildLayerPictureImage(final JimixLayer layer) {
        final WritableImage image = new WritableImage(layer.getProject().getModel().getWidth(), layer.getProject().getModel().getHeight());

        final AnchorPane pane = new AnchorPane();
        //Elements
        for (final JimixPictureElement element : layer.getPictureElementList()) {
            if (!element.getVisible()) {
                LOGGER.trace("picture element " + element.getUuid() + " invisible");
                continue;
            }

            buildElementNode(element, pane);
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

    public Image buildLayerMaskImage(final JimixLayer layer) {
        final WritableImage image = new WritableImage(layer.getProject().getModel().getWidth(), layer.getProject().getModel().getHeight());

        final AnchorPane pane = new AnchorPane();
        //Elements
        for (final JimixMaskElement element : layer.getMaskElementList()) {
            if (!element.getVisible()) {
                LOGGER.trace("mask element " + element.getUuid() + " invisible");
                continue;
            }

            buildElementNode(element, pane);
        }
        pane.snapshot(new JimixSnapshotParams(layer.getModel().getBackground(), new Dimension(layer.getProject().getModel().getWidth(), layer.getProject().getModel().getHeight())), image);

        Image resultImage = image;
        try {
            final JimixFilterInstance instance = new JimixFilterPlugin(new GrayScaleFilter()).createInstance();
            ((GrayScaleFilterConfiguration)instance.getConfiguration()).setUseAsOpacity(true);
            ((GrayScaleFilterConfiguration)instance.getConfiguration()).setTargetColor(Color.RED);
            resultImage = instance.apply(resultImage, JimixSource.Mask);

            /*final Canvas canvas = new Canvas(layer.getProject().getModel().getWidth(), layer.getProject().getModel().getHeight());
            final GraphicsContext gc = canvas.getGraphicsContext2D();
            CheckerBoardPatternUtils.buildPattern(gc, canvas.getWidth(), canvas.getHeight(), Color.LIGHTPINK, Color.PINK);
            final WritableImage checkerBoardPatternImage = canvas.snapshot(new JimixSnapshotParams(), null);

            final JimixBlenderInstance blenderInstance = new JimixBlenderPlugin(new AlphaBlender()).createInstance();
            resultImage = blenderInstance.apply(checkerBoardPatternImage, resultImage, 1d);*/
        } catch (JimixPluginExecutionException e) {
            LOGGER.error("Unable to run filter", e);
        }

        return resultImage;
    }

    public Image buildPictureElementImage(final JimixPictureElement element) {
        final AnchorPane pane = new AnchorPane();
        buildElementNode(element, pane);

        return pane.snapshot(new JimixSnapshotParams(), null);
    }

    private void buildElementNode(JimixElement element, AnchorPane pane) {
        final JimixElementModel model = element.getModel();
        final JimixElementBuilderPlugin elementBuilder = ManipulationPluginManager.getInstance().getElementBuilder(model.getPluginElement().getClass());
        if (elementBuilder == null) {
            LOGGER.error("unable to build picture element " + element.getUuid() + ": no element builder found, skip");
            return;
        }

        try {
            final Node elementNode;
            if (elementBuilder instanceof Jimix2DElementBuilderPlugin) {
                final Node tmpNode = ((Jimix2DElementBuilderPlugin) elementBuilder).buildNode((JimixPlugin2DElement) model.getPluginElement());
                AnchorPane.setLeftAnchor(tmpNode, (double) model.getX());
                AnchorPane.setTopAnchor(tmpNode, (double) model.getY());
                applyViewSettings(element, tmpNode);
                if (element instanceof JimixPictureElement) {
                    elementNode = applyEffects(element.getModel().getX(), element.getModel().getY(), ((JimixPictureElement) element).getModel().getEffectList(), tmpNode);
                } else if (element instanceof JimixMaskElement) {
                    elementNode = applyEffects(element.getModel().getX(), element.getModel().getY(), ((JimixMaskElement) element).getModel().getEffectList(), tmpNode);
                } else
                    throw new RuntimeException();
            } else if (elementBuilder instanceof Jimix3DElementBuilderPlugin) {
                Node tmpNode = ((Jimix3DElementBuilderPlugin) elementBuilder).buildNode((JimixPlugin3DElement) model.getPluginElement());
                //Run 3D effects first on original 3D object
                if (element instanceof JimixPictureElement) {
                    tmpNode = apply3DEffects(element.getModel().getX(), element.getModel().getY(), ((JimixPictureElement) element).getModel().getEffectList(), tmpNode);
                } else if (element instanceof JimixMaskElement) {
                    tmpNode = apply3DEffects(element.getModel().getX(), element.getModel().getY(), ((JimixMaskElement) element).getModel().getEffectList(), tmpNode);
                } else
                    throw new RuntimeException();
                final Image tmpImage = tmpNode.snapshot(new JimixSnapshotParams(), null); //Create temporary image from 3D object
                final ImageView tmpImageNode = new ImageView(tmpImage); //Temporary node
                //tmpImageNode.setTranslateX(model.getX());
                //tmpImageNode.setTranslateY(model.getY());
                AnchorPane.setLeftAnchor(tmpImageNode, (double) model.getX());
                AnchorPane.setTopAnchor(tmpImageNode, (double) model.getY());
                applyViewSettings(element, tmpImageNode);
                //Run 2D effects last on snapshots
                if (element instanceof JimixPictureElement) {
                    elementNode = apply2DEffects(element.getModel().getX(), element.getModel().getY(), ((JimixPictureElement) element).getModel().getEffectList(), tmpImageNode);
                } else if (element instanceof JimixMaskElement) {
                    elementNode = apply2DEffects(element.getModel().getX(), element.getModel().getY(), ((JimixMaskElement) element).getModel().getEffectList(), tmpImageNode);
                    //TODO: Apply Filter
                } else
                    throw new RuntimeException();
            } else
                throw new RuntimeException("Unknown element builder: " + elementBuilder.getClass().getName());

            //Draw result image (original project size)
            pane.getChildren().add(elementNode);
        } catch (JimixPluginExecutionException e) {
            LOGGER.error("unable to draw picture element " + element.getUuid() + ": execution error of plugin, skip", e);
        }
    }

    private Node applyEffects(int x, int y, List<JimixEffectInstance> effectList, Node elementNode) throws JimixPluginExecutionException {
        //Draw effects on / outside image
        for (final JimixEffectInstance instance : effectList) {
            if (instance instanceof Jimix2DEffectInstance) {
                elementNode = ((Jimix2DEffectInstance) instance).apply(elementNode, x, y);
            } else if (instance instanceof Jimix3DEffectInstance) {
                elementNode = ((Jimix3DEffectInstance) instance).apply(elementNode, x, y);
            } else
                throw new RuntimeException();
        }
        return elementNode;
    }

    private Node apply2DEffects(int x, int y, List<JimixEffectInstance> effectList, Node elementNode) throws JimixPluginExecutionException {
        //Draw effects on / outside image
        for (final JimixEffectInstance instance : effectList) {
            if (instance instanceof Jimix2DEffectInstance) {
                elementNode = ((Jimix2DEffectInstance) instance).apply(elementNode, x, y);
            }
        }
        return elementNode;
    }

    private Node apply3DEffects(int x, int y, List<JimixEffectInstance> effectList, Node elementNode) throws JimixPluginExecutionException {
        //Draw effects on / outside image
        for (final JimixEffectInstance instance : effectList) {
            if (instance instanceof Jimix3DEffectInstance) {
                elementNode = ((Jimix3DEffectInstance) instance).apply(elementNode, x, y);
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

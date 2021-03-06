package org.pcsoft.app.jimix.core.project;

import javafx.scene.image.Image;
import javafx.scene.input.Clipboard;
import javafx.scene.paint.ImagePattern;
import org.pcsoft.app.jimix.core.plugin.builtin.blender.OverlayBlender;
import org.pcsoft.app.jimix.core.plugin.builtin.model.RectanglePluginElement;
import org.pcsoft.app.jimix.plugin.common.api.type.JimixPlugin2DElement;
import org.pcsoft.app.jimix.plugin.common.api.type.JimixPluginElement;
import org.pcsoft.app.jimix.plugin.system.manager.type.JimixClipboardProviderInstance;
import org.pcsoft.app.jimix.project.JimixLayerModel;
import org.pcsoft.app.jimix.project.JimixMaskElementModel;
import org.pcsoft.app.jimix.project.JimixPictureElementModel;
import org.pcsoft.app.jimix.project.JimixProjectModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class ProjectManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProjectManager.class);
    private static final ProjectManager instance = new ProjectManager();

    public static ProjectManager getInstance() {
        return instance;
    }

    private final Map<UUID, JimixProject> projectMap = new HashMap<>();

    private ProjectManager() {
    }

    //<editor-fold desc="Project">
    public JimixProject createProjectNative(final JimixProjectModel model) {
        return createProjectNative(model, true);
    }

    public JimixProject createProjectNative(final JimixProjectModel model, boolean addToMap) {
        final JimixProject jimixProject = new JimixProject(model);
        if (addToMap) {
            projectMap.put(jimixProject.getUuid(), jimixProject);
        }
        LOGGER.info("Create native project " + jimixProject.getUuid());

        return jimixProject;
    }

    public JimixProject createProjectFromImage(final Image image) {
        LOGGER.info("Create project from image");

        //Create empty project
        final JimixProject project = this.createEmptyProject((int) image.getWidth(), (int) image.getHeight());
        //Create base layer
        final JimixLayer layer = this.createLayerForProject(project);
        //Create image element
        final RectanglePluginElement rectanglePluginElement = new RectanglePluginElement(new ImagePattern(image));
        rectanglePluginElement.setSize(new Dimension((int) image.getWidth(), (int) image.getHeight()));
        final JimixElement element = this.createPictureElementForLayer(layer, rectanglePluginElement);

        return project;
    }

    public JimixProject createEmptyProject(final int width, final int height) {
        LOGGER.info("Create empty project");

        final JimixProjectModel model = new JimixProjectModel(width, height);
        final JimixProject jimixProject = this.createProjectNative(model);

        return jimixProject;
    }

    public boolean isProjectLoaded(final JimixProject project) {
        return isProjectLoaded(project.getUuid());
    }

    public boolean isProjectLoaded(final UUID projectUUID) {
        return projectMap.containsKey(projectUUID);
    }

    public boolean closeProject(final JimixProject project) {
        return closeProject(project.getUuid());
    }

    public boolean closeProject(final UUID projectUUID) {
        if (!projectMap.containsKey(projectUUID))
            return false;

        LOGGER.info("Close project " + projectUUID);
        projectMap.remove(projectUUID);
        return true;
    }

    public JimixProject getProject(final UUID projectUUID) {
        if (!projectMap.containsKey(projectUUID))
            throw new IllegalArgumentException("Project with UUID " + projectUUID + " not found!");

        return projectMap.get(projectUUID);
    }
    //</editor-fold>

    //<editor-fold desc="Layer">
    public JimixLayer createLayerForProject(final JimixProject project) {
        return createLayerForProject(project.getUuid());
    }

    public JimixLayer createLayerForProject(final UUID projectUUID) {
        if (!projectMap.containsKey(projectUUID))
            throw new IllegalArgumentException("Project with UUID " + projectUUID + " not found!");

        final JimixProject project = projectMap.get(projectUUID);
        final JimixLayerModel model = new JimixLayerModel(new OverlayBlender());
        final JimixLayer layer = new JimixLayer(project, model);
        LOGGER.info("Create layer " + layer.getUuid() + " for project " + projectUUID);
        project.getLayerMap().put(layer.getUuid(), layer);

        return layer;
    }

    public boolean removeLayerFromProject(final JimixLayer layer) {
        if (!projectMap.containsKey(layer.getProject().getUuid()))
            throw new IllegalArgumentException("Project with UUID " + layer.getProject().getUuid() + " not found!");

        final JimixProject project = projectMap.get(layer.getProject().getUuid());
        if (!project.getLayerMap().containsKey(layer.getUuid()))
            return false;

        LOGGER.info("Remove layer " + layer.getUuid() + " for project " + project.getUuid());
        project.getLayerMap().remove(layer.getUuid());
        return true;
    }

    public boolean removeLayerFromProject(final JimixProject project, final UUID layerUUID) {
        if (!project.getLayerMap().containsKey(layerUUID))
            return false;

        LOGGER.info("Remove layer " + layerUUID + " for project " + project.getUuid());
        project.getLayerMap().remove(layerUUID);
        return true;
    }
    //</editor-fold>

    //<editor-fold desc="Element">
    public JimixElement createPictureElementFromClipboardForLayer(final JimixLayer layer, final JimixClipboardProviderInstance instance) {
        final JimixPlugin2DElement pluginElement = instance.createElementFromClipboard(Clipboard.getSystemClipboard());
        final JimixPictureElementModel model = new JimixPictureElementModel(pluginElement);
        final JimixPictureElement element = new JimixPictureElement(layer.getProject(), layer, model);
        LOGGER.info("Create picture element " + element.getUuid() + " from clipboard for layer " + layer.getUuid());
        layer.getPictureElementMap().put(element.getUuid(), element);

        return element;
    }

    public JimixPictureElement createPictureElementForLayer(final JimixLayer layer, JimixPluginElement pluginElement) {
        return createPictureElementForLayer(layer, pluginElement, 0, 0);
    }

    public JimixPictureElement createPictureElementForLayer(final JimixLayer layer, JimixPluginElement pluginElement, int x, int y) {
        final JimixPictureElementModel model = new JimixPictureElementModel(pluginElement);
        model.setX(x);
        model.setY(y);
        final JimixPictureElement element = new JimixPictureElement(layer.getProject(), layer, model);
        LOGGER.info("Create picture element " + element.getUuid() + " (" + pluginElement.getClass().getName() + ") for layer " + layer.getUuid());
        layer.getPictureElementMap().put(element.getUuid(), element);

        return element;
    }

    public JimixMaskElement createMaskElementForLayer(final JimixLayer layer, JimixPluginElement pluginElement) {
        return createMaskElementForLayer(layer, pluginElement, 0, 0);
    }

    public JimixMaskElement createMaskElementForLayer(final JimixLayer layer, JimixPluginElement pluginElement, int x, int y) {
        final JimixMaskElementModel model = new JimixMaskElementModel(pluginElement);
        model.setX(x);
        model.setY(y);
        final JimixMaskElement element = new JimixMaskElement(layer.getProject(), layer, model);
        LOGGER.info("Create mask element " + element.getUuid() + " (" + pluginElement.getClass().getName() + ") for layer " + layer.getUuid());
        layer.getMaskElementMap().put(element.getUuid(), element);

        return element;
    }

    public boolean removeElementFromLayer(final JimixLayer layer, final JimixElement element) {
        return removeElementFromLayer(layer, element.getUuid());
    }

    public boolean removeElementFromLayer(final JimixLayer layer, final UUID elementUUID) {
        if (!layer.getPictureElementMap().containsKey(elementUUID))
            return false;

        LOGGER.info("Remove element " + elementUUID + " from layer " + layer.getUuid());
        layer.getPictureElementMap().remove(elementUUID);
        return true;
    }
    //</editor-fold>
}

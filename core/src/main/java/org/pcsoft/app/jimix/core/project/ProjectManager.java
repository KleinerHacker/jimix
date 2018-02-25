package org.pcsoft.app.jimix.core.project;

import javafx.scene.image.Image;
import javafx.scene.input.Clipboard;
import org.pcsoft.app.jimix.core.plugin.builtin.model.JimixImageElementModel;
import org.pcsoft.app.jimix.core.plugin.type.JimixClipboardProviderInstance;
import org.pcsoft.app.jimix.plugins.api.model.JimixElementModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    public JimixProject createProjectFromImage(final Image image) {
        LOGGER.info("Create project from image");

        //Create empty project
        final JimixProject project = this.createEmptyProject((int) image.getWidth(), (int) image.getHeight());
        //Create base layer
        final JimixLayer layer = this.createLayerForProject(project);
        //Create image element
        final JimixElement element = this.createImageElementForLayer(layer, image);

        projectMap.put(project.getUuid(), project);

        return project;
    }

    public JimixProject createEmptyProject(final int width, final int height) {
        final JimixProjectModel model = new JimixProjectModel(width, height);
        final JimixProject jimixProject = new JimixProject(model);
        LOGGER.info("Create empty project " + jimixProject.getUuid());
        projectMap.put(jimixProject.getUuid(), jimixProject);

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
        final JimixLayerModel model = new JimixLayerModel();
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
    public JimixElement createElementFromClipboardForLayer(final JimixLayer layer, final JimixClipboardProviderInstance instance) {
        final JimixElementModel model = instance.createElementFromClipboard(Clipboard.getSystemClipboard());
        final JimixElement element = new JimixElement(layer.getProject(), layer, model);
        LOGGER.info("Create element " + element.getUuid() + " from clipboard for layer " + layer.getUuid());
        layer.getElementMap().put(element.getUuid(), element);

        return element;
    }

    public JimixElement createImageElementForLayer(final JimixLayer layer, final Image image) {
        final JimixElementModel model = new JimixImageElementModel(image);
        final JimixElement element = new JimixElement(layer.getProject(), layer, model);
        LOGGER.info("Create image element " + element.getUuid() + " for layer " + layer.getUuid());
        layer.getElementMap().put(element.getUuid(), element);

        return element;
    }

    public boolean removeElementFromLayer(final JimixLayer layer, final JimixElement element) {
        return removeElementFromLayer(layer, element.getUuid());
    }

    public boolean removeElementFromLayer(final JimixLayer layer, final UUID elementUUID) {
        if (!layer.getElementMap().containsKey(elementUUID))
            return false;

        LOGGER.info("Remove element " + elementUUID + " from layer " + layer.getUuid());
        layer.getElementMap().remove(elementUUID);
        return true;
    }
    //</editor-fold>
}

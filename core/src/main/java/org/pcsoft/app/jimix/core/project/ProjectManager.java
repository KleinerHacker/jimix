package org.pcsoft.app.jimix.core.project;

import javafx.scene.image.Image;
import org.apache.commons.io.FileUtils;
import org.pcsoft.app.jimix.commons.exception.JimixProjectException;

import java.io.File;
import java.io.IOException;
import java.util.*;

public final class ProjectManager {
    private static final ProjectManager instance = new ProjectManager();

    public static ProjectManager getInstance() {
        return instance;
    }

    private final Map<UUID, JimixProject> projectMap = new HashMap<>();

    private ProjectManager() {
    }

    //<editor-fold desc="Project">
    public JimixProject createProjectFromFile(final File file) throws JimixProjectException {
        try {
            //Load image
            final Image image = new Image(FileUtils.openInputStream(file));
            //Create project from image
            final JimixProject project = this.createProjectFromImage(image);
            project.getModel().setFile(file);

            return project;
        } catch (IOException e) {
            throw new JimixProjectException("unable to load project from file " + file.getAbsolutePath(), e);
        }
    }

    public JimixProject createProjectFromImage(final Image image) {
        //Create empty project
        final JimixProject project = this.createEmptyProject((int) image.getWidth(), (int) image.getHeight());
        //Create base level
        final JimixLevel level = this.createLevelForProject(project);
        //Create image element
        final JimixElement element = this.createImageElementForLevel(level, image);

        projectMap.put(project.getUuid(), project);

        return project;
    }

    public JimixProject createEmptyProject(final int width, final int height) {
        final JimixProjectModel model = new JimixProjectModel(width, height);
        final JimixProject jimixProject = new JimixProject(model);
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

        projectMap.remove(projectUUID);
        return true;
    }

    public JimixProject getProject(final UUID projectUUID) {
        if (!projectMap.containsKey(projectUUID))
            throw new IllegalArgumentException("Project with UUID " + projectUUID.toString() + " not found!");

        return projectMap.get(projectUUID);
    }
    //</editor-fold>

    //<editor-fold desc="Level">
    public JimixLevel createLevelForProject(final JimixProject project) {
        return createLevelForProject(project.getUuid());
    }

    public JimixLevel createLevelForProject(final UUID projectUUID) {
        if (!projectMap.containsKey(projectUUID))
            throw new IllegalArgumentException("Project with UUID " + projectUUID.toString() + " not found!");

        final JimixProject project = projectMap.get(projectUUID);
        final JimixLevelModel model = new JimixLevelModel();
        final JimixLevel level = new JimixLevel(project, model);
        project.getLevelMap().put(level.getUuid(), level);

        return level;
    }

    public boolean removeLevelFromProject(final JimixLevel level) {
        if (!projectMap.containsKey(level.getProject().getUuid()))
            throw new IllegalArgumentException("Project with UUID " + level.getProject().getUuid().toString() + " not found!");

        final JimixProject project = projectMap.get(level.getProject().getUuid());
        if (!project.getLevelMap().containsKey(level.getUuid()))
            return false;

        project.getLevelMap().remove(level.getUuid());
        return true;
    }

    public boolean removeLevelFromProject(final JimixProject project, final UUID levelUUID) {
        if (!project.getLevelMap().containsKey(levelUUID))
            return false;

        project.getLevelMap().remove(levelUUID);
        return true;
    }
    //</editor-fold>

    //<editor-fold desc="Element">
    public JimixElement createImageElementForLevel(final JimixLevel level, final Image image) {
        final JimixElementModel model = new JimixImageElementModel(image);
        final JimixElement element = new JimixElement(level.getProject(), level, model);
        level.getElementMap().put(element.getUuid(), element);

        return element;
    }

    public boolean removeElementFromLevel(final JimixLevel level, final JimixElement element) {
        return removeElementFromLevel(level, element.getUuid());
    }

    public boolean removeElementFromLevel(final JimixLevel level, final UUID elementUUID) {
        if (!level.getElementMap().containsKey(elementUUID))
            return false;

        level.getElementMap().remove(elementUUID);
        return true;
    }
    //</editor-fold>
}

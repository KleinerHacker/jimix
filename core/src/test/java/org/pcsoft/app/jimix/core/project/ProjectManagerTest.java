package org.pcsoft.app.jimix.core.project;

import javafx.scene.paint.Color;
import org.junit.Assert;
import org.junit.Test;
import org.pcsoft.app.jimix.core.plugin.builtin.blender.OverlayBlender;
import org.pcsoft.app.jimix.core.plugin.builtin.model.RectanglePluginElement;
import org.pcsoft.app.jimix.project.JimixElementModel;
import org.pcsoft.app.jimix.project.JimixLayerModel;
import org.pcsoft.app.jimix.project.JimixProjectModel;

import java.io.File;
import java.util.concurrent.atomic.AtomicInteger;

public class ProjectManagerTest {

    @Test
    public void testRefresh() {
        final AtomicInteger counter = new AtomicInteger(0);
        final JimixProject project = ProjectManager.getInstance().createEmptyProject(100, 100);
        project.resultImageProperty().addListener((v, o, n) -> counter.incrementAndGet());

        Assert.assertEquals(0, counter.get());

        project.setFile(new File("C:\\"));
        Assert.assertEquals(0, counter.get());

        project.getModel().setWidth(110);
        Assert.assertEquals(1, counter.get());

        final JimixLayer layer = ProjectManager.getInstance().createLayerForProject(project);
        Assert.assertEquals(3, counter.get());

        layer.setVisibile(false);
        Assert.assertEquals(5, counter.get());

        layer.getModel().setOpacity(0.1);
        Assert.assertEquals(6, counter.get());
    }

    @Test
    public void testRebuild() {
        final JimixProjectModel projectModel = new JimixProjectModel(200, 300);
        final JimixLayerModel layerModel = new JimixLayerModel(new OverlayBlender());
        {
            layerModel.setName("Hello");
            layerModel.setOpacity(0.1d);
            layerModel.setBackground(Color.RED);
            final JimixElementModel elementModel = new JimixElementModel(new RectanglePluginElement());
            {
                elementModel.setOpacity(0.5d);
                elementModel.setX(10);
                elementModel.setY(12);
            }
            layerModel.getElementList().add(elementModel);
        }
        projectModel.getLayerList().add(layerModel);

        final JimixProject project = ProjectManager.getInstance().createProjectNative(projectModel, false);
        Assert.assertNotNull(project);
        Assert.assertEquals(1, project.getLayerList().size());
        Assert.assertEquals(1, project.getLayerList().get(0).getElementList().size());
        Assert.assertNotNull(project.getLayerList().get(0).getElementList().get(0).getModel());
    }

}
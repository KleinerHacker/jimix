package org.pcsoft.app.jimix.core.plugin.builtin.blender;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import org.pcsoft.app.jimix.commons.type.TransparentSnapshotParams;
import org.pcsoft.app.jimix.plugins.api.JimixBlender;
import org.pcsoft.app.jimix.plugins.api.annotation.JimixBlenderDescriptor;

@JimixBlenderDescriptor(name = "Add", description = "Add pictures", iconPath = "/builtin/icons/ic_blender_add16.png")
public class AddBlender implements JimixBlender {
    @Override
    public Image apply(Image groundImage, Image layerImage, double opacity) throws Exception {
        final Canvas canvas = new Canvas(groundImage.getWidth(), groundImage.getHeight());
        final GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setGlobalBlendMode(BlendMode.ADD);

        gc.drawImage(groundImage, 0, 0);
        gc.setGlobalAlpha(opacity);
        gc.drawImage(layerImage, 0, 0);

        return canvas.snapshot(new TransparentSnapshotParams(), null);
    }
}

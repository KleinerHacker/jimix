package org.pcsoft.app.jimix.core.plugin.builtin.blender;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import org.pcsoft.app.jimix.commons.type.TransparentSnapshotParams;
import org.pcsoft.app.jimix.plugin.mani.api.JimixBlender;
import org.pcsoft.app.jimix.plugin.mani.api.annotation.JimixBlenderDescriptor;

@JimixBlenderDescriptor(name = "Darken", description = "Darken algorithm", iconPath = "/builtin/icons/ic_blender_subtract16.png")
public class DarkenBlender implements JimixBlender {
    @Override
    public Image apply(Image groundImage, Image layerImage, double opacity) throws Exception {
        final Canvas canvas = new Canvas(groundImage.getWidth(), groundImage.getHeight());
        final GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setGlobalBlendMode(BlendMode.DARKEN);

        gc.drawImage(groundImage, 0, 0);
        gc.setGlobalAlpha(opacity);
        gc.drawImage(layerImage, 0, 0);

        return canvas.snapshot(new TransparentSnapshotParams(), null);
    }
}

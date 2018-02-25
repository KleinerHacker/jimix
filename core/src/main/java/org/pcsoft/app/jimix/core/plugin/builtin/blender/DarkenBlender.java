package org.pcsoft.app.jimix.core.plugin.builtin.blender;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import org.pcsoft.app.jimix.plugins.api.JimixBlender;
import org.pcsoft.app.jimix.plugins.api.annotation.JimixBlenderDescriptor;

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

        final SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT);
        return canvas.snapshot(params, null);
    }
}

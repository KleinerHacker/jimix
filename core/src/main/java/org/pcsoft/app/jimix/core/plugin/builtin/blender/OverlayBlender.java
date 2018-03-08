package org.pcsoft.app.jimix.core.plugin.builtin.blender;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import org.pcsoft.app.jimix.commons.type.TransparentSnapshotParams;
import org.pcsoft.app.jimix.plugin.manipulation.api.JimixBlender;
import org.pcsoft.app.jimix.plugin.manipulation.api.annotation.JimixBlenderDescriptor;

@JimixBlenderDescriptor(name = "Overlay", description = "Overlay layers (default)", iconPath = "/builtin/icons/ic_blender_overlay16.png")
public class OverlayBlender implements JimixBlender {
    @Override
    public Image apply(Image groundImage, Image layerImage, double opacity) throws Exception {
        final Canvas canvas = new Canvas(groundImage.getWidth(), groundImage.getHeight());
        final GraphicsContext gc = canvas.getGraphicsContext2D();

        gc.drawImage(groundImage, 0, 0);
        gc.setGlobalAlpha(opacity);
        gc.drawImage(layerImage, 0, 0);

        return canvas.snapshot(new TransparentSnapshotParams(), null);
    }
}

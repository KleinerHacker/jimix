package org.pcsoft.app.jimix.core.plugin.builtin.scaler;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import org.pcsoft.app.jimix.commons.type.JimixSnapshotParams;
import org.pcsoft.app.jimix.plugin.manipulation.api.JimixScaler;
import org.pcsoft.app.jimix.plugin.manipulation.api.annotation.JimixScalerDescriptor;
import org.pcsoft.app.jimix.plugin.manipulation.api.type.JimixSource;

@JimixScalerDescriptor(name = "Default", description = "Default JavaFX image scaling algorithm")
public class DefaultScaler implements JimixScaler {
    @Override
    public Image apply(Image image, int targetWidth, int targetHeight, JimixSource source) throws Exception {
        final Canvas canvas = new Canvas(targetWidth, targetHeight);
        final GraphicsContext gc = canvas.getGraphicsContext2D();

        gc.drawImage(image, 0, 0, targetWidth, targetHeight);

        return canvas.snapshot(new JimixSnapshotParams(), null);
    }
}

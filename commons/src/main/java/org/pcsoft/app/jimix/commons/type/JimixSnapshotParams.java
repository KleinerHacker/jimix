package org.pcsoft.app.jimix.commons.type;

import javafx.geometry.Rectangle2D;
import javafx.scene.PerspectiveCamera;
import javafx.scene.SnapshotParameters;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.awt.*;

public class JimixSnapshotParams extends SnapshotParameters {
    public JimixSnapshotParams() {
        this(Color.TRANSPARENT, null);
    }

    public JimixSnapshotParams(final Paint paint, final Dimension size) {
        setFill(paint);

        final PerspectiveCamera camera = new PerspectiveCamera(false);
        camera.setNearClip(Double.MIN_NORMAL);
        camera.setFarClip(Double.MAX_VALUE);
        setCamera(camera);

        if (size != null) {
            setViewport(new Rectangle2D(0, 0, size.width, size.height));
        }
    }
}

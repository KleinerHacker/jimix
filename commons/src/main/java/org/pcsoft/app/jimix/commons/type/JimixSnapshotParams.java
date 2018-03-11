package org.pcsoft.app.jimix.commons.type;

import javafx.scene.PerspectiveCamera;
import javafx.scene.SnapshotParameters;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class JimixSnapshotParams extends SnapshotParameters {
    public JimixSnapshotParams() {
        this(Color.TRANSPARENT);
    }

    public JimixSnapshotParams(final Paint paint) {
        setFill(paint);

        final PerspectiveCamera camera = new PerspectiveCamera(false);
        camera.setNearClip(Double.MIN_NORMAL);
        camera.setFarClip(Double.MAX_VALUE);
        setCamera(camera);
    }
}

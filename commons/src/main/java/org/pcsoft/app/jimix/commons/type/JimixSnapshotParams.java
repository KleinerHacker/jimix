package org.pcsoft.app.jimix.commons.type;

import javafx.beans.property.DoubleProperty;
import javafx.scene.PerspectiveCamera;
import javafx.scene.SnapshotParameters;
import javafx.scene.paint.Color;

public class JimixSnapshotParams extends SnapshotParameters {
    public JimixSnapshotParams() {
        setFill(Color.TRANSPARENT);

        final PerspectiveCamera camera = new PerspectiveCamera(false);
        camera.setNearClip(Double.MIN_NORMAL);
        camera.setFarClip(Double.MAX_VALUE);
        setCamera(camera);
    }
}

package org.pcsoft.app.jimix.commons.type;

import javafx.scene.SnapshotParameters;
import javafx.scene.paint.Color;

public class TransparentSnapshotParams extends SnapshotParameters {
    public TransparentSnapshotParams() {
        setFill(Color.TRANSPARENT);
    }
}

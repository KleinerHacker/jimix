package org.pcsoft.app.jimix.core.util;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public final class ImageManipulationUtils {
    public static Image turnLeft(final Image image) {
        final Canvas canvas = new Canvas(image.getWidth(), image.getHeight());
        canvas.setRotate(-90);

        final GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.drawImage(image, 0, 0);

        return canvas.snapshot(new SnapshotParameters() {{
            setFill(Color.TRANSPARENT);
        }}, null);
    }

    public static Image turnRight(final Image image) {
        final Canvas canvas = new Canvas(image.getWidth(), image.getHeight());
        canvas.setRotate(90);

        final GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.drawImage(image, 0, 0);

        return canvas.snapshot(new SnapshotParameters() {{
            setFill(Color.TRANSPARENT);
        }}, null);
    }

    public static Image mirrorHorizontal(final Image image) {
        final Canvas canvas = new Canvas(image.getWidth(), image.getHeight());
        canvas.setScaleX(-1);

        final GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.drawImage(image, 0, 0);

        return canvas.snapshot(new SnapshotParameters() {{
            setFill(Color.TRANSPARENT);
        }}, null);
    }

    public static Image mirrorVertical(final Image image) {
        final Canvas canvas = new Canvas(image.getWidth(), image.getHeight());
        canvas.setScaleY(-1);

        final GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.drawImage(image, 0, 0);

        return canvas.snapshot(new SnapshotParameters() {{
            setFill(Color.TRANSPARENT);
        }}, null);
    }

    private ImageManipulationUtils() {
    }
}

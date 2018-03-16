package org.pcsoft.app.jimix.core.util;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public final class CheckerBoardPatternUtils {
    public static void buildPattern(GraphicsContext gc, double width, double height) {
        buildPattern(gc, width, height, Color.LIGHTGREY, Color.DARKGRAY);
    }

    public static void buildPattern(GraphicsContext gc, double width, double height, Color col1, Color col2) {
        gc.setFill(col1);
        gc.fillRect(0, 0, width,  height);

        gc.setFill(col2);
        for (int y = 0; y < height / 10; y++) {
            for (int x = 0; x < width / 10; x++) {
                if ((x % 2 == 0 && y % 2 == 0) || (x % 2 != 0 && y % 2 != 0)) {
                    gc.fillRect(x * 10, y * 10, 10, 10);
                }
            }
        }
    }

    private CheckerBoardPatternUtils() {
    }
}

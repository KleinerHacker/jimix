package org.pcsoft.app.jimix.core.util;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public final class TextPluginElementUtils {
    public static Shape buildShape(int x, int y, String str, Font font, boolean underline, boolean strikeout, Paint fill, Paint stroke) {
        final Text text = new Text(str);
        text.setFont(font);
        text.setStrikethrough(strikeout);
        text.setUnderline(underline);
        text.setFill(fill);
        text.setStroke(stroke);
        text.setX(x);
        text.setY(y);

        return text;
    }

    private TextPluginElementUtils() {
    }
}

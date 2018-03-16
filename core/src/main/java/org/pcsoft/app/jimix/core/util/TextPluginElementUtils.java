package org.pcsoft.app.jimix.core.util;

import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import org.apache.commons.lang.StringUtils;
import org.pcsoft.app.jimix.core.plugin.builtin.model.TextPluginElement;
import org.pcsoft.app.jimix.plugin.common.api.util.Shape2DPluginElementUtils;

public final class TextPluginElementUtils {
    public static Shape buildShape(TextPluginElement element) {
        return buildShape(element, null, null);
    }

    public static Shape buildShape(TextPluginElement element, String txt, Double size) {
        final Text text = new Text(StringUtils.isEmpty(txt) ? element.getText() : txt);
        text.setFont(Font.font(element.getFontFamilyName(), element.getFontWeight(), element.getFontPosture(), size == null ? element.getFontSize() : size));
        Shape2DPluginElementUtils.buildShape(text, element);
        text.setStrikethrough(element.isStrikeout());
        text.setUnderline(element.isUnderline());

        return text;
    }

    private TextPluginElementUtils() {
    }
}

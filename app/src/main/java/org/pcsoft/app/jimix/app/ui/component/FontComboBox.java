package org.pcsoft.app.jimix.app.ui.component;

import javafx.scene.control.ListCell;
import javafx.scene.text.Font;
import org.pcsoft.framework.jfex.ui.component.data.ComboBoxEx;

public class FontComboBox extends ComboBoxEx<String, String> {
    public FontComboBox() {
        setValueComparator(String::compareTo);
        setValueCellRendererCallback(FontComboBox::drawCell);
        setValueButtonCellRendererCallback(FontComboBox::drawCell);
        getItems().setAll(Font.getFamilies());
    }

    private static void drawCell(ListCell cell, String item, boolean empty) {
        cell.setText(null);
        cell.setFont(Font.getDefault());
        if (item != null && !empty) {
            cell.setText(item);
            cell.setFont(Font.font(item));
        }
    }
}

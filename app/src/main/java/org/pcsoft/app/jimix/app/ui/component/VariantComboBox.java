package org.pcsoft.app.jimix.app.ui.component;

import javafx.geometry.Insets;
import javafx.scene.control.ListCell;
import org.pcsoft.app.jimix.app.language.LanguageResources;
import org.pcsoft.app.jimix.plugin.manipulation.api.type.JimixVariant;
import org.pcsoft.framework.jfex.data.ComboBoxEx;

import java.util.Comparator;

public class VariantComboBox<T extends JimixVariant> extends ComboBoxEx<T, String> {
    private static final String WORD_BUILTIN = LanguageResources.getText("common.plugin.variant.builtin");
    private static final String WORD_CUSTOM = LanguageResources.getText("common.plugin.variant.custom");

    public VariantComboBox() {
        setHeaderKeyExtractor(variant -> variant.isBuiltin() ? WORD_BUILTIN : WORD_CUSTOM);
        setHeaderComparator((s1, s2) -> s1.equals(s2) ? 0 : s1.equals(WORD_BUILTIN) ? -1 : 1);
        setHeaderCellRendererCallback(this::drawHeader);

        setValueComparator(Comparator.comparing(JimixVariant::getName));
        setValueCellRendererCallback(this::drawValue);
        setValueButtonCellRendererCallback(this::drawValue);
    }

    private void drawValue(ListCell listCell, JimixVariant variant, boolean empty) {
        listCell.setText(null);
        listCell.setStyle("");
        if (variant != null && !empty) {
            listCell.setPadding(new Insets(listCell.getPadding().getTop(), listCell.getPadding().getRight(), listCell.getPadding().getBottom(), 20));
            listCell.setText(variant.getName());
        }
    }

    private void drawHeader(ListCell listCell, String s, boolean empty) {
        listCell.setText(null);
        listCell.setStyle("");
        if (s != null && !empty) {
            listCell.setStyle("-fx-font-weight: bold");
            listCell.setText(s);
        }
    }
}

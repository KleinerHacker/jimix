package org.pcsoft.app.jimix.app.ui.component.prop_sheet.editor;

import javafx.beans.value.ObservableValue;
import org.controlsfx.control.PropertySheet;
import org.controlsfx.property.editor.AbstractPropertyEditor;
import org.pcsoft.app.jimix.app.ui.component.FontComboBox;

public class FontPropertyEditor extends AbstractPropertyEditor<String, FontComboBox> {
    public FontPropertyEditor(PropertySheet.Item property) {
        super(property, new FontComboBox());
    }

    @Override
    protected ObservableValue<String> getObservableValue() {
        return getEditor().valueProperty();
    }

    @Override
    public void setValue(String value) {
        getEditor().setValue(value);
    }
}

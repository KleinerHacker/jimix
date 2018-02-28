package org.pcsoft.app.jimix.app.ui.component.prop_sheet.editor;

import javafx.beans.value.ObservableValue;
import org.controlsfx.control.PropertySheet;
import org.controlsfx.property.editor.AbstractPropertyEditor;
import org.pcsoft.app.jimix.app.ui.component.prop_sheet.editor.cell.DimensionEditorPane;

import java.awt.*;

public class DimensionPropertyEditor extends AbstractPropertyEditor<Dimension, DimensionEditorPane> {
    public DimensionPropertyEditor(PropertySheet.Item property) {
        super(property, new DimensionEditorPane());
    }

    @Override
    protected ObservableValue<Dimension> getObservableValue() {
        return getEditor().valueProperty();
    }

    @Override
    public void setValue(Dimension value) {
        getEditor().setValue(value);
    }
}

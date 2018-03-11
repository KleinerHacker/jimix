package org.pcsoft.app.jimix.app.ui.component.prop_sheet.editor;

import javafx.beans.binding.Bindings;
import javafx.beans.value.ObservableValue;
import org.controlsfx.control.PropertySheet;
import org.controlsfx.property.editor.AbstractPropertyEditor;
import org.pcsoft.app.jimix.app.ui.component.BlenderComboBox;
import org.pcsoft.app.jimix.plugin.manipulation.manager.type.JimixBlenderInstance;

public class BlenderPropertyEditor extends AbstractPropertyEditor<JimixBlenderInstance, BlenderComboBox> {
    public BlenderPropertyEditor(PropertySheet.Item property) {
        super(property, new BlenderComboBox());
    }

    @Override
    protected ObservableValue<JimixBlenderInstance> getObservableValue() {
        return Bindings.createObjectBinding(() -> getEditor().getValue() == null ? null : getEditor().getValue().createInstance(), getEditor().valueProperty());
    }

    @Override
    public void setValue(JimixBlenderInstance value) {
        getEditor().setValue(value.getPlugin());
    }
}

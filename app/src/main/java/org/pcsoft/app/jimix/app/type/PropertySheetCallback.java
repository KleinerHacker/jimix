package org.pcsoft.app.jimix.app.type;

import javafx.util.Callback;
import org.controlsfx.control.PropertySheet;
import org.controlsfx.property.editor.PropertyEditor;

import java.util.HashMap;
import java.util.Map;

public class PropertySheetCallback implements Callback<PropertySheet.Item, PropertyEditor<?>> {
    private final Map<PropertySheet.Item, PropertyEditor<?>> editorMap = new HashMap<>();
    private final Callback<PropertySheet.Item, PropertyEditor<?>> originalCallback;

    public PropertySheetCallback(Callback<PropertySheet.Item, PropertyEditor<?>> originalCallback) {
        this.originalCallback = originalCallback;
    }

    public Map<PropertySheet.Item, PropertyEditor<?>> getEditorMap() {
        return editorMap;
    }

    public Callback<PropertySheet.Item, PropertyEditor<?>> getOriginalCallback() {
        return originalCallback;
    }

    @Override
    public PropertyEditor call(PropertySheet.Item param) {
        if (!editorMap.containsKey(param))
            return originalCallback.call(param);

        return editorMap.get(param);
    }
}

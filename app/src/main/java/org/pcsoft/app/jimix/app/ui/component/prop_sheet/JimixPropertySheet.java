package org.pcsoft.app.jimix.app.ui.component.prop_sheet;

import org.pcsoft.app.jimix.app.ui.component.prop_sheet.editor.DimensionPropertyEditor;
import org.pcsoft.framework.jfex.component.PropertySheetEx;

import java.awt.*;

/**
 * Extension variant for a property sheet, based on ControlsFX component
 */
public class JimixPropertySheet extends PropertySheetEx {

    public JimixPropertySheet() {
        super();

        addCustomTypeSupport(Dimension.class, DimensionPropertyEditor::new);
    }
}
                                                        
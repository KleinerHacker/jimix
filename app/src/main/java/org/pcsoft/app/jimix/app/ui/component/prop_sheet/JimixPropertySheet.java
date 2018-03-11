package org.pcsoft.app.jimix.app.ui.component.prop_sheet;

import javafx.scene.paint.Paint;
import org.pcsoft.app.jimix.app.ui.component.BlenderComboBox;
import org.pcsoft.app.jimix.app.ui.component.prop_sheet.editor.BlenderPropertyEditor;
import org.pcsoft.app.jimix.app.ui.component.prop_sheet.editor.DimensionPropertyEditor;
import org.pcsoft.app.jimix.app.ui.component.prop_sheet.editor.PaintPropertyEditor;
import org.pcsoft.app.jimix.plugin.manipulation.manager.type.JimixBlenderInstance;
import org.pcsoft.framework.jfex.ui.component.PropertySheetEx;

import java.awt.*;

/**
 * Extension variant for a property sheet, based on ControlsFX component
 */
public class JimixPropertySheet extends PropertySheetEx {

    public JimixPropertySheet() {
        super();

        addCustomTypeSupport(Dimension.class, DimensionPropertyEditor::new);
        addCustomTypeSupport(Paint.class, PaintPropertyEditor::new);
        addCustomTypeSupport(JimixBlenderInstance.class, BlenderPropertyEditor::new);
    }
}
                                                        
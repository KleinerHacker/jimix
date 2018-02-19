package org.pcsoft.app.jimix.core.ui.util;

import javafx.scene.control.SelectionModel;

public abstract class FXSelectionModelUtils extends FXUtils {

    protected static <T> void reselect(final SelectionModel<T> selectionModel) {
        final int selectedIndex = selectionModel.getSelectedIndex();
        selectionModel.select(null);
        selectionModel.select(selectedIndex);
    }

}

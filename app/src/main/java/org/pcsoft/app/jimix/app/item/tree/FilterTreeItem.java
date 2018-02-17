package org.pcsoft.app.jimix.app.item.tree;

import javafx.scene.control.TreeItem;
import org.pcsoft.app.jimix.core.plugin.type.JimixFilterInstance;
import org.pcsoft.app.jimix.core.plugin.type.JimixInstance;

public class FilterTreeItem extends TreeItem<Object> {
    public FilterTreeItem(final JimixFilterInstance instance) {
        super(instance);
    }

    public JimixFilterInstance getInstance() {
        return (JimixFilterInstance) getValue();
    }
}

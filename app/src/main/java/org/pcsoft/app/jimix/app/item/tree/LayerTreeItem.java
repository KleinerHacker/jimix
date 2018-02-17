package org.pcsoft.app.jimix.app.item.tree;

import javafx.scene.control.TreeItem;
import org.pcsoft.app.jimix.core.project.JimixLayer;

public class LayerTreeItem extends TreeItem<Object> {
    public LayerTreeItem(final JimixLayer layer) {
        super(layer);
    }

    public JimixLayer getLayer() {
        return (JimixLayer) getValue();
    }
}

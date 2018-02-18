package org.pcsoft.app.jimix.app.item.tree;

import javafx.scene.control.TreeItem;
import org.pcsoft.app.jimix.core.project.JimixLayer;

import java.util.Objects;

public class LayerTreeItem extends TreeItem<Object> {
    public LayerTreeItem(final JimixLayer layer) {
        super(layer);
    }

    public JimixLayer getLayer() {
        return (JimixLayer) getValue();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LayerTreeItem that = (LayerTreeItem) o;
        return Objects.equals(getLayer(), that.getLayer());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLayer());
    }
}

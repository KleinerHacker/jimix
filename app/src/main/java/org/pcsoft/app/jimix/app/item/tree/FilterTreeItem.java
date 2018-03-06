package org.pcsoft.app.jimix.app.item.tree;

import javafx.scene.control.TreeItem;
import org.pcsoft.app.jimix.plugin.mani.manager.type.JimixFilterInstance;

import java.util.Objects;

public class FilterTreeItem extends TreeItem<Object> {
    public FilterTreeItem(final JimixFilterInstance instance) {
        super(instance);
    }

    public JimixFilterInstance getInstance() {
        return (JimixFilterInstance) getValue();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FilterTreeItem that = (FilterTreeItem) o;
        return Objects.equals(getInstance(), that.getInstance()) && Objects.equals(getParent(), that.getParent());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getInstance(), getParent());
    }
}

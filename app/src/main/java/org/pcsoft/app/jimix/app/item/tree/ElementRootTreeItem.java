package org.pcsoft.app.jimix.app.item.tree;

import javafx.scene.control.TreeItem;

import java.util.Objects;

public class ElementRootTreeItem extends TreeItem<Object> {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ElementRootTreeItem that = (ElementRootTreeItem) o;
        return Objects.equals(getParent(), that.getParent());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getParent());
    }
}

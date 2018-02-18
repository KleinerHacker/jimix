package org.pcsoft.app.jimix.app.item.tree;

import javafx.scene.control.TreeItem;
import org.pcsoft.app.jimix.core.project.JimixElement;

import java.util.Objects;

public class ElementTreeItem extends TreeItem<Object> {
    public ElementTreeItem(final JimixElement element) {
        super(element);
    }

    public JimixElement getElement() {
        return (JimixElement) getValue();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ElementTreeItem that = (ElementTreeItem) o;
        return Objects.equals(getElement(), that.getElement()) && Objects.equals(getParent(), that.getParent());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getElement(), getParent());
    }
}

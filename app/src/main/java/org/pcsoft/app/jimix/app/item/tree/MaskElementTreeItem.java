package org.pcsoft.app.jimix.app.item.tree;

import javafx.scene.control.TreeItem;
import org.pcsoft.app.jimix.core.project.JimixMaskElement;

import java.util.Objects;

public class MaskElementTreeItem extends TreeItem<Object> {
    public MaskElementTreeItem(final JimixMaskElement element) {
        super(element);
    }

    public JimixMaskElement getElement() {
        return (JimixMaskElement) getValue();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MaskElementTreeItem that = (MaskElementTreeItem) o;
        return Objects.equals(getElement(), that.getElement()) && Objects.equals(getParent(), that.getParent());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getElement(), getParent());
    }
}

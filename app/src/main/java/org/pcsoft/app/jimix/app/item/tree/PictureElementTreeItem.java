package org.pcsoft.app.jimix.app.item.tree;

import javafx.scene.control.TreeItem;
import org.pcsoft.app.jimix.core.project.JimixPictureElement;

import java.util.Objects;

public class PictureElementTreeItem extends TreeItem<Object> {
    public PictureElementTreeItem(final JimixPictureElement element) {
        super(element);
    }

    public JimixPictureElement getElement() {
        return (JimixPictureElement) getValue();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PictureElementTreeItem that = (PictureElementTreeItem) o;
        return Objects.equals(getElement(), that.getElement()) && Objects.equals(getParent(), that.getParent());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getElement(), getParent());
    }
}

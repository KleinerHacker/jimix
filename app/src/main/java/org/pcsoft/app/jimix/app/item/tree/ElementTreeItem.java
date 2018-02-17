package org.pcsoft.app.jimix.app.item.tree;

import javafx.scene.control.TreeItem;
import org.pcsoft.app.jimix.core.project.JimixElement;

public class ElementTreeItem extends TreeItem<Object> {
    public ElementTreeItem(final JimixElement element) {
        super(element);
    }

    public JimixElement getElement() {
        return (JimixElement) getValue();
    }
}

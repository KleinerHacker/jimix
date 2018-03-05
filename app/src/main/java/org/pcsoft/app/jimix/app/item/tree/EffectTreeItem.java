package org.pcsoft.app.jimix.app.item.tree;

import javafx.scene.control.TreeItem;
import org.pcsoft.app.jimix.plugins.manager.type.JimixEffectInstance;

public class EffectTreeItem extends TreeItem<Object> {
    public EffectTreeItem(final JimixEffectInstance effectInstance) {
        super(effectInstance);
    }

    public JimixEffectInstance getInstance() {
        return (JimixEffectInstance) getValue();
    }
}

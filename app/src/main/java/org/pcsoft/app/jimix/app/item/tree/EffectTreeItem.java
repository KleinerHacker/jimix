package org.pcsoft.app.jimix.app.item.tree;

import javafx.scene.control.TreeItem;
import org.pcsoft.app.jimix.plugin.mani.manager.type.JimixEffectInstance;

import java.util.Objects;

public class EffectTreeItem extends TreeItem<Object> {
    public EffectTreeItem(final JimixEffectInstance effectInstance) {
        super(effectInstance);
    }

    public JimixEffectInstance getInstance() {
        return (JimixEffectInstance) getValue();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EffectTreeItem that = (EffectTreeItem) o;
        return Objects.equals(getInstance(), that.getInstance()) && Objects.equals(getParent(), that.getParent());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getInstance(), getParent());
    }
}

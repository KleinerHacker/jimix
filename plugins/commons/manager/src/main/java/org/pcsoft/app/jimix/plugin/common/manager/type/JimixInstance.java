package org.pcsoft.app.jimix.plugin.common.manager.type;

public interface JimixInstance<T extends JimixPlugin, I extends JimixInstance<T, I>> {
    T getPlugin();

    I copy();
}

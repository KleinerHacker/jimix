package org.pcsoft.app.jimix.plugins.manager.type;

public interface JimixInstance<T extends JimixPlugin> {
    T getPlugin();
}

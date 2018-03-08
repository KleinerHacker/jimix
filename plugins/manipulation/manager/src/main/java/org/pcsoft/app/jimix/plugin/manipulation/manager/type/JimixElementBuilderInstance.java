package org.pcsoft.app.jimix.plugin.manipulation.manager.type;

import org.pcsoft.app.jimix.plugin.common.manager.type.JimixInstance;

import java.util.Objects;
import java.util.UUID;

public abstract class JimixElementBuilderInstance<T extends JimixElementBuilderPlugin> implements JimixInstance<T> {
    private final transient UUID uuid = UUID.randomUUID(); //Temporary identifier only
    protected final T plugin;

    protected JimixElementBuilderInstance(T plugin) {
        this.plugin = plugin;
    }

    @Override
    public T getPlugin() {
        return plugin;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JimixElementBuilderInstance<?> that = (JimixElementBuilderInstance<?>) o;
        return Objects.equals(uuid, that.uuid);
    }

    @Override
    public final int hashCode() {
        return Objects.hash(uuid);
    }
}

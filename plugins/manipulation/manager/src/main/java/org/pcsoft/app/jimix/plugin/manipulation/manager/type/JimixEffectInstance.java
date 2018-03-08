package org.pcsoft.app.jimix.plugin.manipulation.manager.type;

import org.pcsoft.app.jimix.plugin.common.manager.type.JimixInstance;
import org.pcsoft.app.jimix.plugin.manipulation.api.config.JimixEffectConfiguration;

import java.util.Objects;
import java.util.UUID;

public abstract class JimixEffectInstance<T extends JimixEffectPlugin> implements JimixInstance<T> {
    private final transient UUID uuid = UUID.randomUUID(); //Temporary identifier only
    protected final T plugin;
    protected final JimixEffectConfiguration configuration;

    protected JimixEffectInstance(T plugin, JimixEffectConfiguration configuration) {
        this.plugin = plugin;
        this.configuration = configuration;
    }

    @Override
    public T getPlugin() {
        return plugin;
    }

    public JimixEffectConfiguration getConfiguration() {
        return configuration;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JimixEffectInstance instance = (JimixEffectInstance) o;
        return Objects.equals(uuid, instance.uuid);
    }

    @Override
    public final int hashCode() {
        return Objects.hash(uuid);
    }
}

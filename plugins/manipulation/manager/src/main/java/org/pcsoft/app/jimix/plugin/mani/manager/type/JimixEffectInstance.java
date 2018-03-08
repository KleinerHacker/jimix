package org.pcsoft.app.jimix.plugin.mani.manager.type;

import javafx.scene.Node;
import org.pcsoft.app.jimix.commons.exception.JimixPluginExecutionException;
import org.pcsoft.app.jimix.plugin.common.manager.type.JimixInstance;
import org.pcsoft.app.jimix.plugin.mani.api.config.JimixEffectConfiguration;

import java.util.Objects;
import java.util.UUID;

public final class JimixEffectInstance implements JimixInstance<JimixEffectPlugin> {
    private final transient UUID uuid = UUID.randomUUID(); //Temporary identifier only
    private final JimixEffectPlugin plugin;
    private final JimixEffectConfiguration configuration;

    JimixEffectInstance(JimixEffectPlugin plugin, JimixEffectConfiguration configuration) {
        this.plugin = plugin;
        this.configuration = configuration;
    }

    public Node apply(Node node, int x, int y, int width, int height) throws JimixPluginExecutionException {
        return plugin.apply(node, x, y, width, height, configuration);
    }

    @Override
    public JimixEffectPlugin getPlugin() {
        return plugin;
    }

    public JimixEffectConfiguration getConfiguration() {
        return configuration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JimixEffectInstance instance = (JimixEffectInstance) o;
        return Objects.equals(uuid, instance.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }
}

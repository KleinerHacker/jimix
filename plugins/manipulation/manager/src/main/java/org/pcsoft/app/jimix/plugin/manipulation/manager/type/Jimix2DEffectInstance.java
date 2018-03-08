package org.pcsoft.app.jimix.plugin.manipulation.manager.type;

import javafx.scene.Node;
import org.pcsoft.app.jimix.commons.exception.JimixPluginExecutionException;
import org.pcsoft.app.jimix.plugin.common.manager.type.JimixInstance;
import org.pcsoft.app.jimix.plugin.manipulation.api.config.JimixEffectConfiguration;

import java.util.Objects;
import java.util.UUID;

public final class Jimix2DEffectInstance implements JimixInstance<Jimix2DEffectPlugin> {
    private final transient UUID uuid = UUID.randomUUID(); //Temporary identifier only
    private final Jimix2DEffectPlugin plugin;
    private final JimixEffectConfiguration configuration;

    Jimix2DEffectInstance(Jimix2DEffectPlugin plugin, JimixEffectConfiguration configuration) {
        this.plugin = plugin;
        this.configuration = configuration;
    }

    public Node apply(Node node, int x, int y, int width, int height) throws JimixPluginExecutionException {
        return plugin.apply(node, x, y, width, height, configuration);
    }

    @Override
    public Jimix2DEffectPlugin getPlugin() {
        return plugin;
    }

    public JimixEffectConfiguration getConfiguration() {
        return configuration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Jimix2DEffectInstance instance = (Jimix2DEffectInstance) o;
        return Objects.equals(uuid, instance.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }
}

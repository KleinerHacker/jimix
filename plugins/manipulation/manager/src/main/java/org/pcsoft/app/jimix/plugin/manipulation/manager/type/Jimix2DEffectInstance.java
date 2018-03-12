package org.pcsoft.app.jimix.plugin.manipulation.manager.type;

import javafx.scene.Node;
import org.pcsoft.app.jimix.commons.exception.JimixPluginExecutionException;
import org.pcsoft.app.jimix.plugin.manipulation.api.config.JimixEffectConfiguration;

public final class Jimix2DEffectInstance extends JimixEffectInstance<Jimix2DEffectPlugin, Jimix2DEffectInstance> {
    public Jimix2DEffectInstance(Jimix2DEffectPlugin plugin, JimixEffectConfiguration configuration) {
        super(plugin, configuration);
    }

    public Node apply(Node node, int x, int y) throws JimixPluginExecutionException {
        return plugin.apply(node, x, y, configuration);
    }

    @Override
    public Jimix2DEffectInstance copy() {
        return new Jimix2DEffectInstance(plugin, (JimixEffectConfiguration) configuration.copy());
    }
}

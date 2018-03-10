package org.pcsoft.app.jimix.plugin.manipulation.manager.type;

import javafx.scene.Node;
import org.pcsoft.app.jimix.commons.exception.JimixPluginExecutionException;
import org.pcsoft.app.jimix.plugin.manipulation.api.config.JimixEffectConfiguration;

public final class Jimix3DEffectInstance extends JimixEffectInstance<Jimix3DEffectPlugin> {
    public Jimix3DEffectInstance(Jimix3DEffectPlugin plugin, JimixEffectConfiguration configuration) {
        super(plugin, configuration);
    }

    public Node apply(Node node, int x, int y) throws JimixPluginExecutionException {
        return plugin.apply(node, x, y, configuration);
    }
}

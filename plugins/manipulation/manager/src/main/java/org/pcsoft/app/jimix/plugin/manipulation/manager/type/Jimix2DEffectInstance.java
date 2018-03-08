package org.pcsoft.app.jimix.plugin.manipulation.manager.type;

import javafx.scene.Node;
import org.pcsoft.app.jimix.commons.exception.JimixPluginExecutionException;
import org.pcsoft.app.jimix.plugin.manipulation.api.config.JimixEffectConfiguration;

public final class Jimix2DEffectInstance extends JimixEffectInstance<Jimix2DEffectPlugin> {
    public Jimix2DEffectInstance(Jimix2DEffectPlugin plugin, JimixEffectConfiguration configuration) {
        super(plugin, configuration);
    }

    public Node apply(Node node, int x, int y, int width, int height) throws JimixPluginExecutionException {
        return plugin.apply(node, x, y, width, height, configuration);
    }
}

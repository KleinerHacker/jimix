package org.pcsoft.app.jimix.plugins.manager.type;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import org.pcsoft.app.jimix.commons.exception.JimixPluginExecutionException;
import org.pcsoft.app.jimix.plugins.api.config.JimixEffectConfiguration;

public final class JimixEffectInstance implements JimixInstance<JimixEffectPlugin> {
    private final JimixEffectPlugin plugin;
    private final JimixEffectConfiguration configuration;

    JimixEffectInstance(JimixEffectPlugin plugin, JimixEffectConfiguration configuration) {
        this.plugin = plugin;
        this.configuration = configuration;
    }

    public void apply(Image image, int x, int y, GraphicsContext gc) throws JimixPluginExecutionException {
        plugin.apply(image, x, y, gc, configuration);
    }

    @Override
    public JimixEffectPlugin getPlugin() {
        return plugin;
    }

    public JimixEffectConfiguration getConfiguration() {
        return configuration;
    }
}

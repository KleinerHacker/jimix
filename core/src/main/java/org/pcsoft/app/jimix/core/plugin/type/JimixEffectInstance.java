package org.pcsoft.app.jimix.core.plugin.type;

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

    public Image apply(Image image) throws JimixPluginExecutionException {
        return plugin.apply(image, configuration);
    }

    @Override
    public JimixEffectPlugin getPlugin() {
        return plugin;
    }

    public JimixEffectConfiguration getConfiguration() {
        return configuration;
    }
}

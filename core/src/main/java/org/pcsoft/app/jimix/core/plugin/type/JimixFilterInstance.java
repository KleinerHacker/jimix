package org.pcsoft.app.jimix.core.plugin.type;

import javafx.scene.image.Image;
import org.pcsoft.app.jimix.commons.exception.JimixPluginExecutionException;
import org.pcsoft.app.jimix.plugins.api.config.JimixFilterConfiguration;
import org.pcsoft.app.jimix.plugins.api.type.JimixSource;

public final class JimixFilterInstance implements JimixInstance<JimixFilterPlugin> {
    private final JimixFilterPlugin plugin;
    private final JimixFilterConfiguration configuration;

    JimixFilterInstance(JimixFilterPlugin plugin, JimixFilterConfiguration configuration) {
        this.plugin = plugin;
        this.configuration = configuration;
    }

    public Image apply(Image image, JimixSource source) throws JimixPluginExecutionException {
        return plugin.apply(image, configuration, source);
    }

    @Override
    public JimixFilterPlugin getPlugin() {
        return plugin;
    }

    public JimixFilterConfiguration getConfiguration() {
        return configuration;
    }
}

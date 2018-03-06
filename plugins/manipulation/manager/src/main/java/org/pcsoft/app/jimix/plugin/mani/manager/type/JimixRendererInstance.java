package org.pcsoft.app.jimix.plugin.mani.manager.type;

import javafx.scene.image.Image;
import org.pcsoft.app.jimix.commons.exception.JimixPluginExecutionException;
import org.pcsoft.app.jimix.plugin.common.manager.type.JimixInstance;
import org.pcsoft.app.jimix.plugin.mani.api.config.JimixRendererConfiguration;
import org.pcsoft.app.jimix.plugin.mani.api.type.JimixSource;

public final class JimixRendererInstance implements JimixInstance<JimixRendererPlugin> {
    private final JimixRendererPlugin plugin;
    private final JimixRendererConfiguration configuration;

    JimixRendererInstance(JimixRendererPlugin plugin, JimixRendererConfiguration configuration) {
        this.plugin = plugin;
        this.configuration = configuration;
    }

    public Image apply(int width, int height, JimixSource applySource) throws JimixPluginExecutionException {
        return plugin.apply(width, height, configuration, applySource);
    }

    @Override
    public JimixRendererPlugin getPlugin() {
        return plugin;
    }

    public JimixRendererConfiguration getConfiguration() {
        return configuration;
    }
}
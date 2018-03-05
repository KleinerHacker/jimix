package org.pcsoft.app.jimix.plugins.manager.type;

import javafx.scene.image.Image;
import org.pcsoft.app.jimix.commons.exception.JimixPluginExecutionException;

public final class JimixBlenderInstance implements JimixInstance<JimixBlenderPlugin> {
    private final JimixBlenderPlugin plugin;

    JimixBlenderInstance(JimixBlenderPlugin plugin) {
        this.plugin = plugin;
    }

    public Image apply(Image groundImage, Image layerImage, double opacity) throws JimixPluginExecutionException {
        return plugin.apply(groundImage, layerImage, opacity);
    }

    @Override
    public JimixBlenderPlugin getPlugin() {
        return plugin;
    }
}

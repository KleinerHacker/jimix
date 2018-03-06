package org.pcsoft.app.jimix.plugin.mani.manager.type;

import javafx.scene.image.Image;
import org.pcsoft.app.jimix.commons.exception.JimixPluginExecutionException;
import org.pcsoft.app.jimix.plugin.common.manager.type.JimixInstance;

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

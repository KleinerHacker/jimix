package org.pcsoft.app.jimix.plugin.mani.manager.type;

import javafx.scene.image.Image;
import org.pcsoft.app.jimix.commons.exception.JimixPluginExecutionException;
import org.pcsoft.app.jimix.plugin.common.manager.type.JimixInstance;
import org.pcsoft.app.jimix.plugin.mani.api.type.JimixPluginElement;

public final class JimixElementDrawerInstance implements JimixInstance<JimixElementDrawerPlugin> {
    private final JimixElementDrawerPlugin plugin;

    JimixElementDrawerInstance(JimixElementDrawerPlugin plugin) {
        this.plugin = plugin;
    }

    public Image draw(JimixPluginElement elementModel, int width, int height) throws JimixPluginExecutionException {
        return plugin.draw(elementModel, width, height);
    }

    @Override
    public JimixElementDrawerPlugin getPlugin() {
        return plugin;
    }
}

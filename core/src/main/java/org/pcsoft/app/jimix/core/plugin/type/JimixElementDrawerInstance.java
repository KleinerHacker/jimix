package org.pcsoft.app.jimix.core.plugin.type;

import javafx.scene.image.Image;
import org.pcsoft.app.jimix.commons.exception.JimixPluginExecutionException;
import org.pcsoft.app.jimix.plugins.api.model.JimixElementModel;

public final class JimixElementDrawerInstance implements JimixInstance<JimixElementDrawerPlugin> {
    private final JimixElementDrawerPlugin plugin;

    JimixElementDrawerInstance(JimixElementDrawerPlugin plugin) {
        this.plugin = plugin;
    }

    public Image draw(JimixElementModel elementModel) throws JimixPluginExecutionException {
        return plugin.draw(elementModel);
    }

    @Override
    public JimixElementDrawerPlugin getPlugin() {
        return plugin;
    }
}

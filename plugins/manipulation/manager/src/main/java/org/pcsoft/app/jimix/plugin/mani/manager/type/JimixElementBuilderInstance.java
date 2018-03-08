package org.pcsoft.app.jimix.plugin.mani.manager.type;

import javafx.scene.Node;
import org.pcsoft.app.jimix.commons.exception.JimixPluginExecutionException;
import org.pcsoft.app.jimix.plugin.common.api.type.JimixPluginElement;
import org.pcsoft.app.jimix.plugin.common.manager.type.JimixInstance;

public final class JimixElementBuilderInstance implements JimixInstance<JimixElementBuilderPlugin> {
    private final JimixElementBuilderPlugin plugin;

    JimixElementBuilderInstance(JimixElementBuilderPlugin plugin) {
        this.plugin = plugin;
    }

    public Node buildNode(JimixPluginElement elementModel, int x, int y, int width, int height) throws JimixPluginExecutionException {
        return plugin.buildNode(elementModel, x, y, width, height);
    }

    @Override
    public JimixElementBuilderPlugin getPlugin() {
        return plugin;
    }
}

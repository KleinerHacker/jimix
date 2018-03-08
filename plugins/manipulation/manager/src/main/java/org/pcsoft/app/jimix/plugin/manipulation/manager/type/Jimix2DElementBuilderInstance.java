package org.pcsoft.app.jimix.plugin.manipulation.manager.type;

import javafx.scene.Node;
import org.pcsoft.app.jimix.commons.exception.JimixPluginExecutionException;
import org.pcsoft.app.jimix.plugin.common.api.type.JimixPlugin2DElement;
import org.pcsoft.app.jimix.plugin.common.manager.type.JimixInstance;

public final class Jimix2DElementBuilderInstance implements JimixInstance<Jimix2DElementBuilderPlugin> {
    private final Jimix2DElementBuilderPlugin plugin;

    Jimix2DElementBuilderInstance(Jimix2DElementBuilderPlugin plugin) {
        this.plugin = plugin;
    }

    public Node buildNode(JimixPlugin2DElement elementModel, int x, int y, int width, int height) throws JimixPluginExecutionException {
        return plugin.buildNode(elementModel, x, y, width, height);
    }

    @Override
    public Jimix2DElementBuilderPlugin getPlugin() {
        return plugin;
    }
}

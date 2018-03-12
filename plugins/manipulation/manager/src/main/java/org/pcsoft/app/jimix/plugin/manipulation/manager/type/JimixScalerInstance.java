package org.pcsoft.app.jimix.plugin.manipulation.manager.type;

import javafx.scene.image.Image;
import org.pcsoft.app.jimix.commons.exception.JimixPluginExecutionException;
import org.pcsoft.app.jimix.plugin.common.manager.type.JimixInstance;
import org.pcsoft.app.jimix.plugin.manipulation.api.type.JimixSource;

public final class JimixScalerInstance implements JimixInstance<JimixScalerPlugin, JimixScalerInstance> {
    private final JimixScalerPlugin plugin;

    JimixScalerInstance(JimixScalerPlugin plugin) {
        this.plugin = plugin;
    }

    public Image apply(Image image, int targetWidth, int targetHeight, JimixSource source) throws JimixPluginExecutionException {
        return plugin.apply(image, targetWidth, targetHeight, source);
    }

    @Override
    public JimixScalerPlugin getPlugin() {
        return plugin;
    }

    @Override
    public JimixScalerInstance copy() {
        return new JimixScalerInstance(plugin);
    }
}

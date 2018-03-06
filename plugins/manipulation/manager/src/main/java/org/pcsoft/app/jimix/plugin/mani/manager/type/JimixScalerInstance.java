package org.pcsoft.app.jimix.plugin.mani.manager.type;

import javafx.scene.image.Image;
import org.pcsoft.app.jimix.commons.exception.JimixPluginExecutionException;
import org.pcsoft.app.jimix.plugin.common.manager.type.JimixInstance;
import org.pcsoft.app.jimix.plugin.mani.api.type.JimixSource;

public final class JimixScalerInstance implements JimixInstance<JimixScalerPlugin> {
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
}

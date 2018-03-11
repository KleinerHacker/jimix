package org.pcsoft.app.jimix.plugin.manipulation.manager.type;

import javafx.scene.image.Image;
import org.pcsoft.app.jimix.commons.exception.JimixPluginExecutionException;
import org.pcsoft.app.jimix.plugin.common.manager.type.JimixInstance;

import java.util.Objects;
import java.util.UUID;

public final class JimixBlenderInstance implements JimixInstance<JimixBlenderPlugin> {
    private final UUID uuid = UUID.randomUUID();
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JimixBlenderInstance that = (JimixBlenderInstance) o;
        return Objects.equals(uuid, that.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }
}

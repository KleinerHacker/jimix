package org.pcsoft.app.jimix.plugin.manipulation.manager.type;

import javafx.scene.image.Image;
import org.pcsoft.app.jimix.commons.exception.JimixPluginExecutionException;
import org.pcsoft.app.jimix.plugin.common.manager.type.JimixInstance;
import org.pcsoft.app.jimix.plugin.manipulation.api.config.JimixFilterConfiguration;
import org.pcsoft.app.jimix.plugin.manipulation.api.type.JimixSource;

import java.util.Objects;
import java.util.UUID;

public final class JimixFilterInstance implements JimixInstance<JimixFilterPlugin> {
    private final transient UUID uuid = UUID.randomUUID(); //Temporary identifier only
    private final JimixFilterPlugin plugin;
    private final JimixFilterConfiguration configuration;

    JimixFilterInstance(JimixFilterPlugin plugin, JimixFilterConfiguration configuration) {
        this.plugin = plugin;
        this.configuration = configuration;
    }

    public Image apply(Image image, JimixSource source) throws JimixPluginExecutionException {
        return plugin.apply(image, configuration, source);
    }

    @Override
    public JimixFilterPlugin getPlugin() {
        return plugin;
    }

    public JimixFilterConfiguration getConfiguration() {
        return configuration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JimixFilterInstance that = (JimixFilterInstance) o;
        return Objects.equals(uuid, that.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }
}

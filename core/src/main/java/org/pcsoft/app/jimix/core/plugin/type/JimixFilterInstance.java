package org.pcsoft.app.jimix.core.plugin.type;

import org.pcsoft.app.jimix.commons.exception.JimixPluginAnnotationException;
import org.pcsoft.app.jimix.commons.exception.JimixPluginException;
import org.pcsoft.app.jimix.plugins.api.JimixFilter;
import org.pcsoft.app.jimix.plugins.api.annotation.JimixFilterDescriptor;
import org.pcsoft.app.jimix.plugins.api.config.JimixFilterConfiguration;
import org.pcsoft.app.jimix.plugins.api.type.JimixFilterType;

public final class JimixFilterInstance implements JimixInstance {
    private final JimixFilter instance;
    private final JimixFilterDescriptor descriptor;

    public JimixFilterInstance(JimixFilter instance) throws JimixPluginException {
        if (!instance.getClass().isAnnotationPresent(JimixFilterDescriptor.class))
            throw new JimixPluginAnnotationException("Unable to find needed annotation " + JimixFilterDescriptor.class.getName() + " on filter class " + instance.getClass().getName());

        this.instance = instance;
        this.descriptor = instance.getClass().getAnnotation(JimixFilterDescriptor.class);
    }

    @Override
    public String getName() {
        return descriptor.name();
    }

    @Override
    public String getDescription() {
        return descriptor.description();
    }

    public Class<? extends JimixFilterConfiguration> getConfigurationClass() {
        return descriptor.configurationClass();
    }

    public JimixFilterType getType() {
        return descriptor.type();
    }

    public boolean isOnTopLevel() {
        return descriptor.onTopLevel();
    }
}

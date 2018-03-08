package org.pcsoft.app.jimix.plugin.manipulation.api.type;


import org.pcsoft.app.jimix.plugin.manipulation.api.config.JimixConfiguration;

public interface JimixVariant<T extends JimixConfiguration> {
    String getName();
    T getConfiguration();
    boolean isBuiltin();
}

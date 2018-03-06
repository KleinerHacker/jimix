package org.pcsoft.app.jimix.plugin.mani.api.type;


import org.pcsoft.app.jimix.plugin.mani.api.config.JimixConfiguration;

public interface JimixVariant<T extends JimixConfiguration> {
    String getName();
    T getConfiguration();
    boolean isBuiltin();
}

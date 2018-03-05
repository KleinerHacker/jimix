package org.pcsoft.app.jimix.plugins.api.type;

import org.pcsoft.app.jimix.plugins.api.config.JimixConfiguration;

public interface JimixVariant<T extends JimixConfiguration> {
    String getName();
    T getConfiguration();
    boolean isBuiltin();
}

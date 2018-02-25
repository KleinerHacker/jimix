package org.pcsoft.app.jimix.core.plugin.type;

import org.pcsoft.app.jimix.commons.exception.JimixPluginException;

public interface JimixPlugin<T extends JimixInstance> {
    String getName();
    String getDescription();

    String getIdentifier();

    T createInstance() throws JimixPluginException;
}

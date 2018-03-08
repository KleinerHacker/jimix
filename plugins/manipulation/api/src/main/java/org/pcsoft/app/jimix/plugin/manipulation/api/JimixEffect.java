package org.pcsoft.app.jimix.plugin.manipulation.api;

import org.pcsoft.app.jimix.plugin.manipulation.api.config.JimixEffectConfiguration;
import org.pcsoft.app.jimix.plugin.manipulation.api.type.JimixEffectType;
import org.pcsoft.app.jimix.plugin.manipulation.api.type.JimixEffectVariant;

public interface JimixEffect<T extends JimixEffectConfiguration<T>> {

    /**
     * Returns the builtin variants for this effect. <b>No Resource Bundle is used for name.</b>
     *
     * @return
     */
    JimixEffectVariant<T>[] getVariants();

    JimixEffectType getType();

}

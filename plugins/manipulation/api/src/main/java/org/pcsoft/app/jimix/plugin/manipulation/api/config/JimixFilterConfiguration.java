package org.pcsoft.app.jimix.plugin.manipulation.api.config;

import org.pcsoft.app.jimix.plugin.manipulation.api.JimixFilter;

/**
 * Basic interface for all filter configuration, used by {@link JimixFilter}
 * @param <T> Concrete filter configuration implementation class
 */
public interface JimixFilterConfiguration<T extends JimixFilterConfiguration<T>> extends JimixConfiguration<T> {

}

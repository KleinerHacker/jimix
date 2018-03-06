package org.pcsoft.app.jimix.plugin.mani.api.config;

/**
 * Basic interface for all filter configuration, used by {@link org.pcsoft.app.jimix.plugin.mani.api.JimixFilter}
 * @param <T> Concrete filter configuration implementation class
 */
public interface JimixFilterConfiguration<T extends JimixFilterConfiguration<T>> extends JimixConfiguration<T> {

}

package org.pcsoft.app.jimix.plugin.mani.api.config;

/**
 * Basic interface for all filter configuration, used by {@link org.pcsoft.app.jimix.plugins.api.JimixFilter}
 * @param <T> Concrete filter configuration implementation class
 */
public interface JimixFilterConfiguration<T extends JimixFilterConfiguration<T>> extends JimixConfiguration {
    /**
     * Update this configuration to new values of given input configuration
     * @param newConfiguration
     */
    void update(T newConfiguration);
}

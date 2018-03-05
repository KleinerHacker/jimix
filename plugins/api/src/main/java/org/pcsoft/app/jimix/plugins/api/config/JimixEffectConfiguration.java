package org.pcsoft.app.jimix.plugins.api.config;

public interface JimixEffectConfiguration<T extends JimixEffectConfiguration<T>> extends JimixConfiguration {
    void update(T configuration);
}

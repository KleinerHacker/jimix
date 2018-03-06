package org.pcsoft.app.jimix.plugin.mani.api.config;

public interface JimixEffectConfiguration<T extends JimixEffectConfiguration<T>> extends JimixConfiguration {
    void update(T configuration);
}

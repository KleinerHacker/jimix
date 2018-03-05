package org.pcsoft.app.jimix.plugins.api.type;

import org.pcsoft.app.jimix.plugins.api.config.JimixEffectConfiguration;

public final class JimixEffectVariant<T extends JimixEffectConfiguration> implements JimixVariant<T> {
    private final String name;
    private final T configuration;
    private final boolean builtin;

    public JimixEffectVariant(String name, T configuration) {
        this.name = name;
        this.configuration = configuration;
        this.builtin = true; //TODO
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public T getConfiguration() {
        return configuration;
    }

    @Override
    public boolean isBuiltin() {
        return builtin;
    }

    @Override
    public String toString() {
        return "JimixEffectVariant{" +
                "name='" + name + '\'' +
                ", builtin=" + builtin +
                '}';
    }
}

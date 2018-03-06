package org.pcsoft.app.jimix.plugin.mani.api.type;


import org.pcsoft.app.jimix.plugin.mani.api.config.JimixEffectConfiguration;

public final class JimixEffectVariant<T extends JimixEffectConfiguration> implements JimixVariant<T> {
    public static <T extends JimixEffectConfiguration>JimixEffectVariant<T> createBuiltin(String name, T configuration) {
        return new JimixEffectVariant<>(name, configuration, true);
    }

    public static <T extends JimixEffectConfiguration>JimixEffectVariant<T> createCustom(String name, T configuration) {
        return new JimixEffectVariant<>(name, configuration, false);
    }

    private final String name;
    private final T configuration;
    private final boolean builtin;

    private JimixEffectVariant(String name, T configuration, boolean builtin) {
        this.name = name;
        this.configuration = configuration;
        this.builtin = builtin;
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

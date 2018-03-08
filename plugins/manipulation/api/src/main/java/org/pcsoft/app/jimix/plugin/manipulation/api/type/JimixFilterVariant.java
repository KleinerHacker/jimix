package org.pcsoft.app.jimix.plugin.manipulation.api.type;


import org.pcsoft.app.jimix.plugin.manipulation.api.config.JimixFilterConfiguration;

public final class JimixFilterVariant<T extends JimixFilterConfiguration> implements JimixVariant<T> {
    public static <T extends JimixFilterConfiguration>JimixFilterVariant<T> createBuiltin(String name, T configuration) {
        return new JimixFilterVariant<>(name, configuration, true);
    }

    public static <T extends JimixFilterConfiguration>JimixFilterVariant<T> createCustom(String name, T configuration) {
        return new JimixFilterVariant<>(name, configuration, false);
    }

    private final String name;
    private final T configuration;
    private final boolean builtin;

    private JimixFilterVariant(String name, T configuration, boolean builtin) {
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
        return "JimixFilterVariant{" +
                "name='" + name + '\'' +
                ", builtin=" + builtin +
                '}';
    }
}

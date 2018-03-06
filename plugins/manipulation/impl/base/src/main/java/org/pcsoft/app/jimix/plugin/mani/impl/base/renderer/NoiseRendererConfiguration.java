package org.pcsoft.app.jimix.plugin.mani.impl.base.renderer;

import javafx.beans.Observable;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import org.pcsoft.app.jimix.plugin.common.api.annotation.JimixProperty;
import org.pcsoft.app.jimix.plugin.mani.api.config.JimixRendererConfiguration;

import java.io.*;

public class NoiseRendererConfiguration implements JimixRendererConfiguration<NoiseRendererConfiguration> {
    @JimixProperty(fieldType = boolean.class, name = "Colored", description = "Noise with color or not")
    private final BooleanProperty colored = new SimpleBooleanProperty(true);
    @JimixProperty(fieldType = Long.class, name = "Base Value", description = "Base value for random")
    private final ObjectProperty<Long> randomBaseValue = new SimpleObjectProperty<>(null);

    public boolean isColored() {
        return colored.get();
    }

    public BooleanProperty coloredProperty() {
        return colored;
    }

    public void setColored(boolean colored) {
        this.colored.set(colored);
    }

    public Long getRandomBaseValue() {
        return randomBaseValue.get();
    }

    public ObjectProperty<Long> randomBaseValueProperty() {
        return randomBaseValue;
    }

    public void setRandomBaseValue(Long randomBaseValue) {
        this.randomBaseValue.set(randomBaseValue);
    }

    @Override
    public void update(NoiseRendererConfiguration newConfiguration) {
        this.colored.set(newConfiguration.colored.get());
        this.randomBaseValue.set(newConfiguration.randomBaseValue.get());
    }

    @Override
    public NoiseRendererConfiguration copy() {
        final NoiseRendererConfiguration configuration = new NoiseRendererConfiguration();
        configuration.colored.set(this.colored.get());
        configuration.randomBaseValue.set(this.randomBaseValue.get());

        return configuration;
    }

    @Override
    public void save(ObjectOutputStream out) throws IOException {
        out.writeBoolean(this.colored.get());
        out.writeLong(this.randomBaseValue.get());
    }

    @Override
    public void load(ObjectInputStream in) throws IOException {
        this.colored.set(in.readBoolean());
        this.randomBaseValue.set(in.readLong());
    }

    @Override
    public Observable[] getObservables() {
        return new Observable[]{
                colored, randomBaseValue
        };
    }
}

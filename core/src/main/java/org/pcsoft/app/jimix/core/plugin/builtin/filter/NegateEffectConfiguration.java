package org.pcsoft.app.jimix.core.plugin.builtin.filter;

import javafx.beans.Observable;
import org.pcsoft.app.jimix.plugin.mani.api.config.JimixFilterConfiguration;

import java.io.*;

public class NegateEffectConfiguration implements JimixFilterConfiguration<NegateEffectConfiguration> {
    @Override
    public void update(NegateEffectConfiguration newConfiguration) {
        //Empty
    }

    @Override
    public NegateEffectConfiguration copy() {
        return new NegateEffectConfiguration();
    }

    @Override
    public void save(ObjectOutputStream out) throws IOException {
        //Empty
    }

    @Override
    public void load(ObjectInputStream in) throws IOException {
        //Empty
    }

    @Override
    public Observable[] getObservables() {
        return new Observable[0];
    }
}

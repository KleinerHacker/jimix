package org.pcsoft.app.jimix.core.plugin.builtin.filter;

import javafx.beans.Observable;
import org.pcsoft.app.jimix.plugin.manipulation.api.config.JimixFilterConfiguration;

import java.io.*;

public class NegateFilterConfiguration implements JimixFilterConfiguration<NegateFilterConfiguration> {
    @Override
    public void update(NegateFilterConfiguration newConfiguration) {
        //Empty
    }

    @Override
    public NegateFilterConfiguration copy() {
        return new NegateFilterConfiguration();
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

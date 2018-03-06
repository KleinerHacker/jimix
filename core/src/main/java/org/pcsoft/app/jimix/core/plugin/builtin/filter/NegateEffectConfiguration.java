package org.pcsoft.app.jimix.core.plugin.builtin.filter;

import javafx.beans.Observable;
import org.pcsoft.app.jimix.plugin.mani.api.config.JimixFilterConfiguration;

public class NegateEffectConfiguration implements JimixFilterConfiguration<NegateEffectConfiguration> {
    @Override
    public void update(NegateEffectConfiguration newConfiguration) {
        
    }

    @Override
    public Observable[] getObservables() {
        return new Observable[0];
    }
}

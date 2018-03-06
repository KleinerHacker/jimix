package org.pcsoft.app.jimix.plugin.mani.api.config;

import javafx.beans.Observable;

import java.io.Serializable;

public interface JimixConfiguration extends Serializable {
    Observable[] getObservables();
}

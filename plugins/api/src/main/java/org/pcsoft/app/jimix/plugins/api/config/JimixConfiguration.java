package org.pcsoft.app.jimix.plugins.api.config;

import javafx.beans.Observable;

import java.io.Serializable;

public interface JimixConfiguration extends Serializable {
    Observable[] getObservables();
}

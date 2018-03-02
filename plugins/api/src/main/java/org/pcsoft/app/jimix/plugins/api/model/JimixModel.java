package org.pcsoft.app.jimix.plugins.api.model;

import javafx.beans.Observable;

/**
 * Interface base for model classes
 */
public interface JimixModel {
    /**
     * Returns all own observable values (like properties) for observation to rebuild result image
     * @return
     */
    Observable[] getObservableValues();
}

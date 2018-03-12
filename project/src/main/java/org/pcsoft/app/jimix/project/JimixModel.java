package org.pcsoft.app.jimix.project;

import javafx.beans.Observable;

/**
 * Interface base for model classes
 */
public interface JimixModel<T extends JimixModel<T>> {
    T copy();

    /**
     * Returns all own observable values (like properties) for observation to rebuild result image
     * @return
     */
    Observable[] getObservableValues();
}

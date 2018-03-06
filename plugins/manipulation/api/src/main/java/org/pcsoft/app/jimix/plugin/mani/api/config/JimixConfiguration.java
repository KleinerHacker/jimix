package org.pcsoft.app.jimix.plugin.mani.api.config;

import javafx.beans.Observable;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public interface JimixConfiguration<T extends JimixConfiguration<T>> {
    /**
     * Observable array with all observables of this configuration
     *
     * @return
     */
    Observable[] getObservables();

    /**
     * Update this configuration to new values of given input configuration
     *
     * @param newConfiguration
     */
    void update(T newConfiguration);

    /**
     * Copy this configuration
     *
     * @return
     */
    T copy();

    /**
     * Save configuration to given output stream
     *
     * @param out
     */
    void save(final ObjectOutputStream out) throws IOException;

    /**
     * Load configuration from given input stream
     *
     * @param in
     */
    void load(final ObjectInputStream in) throws IOException;
}

package org.pcsoft.app.jimix.app.ui.component;

import de.saxsys.mvvmfx.ViewModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ProjectInfoPaneViewModel implements ViewModel {
    private final StringProperty dimension = new SimpleStringProperty();

    public String getDimension() {
        return dimension.get();
    }

    public StringProperty dimensionProperty() {
        return dimension;
    }

    public void setDimension(String dimension) {
        this.dimension.set(dimension);
    }
}

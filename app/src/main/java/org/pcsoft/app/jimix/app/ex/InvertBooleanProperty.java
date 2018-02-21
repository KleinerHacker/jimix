package org.pcsoft.app.jimix.app.ex;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class InvertBooleanProperty extends SimpleBooleanProperty {
    private final BooleanProperty wrapperProperty;

    public InvertBooleanProperty(BooleanProperty wrapperProperty) {
        this.wrapperProperty = wrapperProperty;
        this.wrapperProperty.addListener(o -> this.fireValueChangedEvent());
    }

    @Override
    public boolean get() {
        return getValue();
    }

    @Override
    public void set(boolean newValue) {
        setValue(newValue);
    }

    @Override
    public void setValue(Boolean v) {
        this.wrapperProperty.setValue(!v);
    }

    @Override
    public Boolean getValue() {
        return !this.wrapperProperty.getValue();
    }
}

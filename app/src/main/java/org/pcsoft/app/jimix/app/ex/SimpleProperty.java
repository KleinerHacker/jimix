package org.pcsoft.app.jimix.app.ex;

import javafx.beans.value.ObservableValue;
import org.controlsfx.control.PropertySheet;

import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class SimpleProperty<T> implements PropertySheet.Item {
    private final Class<T> type;
    private final String name, description, category;
    private final Supplier<T> getter;
    private final Consumer<T> setter;

    public SimpleProperty(Class<T> type, String name, String description, String category, Supplier<T> getter, Consumer<T> setter) {
        this.type = type;
        this.name = name;
        this.description = description;
        this.category = category;
        this.getter = getter;
        this.setter = setter;
    }

    @Override
    public Class<?> getType() {
        return type;
    }

    @Override
    public String getCategory() {
        return category;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public Object getValue() {
        return getter.get();
    }

    @Override
    public void setValue(Object value) {
        setter.accept((T) value);
    }

    @Override
    public Optional<ObservableValue<? extends Object>> getObservableValue() {
        return Optional.empty();
    }
}

package org.pcsoft.app.jimix.core.plugin.builtin.model;

import javafx.beans.Observable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;
import org.pcsoft.app.jimix.core.plugin.builtin.scaler.DefaultScaler;
import org.pcsoft.app.jimix.plugins.api.model.JimixElementModel;

public final class JimixImageElementModel extends JimixElementModel {
    private final ObjectProperty<Image> value = new SimpleObjectProperty<>();
    private final StringProperty scaler = new SimpleStringProperty(DefaultScaler.class.getName());

    public JimixImageElementModel() {
    }

    public JimixImageElementModel(final Image image) {
        this.value.set(image);
        setWidth((int) image.getWidth());
        setHeight((int) image.getHeight());
    }

    public Image getValue() {
        return value.get();
    }

    public ObjectProperty<Image> valueProperty() {
        return value;
    }

    public void setValue(Image value) {
        this.value.set(value);
    }

    public String getScaler() {
        return scaler.get();
    }

    public StringProperty scalerProperty() {
        return scaler;
    }

    public void setScaler(String scaler) {
        this.scaler.set(scaler);
    }

    @Override
    protected Observable[] _getObservableValues() {
        return new Observable[] {
                value, scaler
        };
    }
}

package org.pcsoft.app.jimix.core.plugin.builtin.model;

import javafx.beans.Observable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.image.Image;
import org.pcsoft.app.jimix.plugins.api.model.JimixElementModel;

public final class JimixImageElementModel extends JimixElementModel {
    private final ObjectProperty<Image> value = new SimpleObjectProperty<>();

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

    @Override
    protected Observable[] _getObservableValues() {
        return new Observable[] {
                value
        };
    }
}

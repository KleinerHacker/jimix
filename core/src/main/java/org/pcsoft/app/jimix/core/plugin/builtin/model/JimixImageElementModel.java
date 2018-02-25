package org.pcsoft.app.jimix.core.plugin.builtin.model;

import javafx.beans.Observable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.image.Image;
import org.pcsoft.app.jimix.commons.exception.JimixPluginException;
import org.pcsoft.app.jimix.core.plugin.builtin.scaler.DefaultScaler;
import org.pcsoft.app.jimix.core.plugin.type.JimixScalerInstance;
import org.pcsoft.app.jimix.core.plugin.type.JimixScalerPlugin;
import org.pcsoft.app.jimix.plugins.api.model.JimixElementModel;

public final class JimixImageElementModel extends JimixElementModel {
    private final ObjectProperty<Image> value = new SimpleObjectProperty<>();
    private final ObjectProperty<JimixScalerInstance> scaler;

    public JimixImageElementModel() {
        try {
            scaler = new SimpleObjectProperty<>(new JimixScalerPlugin(new DefaultScaler()).createInstance());
        } catch (JimixPluginException e) {
            throw new RuntimeException(e);
        }
    }

    public JimixImageElementModel(final Image image) {
        this();
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

    public JimixScalerInstance getScaler() {
        return scaler.get();
    }

    public ObjectProperty<JimixScalerInstance> scalerProperty() {
        return scaler;
    }

    public void setScaler(JimixScalerInstance scaler) {
        this.scaler.set(scaler);
    }

    @Override
    protected Observable[] _getObservableValues() {
        return new Observable[] {
                value, scaler
        };
    }
}

package org.pcsoft.app.jimix.core.plugin.builtin.model;

import javafx.beans.Observable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.image.Image;
import org.pcsoft.app.jimix.commons.exception.JimixPluginException;
import org.pcsoft.app.jimix.core.plugin.builtin.scaler.DefaultScaler;
import org.pcsoft.app.jimix.plugin.common.api.type.JimixPluginElement;
import org.pcsoft.app.jimix.plugin.mani.manager.type.JimixScalerInstance;
import org.pcsoft.app.jimix.plugin.mani.manager.type.JimixScalerPlugin;

import java.awt.*;

public final class ImagePluginElement implements JimixPluginElement {
    private final ReadOnlyObjectWrapper<Image> value = new ReadOnlyObjectWrapper<>();
    private final ReadOnlyObjectProperty<Image> readOnlyValue = value.getReadOnlyProperty();
    private final ObjectProperty<JimixScalerInstance> scaler;

    public ImagePluginElement() {
        try {
            scaler = new SimpleObjectProperty<>(new JimixScalerPlugin(new DefaultScaler()).createInstance());
        } catch (JimixPluginException e) {
            throw new RuntimeException(e);
        }
    }

    public ImagePluginElement(final Image image) {
        this();
        this.value.set(image);
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
    public Image getPreview() {
        return value.get();
    }

    @Override
    public ReadOnlyObjectProperty<Image> previewProperty() {
        return readOnlyValue;
    }

    @Override
    public Dimension getPreferedSize() {
        return new Dimension((int)value.get().getWidth(), (int)value.get().getHeight());
    }

    @Override
    public Observable[] getObservables() {
        return new Observable[] {
                value, scaler
        };
    }
}

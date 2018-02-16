package org.pcsoft.app.jimix.core.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;

public final class JimixLevel {
    private final StringProperty name = new SimpleStringProperty("Level");
    private final ObjectProperty<Image> picture = new SimpleObjectProperty<>();
    private final ObjectProperty<Image> mask = new SimpleObjectProperty<>();

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public JimixLevel(Image picture) {
        this.picture.set(picture);
    }

    public Image getPicture() {
        return picture.get();
    }

    public ObjectProperty<Image> pictureProperty() {
        return picture;
    }

    public void setPicture(Image picture) {
        this.picture.set(picture);
    }

    public Image getMask() {
        return mask.get();
    }

    public ObjectProperty<Image> maskProperty() {
        return mask;
    }

    public void setMask(Image mask) {
        this.mask.set(mask);
    }

    @Override
    public String toString() {
        return "JimixLevel{" +
                "name=" + name +
                '}';
    }
}

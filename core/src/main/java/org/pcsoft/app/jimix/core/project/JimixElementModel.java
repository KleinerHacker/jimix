package org.pcsoft.app.jimix.core.project;

import javafx.beans.property.FloatProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;

public abstract class JimixElementModel {
    private final FloatProperty opacity = new SimpleFloatProperty(1f);
    private final IntegerProperty x = new SimpleIntegerProperty(0), y = new SimpleIntegerProperty(0);
    private final IntegerProperty width = new SimpleIntegerProperty(), height = new SimpleIntegerProperty();

    public int getX() {
        return x.get();
    }

    public IntegerProperty xProperty() {
        return x;
    }

    public void setX(int x) {
        this.x.set(x);
    }

    public int getY() {
        return y.get();
    }

    public IntegerProperty yProperty() {
        return y;
    }

    public void setY(int y) {
        this.y.set(y);
    }

    public int getWidth() {
        return width.get();
    }

    public IntegerProperty widthProperty() {
        return width;
    }

    public void setWidth(int width) {
        this.width.set(width);
    }

    public int getHeight() {
        return height.get();
    }

    public IntegerProperty heightProperty() {
        return height;
    }

    public void setHeight(int height) {
        this.height.set(height);
    }

    public float getOpacity() {
        return opacity.get();
    }

    public FloatProperty opacityProperty() {
        return opacity;
    }

    public void setOpacity(float opacity) {
        this.opacity.set(opacity);
    }
}

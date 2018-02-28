package org.pcsoft.app.jimix.plugins.api.model;

import javafx.beans.Observable;
import javafx.beans.property.*;
import org.apache.commons.lang.ArrayUtils;
import org.pcsoft.app.jimix.plugins.api.annotation.JimixProperty;
import org.pcsoft.app.jimix.plugins.api.annotation.JimixPropertyDoubleRestriction;

import java.awt.*;

public abstract class JimixElementModel implements JimixModel {
    @JimixProperty(fieldType = Double.class, name = "Opacity", description = "Opacity of element", category = "View")
    @JimixPropertyDoubleRestriction(minValue = 0d, maxValue = 1d)
    private final DoubleProperty opacity = new SimpleDoubleProperty(1d);
    @JimixProperty(fieldType = Integer.class, name = "X", description = "Left position of element", category = "Alignment")
    private final IntegerProperty x = new SimpleIntegerProperty(0);
    @JimixProperty(fieldType = Integer.class, name = "Y", description = "Top position of element", category = "Alignment")
    private final IntegerProperty y = new SimpleIntegerProperty(0);
    @JimixProperty(fieldType = Dimension.class, name = "Size", description = "Size of element", category = "Alignment")
    private final ObjectProperty<Dimension> dimension = new SimpleObjectProperty<>(new Dimension());
    private final BooleanProperty visibility = new SimpleBooleanProperty(true);

    public Dimension getDimension() {
        return dimension.get();
    }

    public ObjectProperty<Dimension> dimensionProperty() {
        return dimension;
    }

    public void setDimension(Dimension dimension) {
        this.dimension.set(dimension);
    }

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

    public double getOpacity() {
        return opacity.get();
    }

    public DoubleProperty opacityProperty() {
        return opacity;
    }

    public void setOpacity(double opacity) {
        this.opacity.set(opacity);
    }

    public boolean isVisibility() {
        return visibility.get();
    }

    public BooleanProperty visibilityProperty() {
        return visibility;
    }

    public void setVisibility(boolean visibility) {
        this.visibility.set(visibility);
    }

    public int getWidth() {
        return dimension.get().width;
    }

    public int getHeight() {
        return dimension.get().height;
    }

    @Override
    public final Observable[] getObservableValues() {
        return (Observable[]) ArrayUtils.addAll(new Observable[] {
                opacity, x, y, dimension, visibility
        }, _getObservableValues());
    }

    protected abstract Observable[] _getObservableValues();
}

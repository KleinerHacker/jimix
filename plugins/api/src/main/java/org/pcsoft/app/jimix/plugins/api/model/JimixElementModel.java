package org.pcsoft.app.jimix.plugins.api.model;

import javafx.beans.Observable;
import javafx.beans.property.*;
import org.apache.commons.lang.ArrayUtils;
import org.pcsoft.app.jimix.plugins.api.annotation.JimixProperty;
import org.pcsoft.app.jimix.plugins.api.annotation.JimixPropertyDoubleRestriction;

import java.awt.*;

/**
 * Represent a element model. This is the abstract base for all custom elements to show in image.
 */
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
    @JimixProperty(fieldType = Boolean.class, name = "Mirror Horizontal", description = "Mirror Horizontal", category = "View")
    private final BooleanProperty mirrorHorizontal = new SimpleBooleanProperty(false);
    @JimixProperty(fieldType = Boolean.class, name = "Mirror Vertical", description = "Mirror Vertical", category = "View")
    private final BooleanProperty mirrorVertical = new SimpleBooleanProperty(false);
    @JimixProperty(fieldType = Double.class, name = "Rotation", description = "Element Rotation", category = "View")
    @JimixPropertyDoubleRestriction(minValue = -180, maxValue = 180)
    private final DoubleProperty rotation = new SimpleDoubleProperty(0);

    private final BooleanProperty visibility = new SimpleBooleanProperty(true);

    public Dimension getDimension() {
        return dimension.get();
    }

    /**
     * Dimension of element
     * @return
     */
    public ObjectProperty<Dimension> dimensionProperty() {
        return dimension;
    }

    public void setDimension(Dimension dimension) {
        this.dimension.set(dimension);
    }

    public int getX() {
        return x.get();
    }

    /**
     * Left position of element
     * @return
     */
    public IntegerProperty xProperty() {
        return x;
    }

    public void setX(int x) {
        this.x.set(x);
    }

    public int getY() {
        return y.get();
    }

    /**
     * Top position of element
     * @return
     */
    public IntegerProperty yProperty() {
        return y;
    }

    public void setY(int y) {
        this.y.set(y);
    }

    public double getOpacity() {
        return opacity.get();
    }

    /**
     * Alpha value for element
     * @return
     */
    public DoubleProperty opacityProperty() {
        return opacity;
    }

    public void setOpacity(double opacity) {
        this.opacity.set(opacity);
    }

    public boolean isVisibility() {
        return visibility.get();
    }

    /**
     * Visibility of element (show or hide)
     * @return
     */
    public BooleanProperty visibilityProperty() {
        return visibility;
    }

    public void setVisibility(boolean visibility) {
        this.visibility.set(visibility);
    }

    /**
     * Extract width from {@link #dimensionProperty()}
     * @return
     */
    public int getWidth() {
        return dimension.get().width;
    }

    /**
     * Extract height from {@link #dimensionProperty()}
     * @return
     */
    public int getHeight() {
        return dimension.get().height;
    }

    public boolean isMirrorHorizontal() {
        return mirrorHorizontal.get();
    }

    /**
     * Defines state of mirroring on horizontal axe
     * @return
     */
    public BooleanProperty mirrorHorizontalProperty() {
        return mirrorHorizontal;
    }

    public void setMirrorHorizontal(boolean mirrorHorizontal) {
        this.mirrorHorizontal.set(mirrorHorizontal);
    }

    public boolean isMirrorVertical() {
        return mirrorVertical.get();
    }

    /**
     * Defines state of mirroring on vertical axe
     * @return
     */
    public BooleanProperty mirrorVerticalProperty() {
        return mirrorVertical;
    }

    public void setMirrorVertical(boolean mirrorVertical) {
        this.mirrorVertical.set(mirrorVertical);
    }

    public double getRotation() {
        return rotation.get();
    }

    /**
     * Defines rotation of element
     * @return
     */
    public DoubleProperty rotationProperty() {
        return rotation;
    }

    public void setRotation(double rotation) {
        this.rotation.set(rotation);
    }

    @Override
    public final Observable[] getObservableValues() {
        return (Observable[]) ArrayUtils.addAll(new Observable[] {
                opacity, x, y, dimension, visibility, mirrorHorizontal, mirrorVertical, rotation
        }, _getObservableValues());
    }

    protected abstract Observable[] _getObservableValues();
}

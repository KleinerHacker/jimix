package org.pcsoft.app.jimix.project;

import javafx.beans.Observable;
import javafx.beans.property.*;
import org.apache.commons.lang.ArrayUtils;
import org.pcsoft.app.jimix.plugin.common.api.annotation.JimixProperty;
import org.pcsoft.app.jimix.plugin.common.api.annotation.JimixPropertyDoubleRestriction;
import org.pcsoft.app.jimix.plugin.common.api.type.JimixPluginElement;

public abstract class JimixElementModel<T extends JimixElementModel<T>> implements JimixModel<T> {
    @JimixProperty(fieldType = Double.class, name = "Opacity", description = "Opacity of element", category = "View")
    @JimixPropertyDoubleRestriction(minValue = 0d, maxValue = 1d)
    private final DoubleProperty opacity = new SimpleDoubleProperty(1d);
    @JimixProperty(fieldType = Integer.class, name = "X", description = "Left position of element", category = "Alignment")
    private final IntegerProperty x = new SimpleIntegerProperty(0);
    @JimixProperty(fieldType = Integer.class, name = "Y", description = "Top position of element", category = "Alignment")
    private final IntegerProperty y = new SimpleIntegerProperty(0);
    @JimixProperty(fieldType = Boolean.class, name = "Mirror Horizontal", description = "Mirror Horizontal", category = "View")
    private final BooleanProperty mirrorHorizontal = new SimpleBooleanProperty(false);
    @JimixProperty(fieldType = Boolean.class, name = "Mirror Vertical", description = "Mirror Vertical", category = "View")
    private final BooleanProperty mirrorVertical = new SimpleBooleanProperty(false);
    @JimixProperty(fieldType = Double.class, name = "Rotation", description = "Element Rotation", category = "View")
    @JimixPropertyDoubleRestriction(minValue = -180, maxValue = 180)
    private final DoubleProperty rotation = new SimpleDoubleProperty(0);

    private final ReadOnlyObjectProperty<JimixPluginElement> pluginElement;

    protected JimixElementModel(final JimixPluginElement pluginElement) {
        this.pluginElement = new ReadOnlyObjectWrapper<>(pluginElement).getReadOnlyProperty();
    }

    public JimixPluginElement getPluginElement() {
        return pluginElement.get();
    }

    public ReadOnlyObjectProperty<JimixPluginElement> pluginElementProperty() {
        return pluginElement;
    }

    public int getX() {
        return x.get();
    }

    /**
     * Left position of element
     *
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
     *
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
     *
     * @return
     */
    public DoubleProperty opacityProperty() {
        return opacity;
    }

    public void setOpacity(double opacity) {
        this.opacity.set(opacity);
    }

    public boolean isMirrorHorizontal() {
        return mirrorHorizontal.get();
    }

    /**
     * Defines state of mirroring on horizontal axe
     *
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
     *
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
     *
     * @return
     */
    public DoubleProperty rotationProperty() {
        return rotation;
    }

    public void setRotation(double rotation) {
        this.rotation.set(rotation);
    }

    @Override
    public final T copy() {
        final T copy = _copy(pluginElement.get());
        copy.setX(this.x.get());
        copy.setY(this.y.get());
        copy.setRotation(this.rotation.get());
        copy.setMirrorHorizontal(this.mirrorHorizontal.get());
        copy.setMirrorVertical(this.mirrorVertical.get());
        copy.setOpacity(this.opacity.get());

        return copy;
    }

    protected abstract T _copy(final JimixPluginElement pluginElement);

    @Override
    public final Observable[] getObservables() {
        return (Observable[]) ArrayUtils.addAll(
                _getObservables(),
                ArrayUtils.addAll(
                        new Observable[]{opacity, x, y, mirrorHorizontal, mirrorVertical, rotation},
                        pluginElement.get().getObservables()
                )
        );
    }

    protected abstract Observable[] _getObservables();
}

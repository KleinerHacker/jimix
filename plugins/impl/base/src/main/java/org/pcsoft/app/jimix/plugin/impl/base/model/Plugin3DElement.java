package org.pcsoft.app.jimix.plugin.impl.base.model;

import javafx.beans.Observable;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.shape.DrawMode;
import org.apache.commons.lang.ArrayUtils;
import org.pcsoft.app.jimix.plugin.common.api.annotation.JimixProperty;
import org.pcsoft.app.jimix.plugin.common.api.annotation.JimixPropertyDoubleRestriction;
import org.pcsoft.app.jimix.plugin.common.api.type.JimixPlugin3DElement;

public abstract class Plugin3DElement implements JimixPlugin3DElement {
    @JimixProperty(fieldType = Double.class, name = "Position X (%)", description = "Rotate on X axis", category = "Light")
    @JimixPropertyDoubleRestriction(minValue = 0, maxValue = 1)
    private final DoubleProperty lightPositionX = new SimpleDoubleProperty(0.5d);
    @JimixProperty(fieldType = Double.class, name = "Position Y (%)", description = "Rotate on Y axis", category = "Light")
    @JimixPropertyDoubleRestriction(minValue = 0, maxValue = 1)
    private final DoubleProperty lightPositionY = new SimpleDoubleProperty(0.5d);
    @JimixProperty(fieldType = Double.class, name = "Position Z (%)", description = "Rotate on Z axis", category = "Light")
    @JimixPropertyDoubleRestriction(minValue = 0, maxValue = 1)
    private final DoubleProperty lightPositionZ = new SimpleDoubleProperty(0.5d);

    @JimixProperty(fieldType = DrawMode.class, name = "Draw Mode", description = "Draw mode of object", category = "Drawing")
    private final ObjectProperty<DrawMode> drawMode = new SimpleObjectProperty<>(DrawMode.FILL);

    public double getLightPositionX() {
        return lightPositionX.get();
    }

    public DoubleProperty lightPositionXProperty() {
        return lightPositionX;
    }

    public void setLightPositionX(double lightPositionX) {
        this.lightPositionX.set(lightPositionX);
    }

    public double getLightPositionY() {
        return lightPositionY.get();
    }

    public DoubleProperty lightPositionYProperty() {
        return lightPositionY;
    }

    public void setLightPositionY(double lightPositionY) {
        this.lightPositionY.set(lightPositionY);
    }

    public double getLightPositionZ() {
        return lightPositionZ.get();
    }

    public DoubleProperty lightPositionZProperty() {
        return lightPositionZ;
    }

    public void setLightPositionZ(double lightPositionZ) {
        this.lightPositionZ.set(lightPositionZ);
    }

    public DrawMode getDrawMode() {
        return drawMode.get();
    }

    public ObjectProperty<DrawMode> drawModeProperty() {
        return drawMode;
    }

    public void setDrawMode(DrawMode drawMode) {
        this.drawMode.set(drawMode);
    }

    @Override
    public final Observable[] getObservables() {
        return (Observable[]) ArrayUtils.addAll(
                new Observable[] {
                        lightPositionX, lightPositionY, lightPositionZ, drawMode
                },
                _getObservables()
        );
    }

    protected abstract Observable[] _getObservables();
}

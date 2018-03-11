package org.pcsoft.app.jimix.plugin.impl.base.model;

import javafx.beans.Observable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.shape.DrawMode;
import org.apache.commons.lang.ArrayUtils;
import org.pcsoft.app.jimix.plugin.common.api.annotation.JimixProperty;
import org.pcsoft.app.jimix.plugin.common.api.type.JimixPlugin3DElement;

public abstract class Plugin3DElement implements JimixPlugin3DElement {
    @JimixProperty(fieldType = DrawMode.class, name = "Draw Mode", description = "Draw mode of object", category = "Drawing")
    private final ObjectProperty<DrawMode> drawMode = new SimpleObjectProperty<>(DrawMode.FILL);

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
                        drawMode
                },
                _getObservables()
        );
    }

    protected abstract Observable[] _getObservables();
}

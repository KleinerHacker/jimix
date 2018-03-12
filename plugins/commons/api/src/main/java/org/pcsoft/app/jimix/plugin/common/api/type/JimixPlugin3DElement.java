package org.pcsoft.app.jimix.plugin.common.api.type;

import javafx.beans.Observable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.shape.DrawMode;
import org.apache.commons.lang.ArrayUtils;
import org.pcsoft.app.jimix.plugin.common.api.annotation.JimixProperty;

public abstract class JimixPlugin3DElement<T extends JimixPlugin3DElement<T>> implements JimixPluginElement<T> {
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

    @Override
    public final JimixElementType getType() {
        return JimixElementType.Element3D;
    }
}

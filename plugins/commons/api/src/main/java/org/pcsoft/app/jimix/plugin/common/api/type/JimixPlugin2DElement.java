package org.pcsoft.app.jimix.plugin.common.api.type;

import javafx.beans.Observable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import org.apache.commons.lang.ArrayUtils;
import org.pcsoft.app.jimix.plugin.common.api.annotation.JimixProperty;

public abstract class JimixPlugin2DElement<T extends JimixPlugin2DElement<T>> implements JimixPluginElement<T> {
    @JimixProperty(fieldType = Paint.class, name = "Fill", description = "Fill of rectangle")
    private final ObjectProperty<Paint> fill = new SimpleObjectProperty<>(Color.PURPLE);

    public Paint getFill() {
        return fill.get();
    }

    public ObjectProperty<Paint> fillProperty() {
        return fill;
    }

    public void setFill(Paint fill) {
        this.fill.set(fill);
    }

    @Override
    public final Observable[] getObservables() {
        return (Observable[]) ArrayUtils.addAll(
                new Observable[] {
                        fill
                },
                _getObservables()
        );
    }

    protected abstract Observable[] _getObservables();

    @Override
    public final JimixElementType getType() {
        return JimixElementType.Element2D;
    }
}

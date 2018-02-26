package org.pcsoft.app.jimix.plugin.impl.base.filter;

import javafx.beans.Observable;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import org.pcsoft.app.jimix.plugins.api.annotation.JimixProperty;
import org.pcsoft.app.jimix.plugins.api.annotation.JimixPropertyIntegerRestriction;
import org.pcsoft.app.jimix.plugins.api.config.JimixFilterConfiguration;

public class ScrimDiffuserConfiguration implements JimixFilterConfiguration {
    @JimixProperty(fieldType = int.class, name = "Radius", description = "Radius of blur", category = "View")
    @JimixPropertyIntegerRestriction(minValue = 1, maxValue = 10)
    private final IntegerProperty radius = new SimpleIntegerProperty(5);

    public int getRadius() {
        return radius.get();
    }

    public IntegerProperty radiusProperty() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius.set(radius);
    }

    @Override
    public Observable[] getObservables() {
        return new Observable[] {
                radius
        };
    }
}

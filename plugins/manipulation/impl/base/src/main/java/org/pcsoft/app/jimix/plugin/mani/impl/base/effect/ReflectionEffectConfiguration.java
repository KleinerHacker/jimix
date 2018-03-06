package org.pcsoft.app.jimix.plugin.mani.impl.base.effect;

import javafx.beans.Observable;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import org.pcsoft.app.jimix.plugin.common.api.annotation.JimixProperty;
import org.pcsoft.app.jimix.plugin.common.api.annotation.JimixPropertyDoubleRestriction;
import org.pcsoft.app.jimix.plugin.mani.api.config.JimixEffectConfiguration;

public class ReflectionEffectConfiguration implements JimixEffectConfiguration<ReflectionEffectConfiguration> {
    @JimixProperty(fieldType = Integer.class, name = "Offset", description = "Top Offset")
    private final IntegerProperty offset = new SimpleIntegerProperty(0);
    @JimixProperty(fieldType = Double.class, name = "Top Opacity", description = "Top Opacity")
    @JimixPropertyDoubleRestriction(minValue = 0d, maxValue = 1d)
    private final DoubleProperty topOpacity = new SimpleDoubleProperty(0.5d);
    @JimixProperty(fieldType = Double.class, name = "Bottom Opacity", description = "Bottom Opacity")
    @JimixPropertyDoubleRestriction(minValue = 0d, maxValue = 1d)
    private final DoubleProperty bottomOpacity = new SimpleDoubleProperty(0d);
    @JimixProperty(fieldType = Double.class, name = "Fraction", description = "Fraction")
    @JimixPropertyDoubleRestriction(minValue = 0.1d, maxValue = 1d)
    private final DoubleProperty fraction = new SimpleDoubleProperty(0.75d);

    @Override
    public void update(ReflectionEffectConfiguration configuration) {
        this.offset.set(configuration.offset.get());
        this.topOpacity.set(configuration.topOpacity.get());
        this.bottomOpacity.set(configuration.bottomOpacity.get());
        this.fraction.set(configuration.fraction.get());
    }

    public int getOffset() {
        return offset.get();
    }

    public IntegerProperty offsetProperty() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset.set(offset);
    }

    public double getTopOpacity() {
        return topOpacity.get();
    }

    public DoubleProperty topOpacityProperty() {
        return topOpacity;
    }

    public void setTopOpacity(double topOpacity) {
        this.topOpacity.set(topOpacity);
    }

    public double getBottomOpacity() {
        return bottomOpacity.get();
    }

    public DoubleProperty bottomOpacityProperty() {
        return bottomOpacity;
    }

    public void setBottomOpacity(double bottomOpacity) {
        this.bottomOpacity.set(bottomOpacity);
    }

    public double getFraction() {
        return fraction.get();
    }

    public DoubleProperty fractionProperty() {
        return fraction;
    }

    public void setFraction(double fraction) {
        this.fraction.set(fraction);
    }

    @Override
    public Observable[] getObservables() {
        return new Observable[] {
                offset, topOpacity, bottomOpacity, fraction
        };
    }
}

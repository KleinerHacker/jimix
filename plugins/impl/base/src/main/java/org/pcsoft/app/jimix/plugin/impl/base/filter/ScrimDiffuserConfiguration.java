package org.pcsoft.app.jimix.plugin.impl.base.filter;

import org.pcsoft.app.jimix.plugins.api.config.JimixEffectConfiguration;
import org.pcsoft.app.jimix.plugins.api.config.JimixFilterConfiguration;

public class ScrimDiffuserConfiguration implements JimixFilterConfiguration {
    private int radius = 5;

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }
}

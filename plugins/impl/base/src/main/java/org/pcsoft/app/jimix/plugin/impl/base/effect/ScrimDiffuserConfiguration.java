package org.pcsoft.app.jimix.plugin.impl.base.effect;

import org.pcsoft.app.jimix.plugins.api.config.JimixEffectConfiguration;

public class ScrimDiffuserConfiguration implements JimixEffectConfiguration {
    private int radius = 5;

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }
}

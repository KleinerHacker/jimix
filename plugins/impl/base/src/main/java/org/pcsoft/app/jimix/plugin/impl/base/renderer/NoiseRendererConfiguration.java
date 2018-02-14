package org.pcsoft.app.jimix.plugin.impl.base.renderer;

import org.pcsoft.app.jimix.plugins.api.config.JimixRendererConfiguration;

public class NoiseRendererConfiguration implements JimixRendererConfiguration {
    private boolean colored = true;
    private Long randomBaseValue = null;

    public boolean isColored() {
        return colored;
    }

    public void setColored(boolean colored) {
        this.colored = colored;
    }

    public Long getRandomBaseValue() {
        return randomBaseValue;
    }

    public void setRandomBaseValue(Long randomBaseValue) {
        this.randomBaseValue = randomBaseValue;
    }
}

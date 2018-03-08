package org.pcsoft.app.jimix.plugin.common.api.type;

import java.awt.*;

public interface JimixPlugin2DElement extends JimixPluginElement {
    Dimension getPreferedSize();

    @Override
    default JimixElementType getType() {
        return JimixElementType.Element2D;
    }
}

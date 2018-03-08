package org.pcsoft.app.jimix.plugin.common.api.type;

public interface JimixPlugin3DElement extends JimixPluginElement {
    @Override
    default JimixElementType getType() {
        return JimixElementType.Element3D;
    }
}

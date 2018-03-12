package org.pcsoft.app.jimix.plugin.manipulation.manager.type;

public final class Jimix3DElementBuilderInstance extends JimixElementBuilderInstance<Jimix3DElementBuilderPlugin, Jimix3DElementBuilderInstance> {
    public Jimix3DElementBuilderInstance(Jimix3DElementBuilderPlugin plugin) {
        super(plugin);
    }

    @Override
    public Jimix3DElementBuilderInstance copy() {
        return new Jimix3DElementBuilderInstance(plugin);
    }
}



package org.pcsoft.app.jimix.plugin.manipulation.manager.type;

public final class Jimix2DElementBuilderInstance extends JimixElementBuilderInstance<Jimix2DElementBuilderPlugin, Jimix2DElementBuilderInstance> {
    public Jimix2DElementBuilderInstance(Jimix2DElementBuilderPlugin plugin) {
        super(plugin);
    }

    @Override
    public Jimix2DElementBuilderInstance copy() {
        return new Jimix2DElementBuilderInstance(plugin);
    }
}

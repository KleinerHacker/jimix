package org.pcsoft.app.jimix.plugin.manipulation.manager;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class ManipulationPluginManagerTest {
    @Test
    public void test() throws IOException {
        final ManipulationPluginManager pluginManager = ManipulationPluginManager.getInstance();
        final File basePlugin = new File("plugins/impl/base/target/classes");
        final File builtinPlugin = new File("core/target/classes");
        System.out.println("Plugin Paths: " + basePlugin.getAbsolutePath() + ", " + builtinPlugin.getAbsolutePath());
        pluginManager.init(Arrays.asList(basePlugin, builtinPlugin));

        /*Assert.assertEquals(4, pluginManager.getAllFilters().length);
        Assert.assertEquals(7, pluginManager.getAll2DEffects().length);
        Assert.assertEquals(1, pluginManager.getAll3DEffects().length);
        Assert.assertEquals(1, pluginManager.getAllRenderers().length);
        Assert.assertEquals(5, pluginManager.getAllBlenders().length);
        Assert.assertEquals(1, pluginManager.getAllScalers().length);
        Assert.assertEquals(1, pluginManager.getAll2DElementBuilders().length);
        Assert.assertEquals(2, pluginManager.getAll3DElementBuilders().length);*/
    }

}
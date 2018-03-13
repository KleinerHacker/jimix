package org.pcsoft.app.jimix.plugin.system.manager;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class SystemPluginManagerTest {
    @Test
    public void test() throws IOException {
        final SystemPluginManager pluginManager = SystemPluginManager.getInstance();
        final File basePlugin = new File("plugins/impl/base/target/classes");
        final File builtinPlugin = new File("core/target/classes");
        System.out.println("Plugin Paths: " + basePlugin.getAbsolutePath() + ", " + builtinPlugin.getAbsolutePath());
        pluginManager.init(Arrays.asList(basePlugin, builtinPlugin));

        /*Assert.assertEquals(1, pluginManager.getAllClipboardProviders().length);
        Assert.assertEquals(3, pluginManager.getAllImageFileTypeProviders().length);
        Assert.assertEquals(1, pluginManager.getAllProjectFileTypeProviders().length);*/
    }
}
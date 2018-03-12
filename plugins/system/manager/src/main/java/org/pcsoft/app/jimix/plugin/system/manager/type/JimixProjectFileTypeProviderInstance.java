package org.pcsoft.app.jimix.plugin.system.manager.type;

import org.pcsoft.app.jimix.plugin.common.manager.type.JimixInstance;
import org.pcsoft.app.jimix.project.JimixProjectModel;

import java.io.File;
import java.io.IOException;

public final class JimixProjectFileTypeProviderInstance implements JimixInstance<JimixProjectFileTypeProviderPlugin, JimixProjectFileTypeProviderInstance> {
    private final JimixProjectFileTypeProviderPlugin plugin;

    JimixProjectFileTypeProviderInstance(JimixProjectFileTypeProviderPlugin plugin) {
        this.plugin = plugin;
    }

    public boolean acceptFile(File file) throws IOException {
        return plugin.acceptFile(file);
    }

    public void save(JimixProjectModel projectModel, File file) throws IOException {
        plugin.save(projectModel, file);
    }

    public JimixProjectModel load(File file) throws IOException {
        return plugin.load(file);
    }

    @Override
    public JimixProjectFileTypeProviderPlugin getPlugin() {
        return plugin;
    }

    @Override
    public JimixProjectFileTypeProviderInstance copy() {
        return new JimixProjectFileTypeProviderInstance(plugin);
    }
}

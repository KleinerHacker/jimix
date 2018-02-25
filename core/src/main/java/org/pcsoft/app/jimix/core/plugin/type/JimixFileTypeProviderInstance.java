package org.pcsoft.app.jimix.core.plugin.type;

import javafx.scene.image.Image;

import java.io.File;
import java.io.IOException;

public final class JimixFileTypeProviderInstance implements JimixInstance<JimixFileTypeProviderPlugin> {
    private final JimixFileTypeProviderPlugin plugin;

    JimixFileTypeProviderInstance(JimixFileTypeProviderPlugin plugin) {
        this.plugin = plugin;
    }

    public boolean acceptFile(File file) throws IOException {
        return plugin.acceptFile(file);
    }

    public void save(Image image, File file) throws IOException {
        plugin.save(image, file);
    }

    public Image load(File file) throws IOException {
        return plugin.load(file);
    }

    @Override
    public JimixFileTypeProviderPlugin getPlugin() {
        return plugin;
    }
}

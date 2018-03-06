package org.pcsoft.app.jimix.plugin.system.manager.type;

import javafx.scene.image.Image;
import org.pcsoft.app.jimix.plugin.common.manager.type.JimixInstance;

import java.io.File;
import java.io.IOException;

public final class JimixImageFileTypeProviderInstance implements JimixInstance<JimixImageFileTypeProviderPlugin> {
    private final JimixImageFileTypeProviderPlugin plugin;

    JimixImageFileTypeProviderInstance(JimixImageFileTypeProviderPlugin plugin) {
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
    public JimixImageFileTypeProviderPlugin getPlugin() {
        return plugin;
    }
}

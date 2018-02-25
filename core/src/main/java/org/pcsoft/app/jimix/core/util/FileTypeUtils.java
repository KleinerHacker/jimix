package org.pcsoft.app.jimix.core.util;

import org.pcsoft.app.jimix.core.plugin.PluginManager;
import org.pcsoft.app.jimix.core.plugin.type.JimixFileTypeProviderInstance;

import java.io.File;
import java.io.IOException;

public final class FileTypeUtils {
    public static JimixFileTypeProviderInstance find(final File file) throws IOException {
        for (final JimixFileTypeProviderInstance fileTypeProvider : PluginManager.getInstance().getAllFileTypeProviders()) {
            if (fileTypeProvider.acceptFile(file)) {
                return fileTypeProvider;
            }
        }

        return null;
    }

    private FileTypeUtils() {
    }
}

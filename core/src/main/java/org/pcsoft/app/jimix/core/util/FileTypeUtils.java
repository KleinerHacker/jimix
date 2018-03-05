package org.pcsoft.app.jimix.core.util;

import org.pcsoft.app.jimix.commons.exception.JimixPluginException;
import org.pcsoft.app.jimix.plugins.manager.PluginManager;
import org.pcsoft.app.jimix.plugins.manager.type.JimixFileTypeProviderInstance;
import org.pcsoft.app.jimix.plugins.manager.type.JimixFileTypeProviderPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

public final class FileTypeUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileTypeUtils.class);

    public static JimixFileTypeProviderInstance find(final File file) throws IOException {
        for (final JimixFileTypeProviderPlugin fileTypeProvider : PluginManager.getInstance().getAllFileTypeProviders()) {
            try {
                final JimixFileTypeProviderInstance instance = fileTypeProvider.createInstance();
                if (instance.acceptFile(file)) {
                    return instance;
                }
            } catch (JimixPluginException e) {
                LOGGER.error("Unable to create instance for " + fileTypeProvider.getIdentifier() + ", skip", e);
                continue;
            }
        }

        return null;
    }

    private FileTypeUtils() {
    }
}

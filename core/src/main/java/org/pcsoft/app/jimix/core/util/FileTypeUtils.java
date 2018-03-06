package org.pcsoft.app.jimix.core.util;

import org.pcsoft.app.jimix.commons.exception.JimixPluginException;
import org.pcsoft.app.jimix.plugin.system.manager.SystemPluginManager;
import org.pcsoft.app.jimix.plugin.system.manager.type.JimixImageFileTypeProviderInstance;
import org.pcsoft.app.jimix.plugin.system.manager.type.JimixImageFileTypeProviderPlugin;
import org.pcsoft.app.jimix.plugin.system.manager.type.JimixProjectFileTypeProviderInstance;
import org.pcsoft.app.jimix.plugin.system.manager.type.JimixProjectFileTypeProviderPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

public final class FileTypeUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileTypeUtils.class);

    public static JimixImageFileTypeProviderInstance findImageSupport(final File file) throws IOException {
        for (final JimixImageFileTypeProviderPlugin imageFileTypeProvider : SystemPluginManager.getInstance().getAllImageFileTypeProviders()) {
            try {
                final JimixImageFileTypeProviderInstance instance = imageFileTypeProvider.createInstance();
                if (instance.acceptFile(file)) {
                    return instance;
                }
            } catch (JimixPluginException e) {
                LOGGER.error("Unable to create instance for " + imageFileTypeProvider.getIdentifier() + ", skip", e);
                continue;
            }
        }

        return null;
    }

    public static JimixProjectFileTypeProviderInstance findProjectSupport(final File file) throws IOException {
        for (final JimixProjectFileTypeProviderPlugin projectFileTypeProvider : SystemPluginManager.getInstance().getAllProjectFileTypeProviders()) {
            try {
                final JimixProjectFileTypeProviderInstance instance = projectFileTypeProvider.createInstance();
                if (instance.acceptFile(file)) {
                    return instance;
                }
            } catch (JimixPluginException e) {
                LOGGER.error("Unable to create instance for " + projectFileTypeProvider.getIdentifier() + ", skip", e);
                continue;
            }
        }

        return null;
    }

    private FileTypeUtils() {
    }
}

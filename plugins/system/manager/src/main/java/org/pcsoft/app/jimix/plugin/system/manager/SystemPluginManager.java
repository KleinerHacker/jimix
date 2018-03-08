package org.pcsoft.app.jimix.plugin.system.manager;

import org.apache.commons.lang.StringUtils;
import org.pcsoft.app.jimix.commons.exception.JimixPluginException;
import org.pcsoft.app.jimix.plugin.common.manager.PluginManager;
import org.pcsoft.app.jimix.plugin.system.api.JimixClipboardProvider;
import org.pcsoft.app.jimix.plugin.system.api.JimixImageFileTypeProvider;
import org.pcsoft.app.jimix.plugin.system.api.JimixProjectFileTypeProvider;
import org.pcsoft.app.jimix.plugin.system.manager.type.JimixClipboardProviderPlugin;
import org.pcsoft.app.jimix.plugin.system.manager.type.JimixImageFileTypeProviderPlugin;
import org.pcsoft.app.jimix.plugin.system.manager.type.JimixProjectFileTypeProviderPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.stream.Collectors;

public final class SystemPluginManager implements PluginManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(SystemPluginManager.class);
    private static final SystemPluginManager instance = new SystemPluginManager();

    public static SystemPluginManager getInstance() {
        return instance;
    }

    private final Map<String, JimixClipboardProviderPlugin> clipboardProviderMap = new HashMap<>();
    private final Map<String, JimixImageFileTypeProviderPlugin> imageFileTypeProviderMap = new HashMap<>();
    private final Map<String, JimixProjectFileTypeProviderPlugin> projectFileTypeProviderMap = new HashMap<>();

    private ClassLoader classLoader;

    private SystemPluginManager() {
    }

    @Override
    public void init(final List<File> pluginPathList) throws IOException {
        LOGGER.debug("Initialize system plugin manager");
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("> Load plugins from paths: [" + StringUtils.join(pluginPathList.stream().map(File::getAbsolutePath).collect(Collectors.toList()), ',') + "]");
        }

        final List<URL> pluginUrlList = new ArrayList<>();
        for (final File pluginPath : pluginPathList) {
            pluginUrlList.add(pluginPath.toURI().toURL());
        }

        classLoader = new URLClassLoader(pluginUrlList.toArray(new URL[pluginUrlList.size()]),
                SystemPluginManager.class.getClassLoader());

        loadClipboardProvider(classLoader);
        loadImageFileTypeProvider(classLoader);
        loadProjectFileTypeProvider(classLoader);

        LOGGER.debug("Found {} clipboard providers, {} image file type providers, {} project file type providers",
                clipboardProviderMap.size(), imageFileTypeProviderMap.size(), projectFileTypeProviderMap.size());
    }

    private void loadClipboardProvider(final ClassLoader classLoader) {
        LOGGER.debug("> Load clipboard provider plugins");

        final ServiceLoader<JimixClipboardProvider> jimixClipboardProviders = ServiceLoader.load(JimixClipboardProvider.class, classLoader);
        for (final JimixClipboardProvider jimixClipboardProvider : jimixClipboardProviders) {
            try {
                final JimixClipboardProviderPlugin jimixClipboardProviderInstance = new JimixClipboardProviderPlugin(jimixClipboardProvider);
                clipboardProviderMap.put(jimixClipboardProviderInstance.getIdentifier(), jimixClipboardProviderInstance);
                LOGGER.trace(">>> {}", jimixClipboardProviderInstance.getIdentifier());
            } catch (JimixPluginException e) {
                LOGGER.error("Unable to load clipboard provider " + jimixClipboardProvider.getClass().getName(), e);
            }
        }
    }

    private void loadImageFileTypeProvider(final ClassLoader classLoader) {
        LOGGER.debug("> Load image file type provider plugins");

        final ServiceLoader<JimixImageFileTypeProvider> jimixImageFileTypeProviders = ServiceLoader.load(JimixImageFileTypeProvider.class, classLoader);
        for (final JimixImageFileTypeProvider jimixImageFileTypeProvider : jimixImageFileTypeProviders) {
            try {
                final JimixImageFileTypeProviderPlugin jimixImageFileTypeProviderPlugin = new JimixImageFileTypeProviderPlugin(jimixImageFileTypeProvider);
                imageFileTypeProviderMap.put(jimixImageFileTypeProviderPlugin.getIdentifier(), jimixImageFileTypeProviderPlugin);
                LOGGER.trace(">>> {}", jimixImageFileTypeProviderPlugin.getIdentifier());
            } catch (JimixPluginException e) {
                LOGGER.error("Unable to load image file type provider " + jimixImageFileTypeProvider.getClass().getName(), e);
            }
        }
    }

    private void loadProjectFileTypeProvider(final ClassLoader classLoader) {
        LOGGER.debug("> Load project file type provider plugins");

        final ServiceLoader<JimixProjectFileTypeProvider> jimixProjectFileTypeProviders = ServiceLoader.load(JimixProjectFileTypeProvider.class, classLoader);
        for (final JimixProjectFileTypeProvider jimixProjectFileTypeProvider : jimixProjectFileTypeProviders) {
            try {
                final JimixProjectFileTypeProviderPlugin jimixProjectFileTypeProviderPlugin = new JimixProjectFileTypeProviderPlugin(jimixProjectFileTypeProvider);
                projectFileTypeProviderMap.put(jimixProjectFileTypeProviderPlugin.getIdentifier(), jimixProjectFileTypeProviderPlugin);
                LOGGER.trace(">>> {}", jimixProjectFileTypeProviderPlugin.getIdentifier());
            } catch (JimixPluginException e) {
                LOGGER.error("Unable to load project image file type provider " + jimixProjectFileTypeProvider.getClass().getName(), e);
            }
        }
    }

    public JimixImageFileTypeProviderPlugin[] getAllImageFileTypeProviders() {
        return imageFileTypeProviderMap.values().toArray(new JimixImageFileTypeProviderPlugin[imageFileTypeProviderMap.size()]);
    }

    public JimixImageFileTypeProviderPlugin getImageFileTypeProvider(final String fileTypeProviderClassName) {
        return imageFileTypeProviderMap.get(fileTypeProviderClassName);
    }

    public JimixProjectFileTypeProviderPlugin[] getAllProjectFileTypeProviders() {
        return projectFileTypeProviderMap.values().toArray(new JimixProjectFileTypeProviderPlugin[projectFileTypeProviderMap.size()]);
    }

    public JimixProjectFileTypeProviderPlugin getProjectFileTypeProvider(final String fileTypeProviderClassName) {
        return projectFileTypeProviderMap.get(fileTypeProviderClassName);
    }

    public JimixClipboardProviderPlugin[] getAllClipboardProviders() {
        return clipboardProviderMap.values().toArray(new JimixClipboardProviderPlugin[clipboardProviderMap.size()]);
    }

    public JimixClipboardProviderPlugin getClipboardProvider(final String clipboardProviderClassName) {
        return clipboardProviderMap.get(clipboardProviderClassName);
    }

    @Override
    public ClassLoader getClassLoader() {
        return classLoader;
    }
}

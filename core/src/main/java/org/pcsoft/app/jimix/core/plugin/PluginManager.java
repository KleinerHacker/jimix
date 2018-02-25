package org.pcsoft.app.jimix.core.plugin;

import org.apache.commons.lang.StringUtils;
import org.pcsoft.app.jimix.commons.exception.JimixPluginException;
import org.pcsoft.app.jimix.core.plugin.type.*;
import org.pcsoft.app.jimix.plugins.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.stream.Collectors;

public final class PluginManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(PluginManager.class);
    private static final PluginManager instance = new PluginManager();

    public static PluginManager getInstance() {
        return instance;
    }

    private final Map<String, JimixEffectPlugin> effectMap = new HashMap<>();
    private final Map<String, JimixFilterPlugin> filterMap = new HashMap<>();
    private final Map<String, JimixRendererPlugin> rendererMap = new HashMap<>();
    private final Map<String, JimixBlenderPlugin> blenderMap = new HashMap<>();
    private final Map<String, JimixScalerPlugin> scalerMap = new HashMap<>();
    private final Map<String, JimixClipboardProviderPlugin> clipboardProviderMap = new HashMap<>();
    private final Map<String, JimixFileTypeProviderPlugin> fileTypeProviderMap = new HashMap<>();

    private PluginManager() {
    }

    public void init(final List<File> pluginPathList) throws IOException {
        LOGGER.debug("Initialize plugin manager");
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("> Load plugins from paths: [" + StringUtils.join(pluginPathList.stream().map(File::getAbsolutePath).collect(Collectors.toList()), ',') + "]");
        }

        final List<URL> pluginUrlList = new ArrayList<>();
        for (final File pluginPath : pluginPathList) {
            pluginUrlList.add(pluginPath.toURI().toURL());
        }

        final ClassLoader classLoader = new URLClassLoader(pluginUrlList.toArray(new URL[pluginUrlList.size()]),
                PluginManager.class.getClassLoader());
        
        loadEffects(classLoader);
        loadFilter(classLoader);
        loadRenderer(classLoader);
        loadBlender(classLoader);
        loadScaler(classLoader);
        loadClipboardProvider(classLoader);
        loadFileTypeProvider(classLoader);

        LOGGER.debug("Found {} effects, {} filters, {} renderer, {} blender, {} scaler, {} clipboard providers, {} file type providers",
                effectMap.size(), filterMap.size(), rendererMap.size(), blenderMap.size(), scalerMap.size(), clipboardProviderMap.size(),
                fileTypeProviderMap.size());
    }

    private void loadEffects(final ClassLoader classLoader) {
        LOGGER.debug("> Load effect plugins");

        final ServiceLoader<JimixEffect> jimixEffects = ServiceLoader.load(JimixEffect.class, classLoader);
        for (final JimixEffect jimixEffect : jimixEffects) {
            try {
                final JimixEffectPlugin jimixEffectInstance = new JimixEffectPlugin(jimixEffect);
                effectMap.put(jimixEffectInstance.getIdentifier(), jimixEffectInstance);
                LOGGER.trace(">>> {}", jimixEffectInstance.getIdentifier());
            } catch (JimixPluginException e) {
                LOGGER.error("Unable to load effect " + jimixEffect.getClass().getName(), e);
            }
        }
    }

    private void loadFilter(final ClassLoader classLoader) {
        LOGGER.debug("> Load filter plugins");

        final ServiceLoader<JimixFilter> jimixFilters = ServiceLoader.load(JimixFilter.class, classLoader);
        for (final JimixFilter jimixFilter : jimixFilters) {
            try {
                final JimixFilterPlugin jimixFilterInstance = new JimixFilterPlugin(jimixFilter);
                filterMap.put(jimixFilterInstance.getIdentifier(), jimixFilterInstance);
                LOGGER.trace(">>> {}", jimixFilterInstance.getIdentifier());
            } catch (JimixPluginException e) {
                LOGGER.error("Unable to load filter " + jimixFilter.getClass().getName(), e);
            }
        }
    }

    private void loadRenderer(final ClassLoader classLoader) {
        LOGGER.debug("> Load renderer plugins");

        final ServiceLoader<JimixRenderer> jimixRenderers = ServiceLoader.load(JimixRenderer.class, classLoader);
        for (final JimixRenderer jimixRenderer : jimixRenderers) {
            try {
                final JimixRendererPlugin jimixRendererInstance = new JimixRendererPlugin(jimixRenderer);
                rendererMap.put(jimixRendererInstance.getIdentifier(), jimixRendererInstance);
                LOGGER.trace(">>> {}", jimixRendererInstance.getIdentifier());
            } catch (JimixPluginException e) {
                LOGGER.error("Unable to load renderer " + jimixRenderer.getClass().getName(), e);
            }
        }
    }

    private void loadBlender(final ClassLoader classLoader) {
        LOGGER.debug("> Load blender plugins");

        final ServiceLoader<JimixBlender> jimixBlenders = ServiceLoader.load(JimixBlender.class, classLoader);
        for (final JimixBlender jimixBlender : jimixBlenders) {
            try {
                final JimixBlenderPlugin jimixBlenderInstance = new JimixBlenderPlugin(jimixBlender);
                blenderMap.put(jimixBlenderInstance.getIdentifier(), jimixBlenderInstance);
                LOGGER.trace(">>> {}", jimixBlenderInstance.getIdentifier());
            } catch (JimixPluginException e) {
                LOGGER.error("Unable to load blender " + jimixBlender.getClass().getName(), e);
            }
        }
    }

    private void loadScaler(final ClassLoader classLoader) {
        LOGGER.debug("> Load scaler plugins");

        final ServiceLoader<JimixScaler> jimixScalers = ServiceLoader.load(JimixScaler.class, classLoader);
        for (final JimixScaler jimixScaler : jimixScalers) {
            try {
                final JimixScalerPlugin jimixScalerInstance = new JimixScalerPlugin(jimixScaler);
                scalerMap.put(jimixScalerInstance.getIdentifier(), jimixScalerInstance);
                LOGGER.trace(">>> {}", jimixScalerInstance.getIdentifier());
            } catch (JimixPluginException e) {
                LOGGER.error("Unable to load scaler " + jimixScaler.getClass().getName(), e);
            }
        }
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

    private void loadFileTypeProvider(final ClassLoader classLoader) {
        LOGGER.debug("> Load file type provider plugins");

        final ServiceLoader<JimixFileTypeProvider> jimixFileTypeProviders = ServiceLoader.load(JimixFileTypeProvider.class, classLoader);
        for (final JimixFileTypeProvider jimixFileTypeProvider : jimixFileTypeProviders) {
            try {
                final JimixFileTypeProviderPlugin jimixFileTypeProviderInstance = new JimixFileTypeProviderPlugin(jimixFileTypeProvider);
                fileTypeProviderMap.put(jimixFileTypeProviderInstance.getIdentifier(), jimixFileTypeProviderInstance);
                LOGGER.trace(">>> {}", jimixFileTypeProviderInstance.getIdentifier());
            } catch (JimixPluginException e) {
                LOGGER.error("Unable to load file type provider " + jimixFileTypeProvider.getClass().getName(), e);
            }
        }
    }

    public JimixEffectPlugin[] getAllEffects() {
        return effectMap.values().toArray(new JimixEffectPlugin[effectMap.size()]);
    }

    public JimixEffectPlugin getEffect(final String effectClassName) {
        return effectMap.get(effectClassName);
    }

    public JimixFilterPlugin[] getAllFilters() {
        return filterMap.values().toArray(new JimixFilterPlugin[filterMap.size()]);
    }

    public JimixFilterPlugin getFilter(final String filterClassName) {
        return filterMap.get(filterClassName);
    }

    public JimixRendererPlugin[] getAllRenderers() {
        return rendererMap.values().toArray(new JimixRendererPlugin[rendererMap.size()]);
    }

    public JimixRendererPlugin getRenderer(final String rendererClassName) {
        return rendererMap.get(rendererClassName);
    }

    public JimixBlenderPlugin[] getAllBlenders() {
        return blenderMap.values().toArray(new JimixBlenderPlugin[blenderMap.size()]);
    }

    public JimixBlenderPlugin getBlender(final String blenderClassName) {
        return blenderMap.get(blenderClassName);
    }

    public JimixScalerPlugin[] getAllScalers() {
        return scalerMap.values().toArray(new JimixScalerPlugin[scalerMap.size()]);
    }

    public JimixScalerPlugin getScaler(final String scalerClassName) {
        return scalerMap.get(scalerClassName);
    }

    public JimixClipboardProviderPlugin[] getAllClipboardProviders() {
        return clipboardProviderMap.values().toArray(new JimixClipboardProviderPlugin[clipboardProviderMap.size()]);
    }

    public JimixClipboardProviderPlugin getClipboardProvider(final String clipboardProviderClassName) {
        return clipboardProviderMap.get(clipboardProviderClassName);
    }

    public JimixFileTypeProviderPlugin[] getAllFileTypeProviders() {
        return fileTypeProviderMap.values().toArray(new JimixFileTypeProviderPlugin[fileTypeProviderMap.size()]);
    }

    public JimixFileTypeProviderPlugin getFileTypeProvider(final String fileTypeProviderClassName) {
        return fileTypeProviderMap.get(fileTypeProviderClassName);
    }
}

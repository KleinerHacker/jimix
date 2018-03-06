package org.pcsoft.app.jimix.plugin.mani.manager;

import org.apache.commons.lang.StringUtils;
import org.pcsoft.app.jimix.commons.exception.JimixPluginException;
import org.pcsoft.app.jimix.plugin.common.manager.PluginManager;
import org.pcsoft.app.jimix.plugin.mani.api.*;
import org.pcsoft.app.jimix.plugin.mani.api.type.JimixPluginElement;
import org.pcsoft.app.jimix.plugin.mani.manager.type.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.stream.Collectors;

public final class ManipulationPluginManager implements PluginManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(ManipulationPluginManager.class);
    private static final ManipulationPluginManager instance = new ManipulationPluginManager();

    public static ManipulationPluginManager getInstance() {
        return instance;
    }

    private final Map<String, JimixEffectPlugin> effectMap = new HashMap<>();
    private final Map<String, JimixFilterPlugin> filterMap = new HashMap<>();
    private final Map<String, JimixRendererPlugin> rendererMap = new HashMap<>();
    private final Map<String, JimixBlenderPlugin> blenderMap = new HashMap<>();
    private final Map<String, JimixScalerPlugin> scalerMap = new HashMap<>();
    private final Map<String, JimixClipboardProviderPlugin> clipboardProviderMap = new HashMap<>();
    private final Map<Class<? extends JimixPluginElement>, JimixElementDrawerPlugin> elementDrawerMap = new HashMap<>();

    private ClassLoader classLoader;

    private ManipulationPluginManager() {
    }

    @Override
    public void init(final List<File> pluginPathList) throws IOException {
        LOGGER.debug("Initialize manipulation plugin manager");
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("> Load plugins from paths: [" + StringUtils.join(pluginPathList.stream().map(File::getAbsolutePath).collect(Collectors.toList()), ',') + "]");
        }

        final List<URL> pluginUrlList = new ArrayList<>();
        for (final File pluginPath : pluginPathList) {
            pluginUrlList.add(pluginPath.toURI().toURL());
        }

        classLoader = new URLClassLoader(pluginUrlList.toArray(new URL[pluginUrlList.size()]),
                ManipulationPluginManager.class.getClassLoader());
        
        loadEffects(classLoader);
        loadFilter(classLoader);
        loadRenderer(classLoader);
        loadBlender(classLoader);
        loadScaler(classLoader);
        loadClipboardProvider(classLoader);
        loadElementDrawerProvider(classLoader);

        LOGGER.debug("Found {} effects, {} filters, {} renderer, {} blender, {} scaler, {} clipboard providers, {} element drawers",
                effectMap.size(), filterMap.size(), rendererMap.size(), blenderMap.size(), scalerMap.size(), clipboardProviderMap.size(),
                elementDrawerMap.size());
    }

    private void loadEffects(final ClassLoader classLoader) {
        LOGGER.debug("> Load effect plugins");

        final ServiceLoader<JimixEffect> jimixEffects = ServiceLoader.load(JimixEffect.class, classLoader);
        for (final JimixEffect jimixEffect : jimixEffects) {
            try {
                final JimixEffectPlugin jimixEffectPlugin = new JimixEffectPlugin(jimixEffect);
                effectMap.put(jimixEffectPlugin.getIdentifier(), jimixEffectPlugin);
                LOGGER.trace(">>> {}", jimixEffectPlugin.getIdentifier());
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
                final JimixFilterPlugin jimixFilterPlugin = new JimixFilterPlugin(jimixFilter);
                filterMap.put(jimixFilterPlugin.getIdentifier(), jimixFilterPlugin);
                LOGGER.trace(">>> {}", jimixFilterPlugin.getIdentifier());
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
                final JimixRendererPlugin jimixRendererPlugin = new JimixRendererPlugin(jimixRenderer);
                rendererMap.put(jimixRendererPlugin.getIdentifier(), jimixRendererPlugin);
                LOGGER.trace(">>> {}", jimixRendererPlugin.getIdentifier());
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
                final JimixBlenderPlugin jimixBlenderPlugin = new JimixBlenderPlugin(jimixBlender);
                blenderMap.put(jimixBlenderPlugin.getIdentifier(), jimixBlenderPlugin);
                LOGGER.trace(">>> {}", jimixBlenderPlugin.getIdentifier());
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
                final JimixScalerPlugin jimixScalerPlugin = new JimixScalerPlugin(jimixScaler);
                scalerMap.put(jimixScalerPlugin.getIdentifier(), jimixScalerPlugin);
                LOGGER.trace(">>> {}", jimixScalerPlugin.getIdentifier());
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

    private void loadElementDrawerProvider(final ClassLoader classLoader) {
        LOGGER.debug("> Load element drawer plugins");

        final ServiceLoader<JimixElementDrawer> jimixElementDrawers = ServiceLoader.load(JimixElementDrawer.class, classLoader);
        for (final JimixElementDrawer jimixElementDrawer : jimixElementDrawers) {
            try {
                final JimixElementDrawerPlugin jimixElementDrawerPlugin = new JimixElementDrawerPlugin(jimixElementDrawer);
                elementDrawerMap.put(jimixElementDrawerPlugin.getElementModelClass(), jimixElementDrawerPlugin);
                LOGGER.trace(">>> {}", jimixElementDrawerPlugin.getIdentifier());
            } catch (JimixPluginException e) {
                LOGGER.error("Unable to load element drawer " + jimixElementDrawer.getClass().getName(), e);
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

    public JimixElementDrawerPlugin[] getAllElementDrawers() {
        return elementDrawerMap.values().toArray(new JimixElementDrawerPlugin[elementDrawerMap.size()]);
    }

    public JimixElementDrawerPlugin getElementDrawer(final Class<? extends JimixPluginElement> elementModelClass) {
        return elementDrawerMap.get(elementModelClass);
    }

    @Override
    public ClassLoader getClassLoader() {
        return classLoader;
    }
}

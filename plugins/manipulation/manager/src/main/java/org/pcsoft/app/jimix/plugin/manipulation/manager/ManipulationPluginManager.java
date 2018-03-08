package org.pcsoft.app.jimix.plugin.manipulation.manager;

import org.apache.commons.lang.StringUtils;
import org.pcsoft.app.jimix.commons.exception.JimixPluginException;
import org.pcsoft.app.jimix.commons.util.ComparatorUtils;
import org.pcsoft.app.jimix.plugin.common.api.type.JimixPlugin2DElement;
import org.pcsoft.app.jimix.plugin.common.api.type.JimixPlugin3DElement;
import org.pcsoft.app.jimix.plugin.common.api.type.JimixPluginElement;
import org.pcsoft.app.jimix.plugin.common.manager.PluginManager;
import org.pcsoft.app.jimix.plugin.manipulation.api.*;
import org.pcsoft.app.jimix.plugin.manipulation.manager.type.*;
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

    private final Map<String, Jimix2DEffectPlugin> effect2DMap = new HashMap<>();
    private final Map<String, Jimix3DEffectPlugin> effect3DMap = new HashMap<>();
    private final Map<String, JimixFilterPlugin> filterMap = new HashMap<>();
    private final Map<String, JimixRendererPlugin> rendererMap = new HashMap<>();
    private final Map<String, JimixBlenderPlugin> blenderMap = new HashMap<>();
    private final Map<String, JimixScalerPlugin> scalerMap = new HashMap<>();
    private final Map<Class<? extends JimixPlugin2DElement>, Jimix2DElementBuilderPlugin> element2DBuilderMap = new HashMap<>();
    private final Map<Class<? extends JimixPlugin3DElement>, Jimix3DElementBuilderPlugin> element3DBuilderMap = new HashMap<>();

    private ClassLoader classLoader;

    private ManipulationPluginManager() {
    }

    @Override
    public void init(final List<File> pluginPathList) throws IOException {
        LOGGER.debug("Initialize manipulation plugin manager");

        //Cleanup
        effect2DMap.clear();
        effect3DMap.clear();
        filterMap.clear();
        rendererMap.clear();
        blenderMap.clear();
        scalerMap.clear();
        element2DBuilderMap.clear();
        element3DBuilderMap.clear();

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("> Load plugins from paths: [" + StringUtils.join(pluginPathList.stream().map(File::getAbsolutePath).collect(Collectors.toList()), ',') + "]");
        }

        final List<URL> pluginUrlList = new ArrayList<>();
        for (final File pluginPath : pluginPathList) {
            pluginUrlList.add(pluginPath.toURI().toURL());
        }

        classLoader = new URLClassLoader(pluginUrlList.toArray(new URL[pluginUrlList.size()]),
                ManipulationPluginManager.class.getClassLoader());

        load2DEffects(classLoader);
        load3DEffects(classLoader);
        loadFilter(classLoader);
        loadRenderer(classLoader);
        loadBlender(classLoader);
        loadScaler(classLoader);
        load2DElementBuilderProvider(classLoader);
        load3DElementBuilderProvider(classLoader);

        LOGGER.debug("Found {} 2D effects, {} 3D effect, {} filters, {} renderer, {} blender, {} scaler, {} 2D element builders, {} 3D element builders",
                effect2DMap.size(), effect3DMap.size(), filterMap.size(), rendererMap.size(), blenderMap.size(), scalerMap.size(),
                element2DBuilderMap.size(), element3DBuilderMap.size());
    }

    private void load2DEffects(final ClassLoader classLoader) {
        LOGGER.debug("> Load 2D effect plugins");

        final ServiceLoader<Jimix2DEffect> jimix2DEffects = ServiceLoader.load(Jimix2DEffect.class, classLoader);
        for (final Jimix2DEffect jimix2DEffect : jimix2DEffects) {
            try {
                final Jimix2DEffectPlugin jimix2DEffectPlugin = new Jimix2DEffectPlugin(jimix2DEffect);
                effect2DMap.put(jimix2DEffectPlugin.getIdentifier(), jimix2DEffectPlugin);
                LOGGER.trace(">>> {}", jimix2DEffectPlugin.getIdentifier());
            } catch (JimixPluginException e) {
                LOGGER.error("Unable to load 2D effect " + jimix2DEffect.getClass().getName(), e);
            }
        }
    }

    private void load3DEffects(final ClassLoader classLoader) {
        LOGGER.debug("> Load 3D effect plugins");

        final ServiceLoader<Jimix3DEffect> jimix3DEffects = ServiceLoader.load(Jimix3DEffect.class, classLoader);
        for (final Jimix3DEffect jimix3DEffect : jimix3DEffects) {
            try {
                final Jimix3DEffectPlugin jimix3DEffectPlugin = new Jimix3DEffectPlugin(jimix3DEffect);
                effect3DMap.put(jimix3DEffectPlugin.getIdentifier(), jimix3DEffectPlugin);
                LOGGER.trace(">>> {}", jimix3DEffectPlugin.getIdentifier());
            } catch (JimixPluginException e) {
                LOGGER.error("Unable to load 3D effect " + jimix3DEffect.getClass().getName(), e);
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

    private void load2DElementBuilderProvider(final ClassLoader classLoader) {
        LOGGER.debug("> Load 2D element builder plugins");

        final ServiceLoader<Jimix2DElementBuilder> jimix2DElementBuilders = ServiceLoader.load(Jimix2DElementBuilder.class, classLoader);
        for (final Jimix2DElementBuilder jimix2DElementBuilder : jimix2DElementBuilders) {
            try {
                final Jimix2DElementBuilderPlugin jimix2DElementBuilderPlugin = new Jimix2DElementBuilderPlugin(jimix2DElementBuilder);
                element2DBuilderMap.put(jimix2DElementBuilderPlugin.getElementModelClass(), jimix2DElementBuilderPlugin);
                LOGGER.trace(">>> {}", jimix2DElementBuilderPlugin.getIdentifier());
            } catch (JimixPluginException e) {
                LOGGER.error("Unable to load 2D element builder " + jimix2DElementBuilder.getClass().getName(), e);
            }
        }
    }

    private void load3DElementBuilderProvider(final ClassLoader classLoader) {
        LOGGER.debug("> Load 3D element builder plugins");

        final ServiceLoader<Jimix3DElementBuilder> jimix3DElementBuilders = ServiceLoader.load(Jimix3DElementBuilder.class, classLoader);
        for (final Jimix3DElementBuilder jimix3DElementBuilder : jimix3DElementBuilders) {
            try {
                final Jimix3DElementBuilderPlugin jimix3DElementBuilderPlugin = new Jimix3DElementBuilderPlugin(jimix3DElementBuilder);
                element3DBuilderMap.put(jimix3DElementBuilderPlugin.getElementModelClass(), jimix3DElementBuilderPlugin);
                LOGGER.trace(">>> {}", jimix3DElementBuilderPlugin.getIdentifier());
            } catch (JimixPluginException e) {
                LOGGER.error("Unable to load 3D element builder " + jimix3DElementBuilder.getClass().getName(), e);
            }
        }
    }

    public Jimix2DEffectPlugin[] getAll2DEffects() {
        return effect2DMap.values().stream()
                .sorted(ComparatorUtils.chainedComparator(
                        ComparatorUtils.compareWithNull(Jimix2DEffectPlugin::getGroup, ComparatorUtils.ComparatorDirection.GoToBottom),
                        Comparator.comparing(Jimix2DEffectPlugin::getName)
                ))
                .toArray(Jimix2DEffectPlugin[]::new);
    }

    public Jimix2DEffectPlugin get2DEffect(final String effectClassName) {
        return effect2DMap.get(effectClassName);
    }

    public Jimix3DEffectPlugin[] getAll3DEffects() {
        return effect3DMap.values().stream()
                .sorted(ComparatorUtils.chainedComparator(
                        ComparatorUtils.compareWithNull(Jimix3DEffectPlugin::getGroup, ComparatorUtils.ComparatorDirection.GoToBottom),
                        Comparator.comparing(Jimix3DEffectPlugin::getName)
                ))
                .toArray(Jimix3DEffectPlugin[]::new);
    }

    public Jimix3DEffectPlugin get3DEffect(final String effectClassName) {
        return effect3DMap.get(effectClassName);
    }

    public JimixEffectPlugin[] getAllEffects() {
        final List<JimixEffectPlugin> list = new ArrayList<>();
        list.addAll(effect2DMap.values());
        list.addAll(effect3DMap.values());

        return list.stream()
                .sorted(ComparatorUtils.chainedComparator(
                        ComparatorUtils.compareWithNull(JimixEffectPlugin::getGroup, ComparatorUtils.ComparatorDirection.GoToBottom),
                        Comparator.comparing(JimixEffectPlugin::getName)
                ))
                .toArray(JimixEffectPlugin[]::new);
    }

    public JimixEffectPlugin getEffect(final String effectClassName) {
        if (effect2DMap.containsKey(effectClassName))
            return effect2DMap.get(effectClassName);

        return effect3DMap.get(effectClassName);
    }

    public JimixFilterPlugin[] getAllFilters() {
        return filterMap.values().stream()
                .sorted(ComparatorUtils.chainedComparator(
                        ComparatorUtils.compareWithNull(JimixFilterPlugin::getGroup, ComparatorUtils.ComparatorDirection.GoToBottom),
                        Comparator.comparing(JimixFilterPlugin::getName)
                ))
                .toArray(JimixFilterPlugin[]::new);
    }

    public JimixFilterPlugin getFilter(final String filterClassName) {
        return filterMap.get(filterClassName);
    }

    public JimixRendererPlugin[] getAllRenderers() {
        return rendererMap.values().stream()
                .sorted(ComparatorUtils.chainedComparator(
                        ComparatorUtils.compareWithNull(JimixRendererPlugin::getGroup, ComparatorUtils.ComparatorDirection.GoToBottom),
                        Comparator.comparing(JimixRendererPlugin::getName)
                ))
                .toArray(JimixRendererPlugin[]::new);
    }

    public JimixRendererPlugin getRenderer(final String rendererClassName) {
        return rendererMap.get(rendererClassName);
    }

    public JimixBlenderPlugin[] getAllBlenders() {
        return blenderMap.values().stream()
                .sorted(Comparator.comparing(JimixBlenderPlugin::getName))
                .toArray(JimixBlenderPlugin[]::new);
    }

    public JimixBlenderPlugin getBlender(final String blenderClassName) {
        return blenderMap.get(blenderClassName);
    }

    public JimixScalerPlugin[] getAllScalers() {
        return scalerMap.values().stream()
                .sorted(Comparator.comparing(JimixScalerPlugin::getName))
                .toArray(JimixScalerPlugin[]::new);
    }

    public JimixScalerPlugin getScaler(final String scalerClassName) {
        return scalerMap.get(scalerClassName);
    }

    public Jimix2DElementBuilderPlugin[] getAll2DElementBuilders() {
        return element2DBuilderMap.values().toArray(new Jimix2DElementBuilderPlugin[element2DBuilderMap.size()]);
    }

    public Jimix2DElementBuilderPlugin get2DElementBuilder(final Class<? extends JimixPlugin2DElement> elementModelClass) {
        return element2DBuilderMap.get(elementModelClass);
    }

    public Jimix3DElementBuilderPlugin[] getAll3DElementBuilders() {
        return element3DBuilderMap.values().toArray(new Jimix3DElementBuilderPlugin[element3DBuilderMap.size()]);
    }

    public Jimix3DElementBuilderPlugin get3DElementBuilder(final Class<? extends JimixPlugin3DElement> elementModelClass) {
        return element3DBuilderMap.get(elementModelClass);
    }

    public JimixElementBuilderPlugin[] getAllElementBuilders() {
        final List<JimixElementBuilderPlugin> list = new ArrayList<>();
        list.addAll(element2DBuilderMap.values());
        list.addAll(element3DBuilderMap.values());

        return list.toArray(new JimixElementBuilderPlugin[list.size()]);
    }

    @SuppressWarnings({"unchecked", "SuspiciousMethodCalls"})
    public JimixElementBuilderPlugin getElementBuilder(final Class<? extends JimixPluginElement> elementModelClass) {
        if (element2DBuilderMap.containsKey(elementModelClass))
            return element2DBuilderMap.get(elementModelClass);

        return element3DBuilderMap.get(elementModelClass);
    }

    @Override
    public ClassLoader getClassLoader() {
        return classLoader;
    }
}

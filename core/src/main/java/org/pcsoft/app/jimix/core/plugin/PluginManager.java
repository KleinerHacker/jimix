package org.pcsoft.app.jimix.core.plugin;

import org.apache.commons.lang.StringUtils;
import org.pcsoft.app.jimix.commons.exception.JimixPluginException;
import org.pcsoft.app.jimix.core.plugin.type.JimixBlenderInstance;
import org.pcsoft.app.jimix.core.plugin.type.JimixEffectInstance;
import org.pcsoft.app.jimix.core.plugin.type.JimixRendererInstance;
import org.pcsoft.app.jimix.plugins.api.JimixBlender;
import org.pcsoft.app.jimix.plugins.api.JimixEffect;
import org.pcsoft.app.jimix.plugins.api.JimixRenderer;
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

    private final Map<String, JimixEffectInstance> effectMap = new HashMap<>();
    private final Map<String, JimixRendererInstance> rendererMap = new HashMap<>();
    private final Map<String, JimixBlenderInstance> blenderMap = new HashMap<>();

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
        loadRenderer(classLoader);
        loadBlender(classLoader);

        LOGGER.debug("Found {} effects, {} renderer, {} blender", effectMap.size(), rendererMap.size(), blenderMap.size());
    }

    private void loadEffects(final ClassLoader classLoader) {
        LOGGER.debug("> Load effect plugins");

        final ServiceLoader<JimixEffect> jimixEffects = ServiceLoader.load(JimixEffect.class, classLoader);
        for (final JimixEffect jimixEffect : jimixEffects) {
            try {
                effectMap.put(jimixEffect.getClass().getName(), new JimixEffectInstance(jimixEffect));
                LOGGER.trace(">>> {}", jimixEffect.getClass().getName());
            } catch (JimixPluginException e) {
                LOGGER.error("Unable to load effect " + jimixEffect.getClass().getName(), e);
            }
        }
    }

    private void loadRenderer(final ClassLoader classLoader) {
        LOGGER.debug("> Load renderer plugins");

        final ServiceLoader<JimixRenderer> jimixRenderers = ServiceLoader.load(JimixRenderer.class, classLoader);
        for (final JimixRenderer jimixRenderer : jimixRenderers) {
            try {
                rendererMap.put(jimixRenderer.getClass().getName(), new JimixRendererInstance(jimixRenderer));
                LOGGER.trace(">>> {}", jimixRenderer.getClass().getName());
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
                blenderMap.put(jimixBlender.getClass().getName(), new JimixBlenderInstance(jimixBlender));
                LOGGER.trace(">>> {}", jimixBlender.getClass().getName());
            } catch (JimixPluginException e) {
                LOGGER.error("Unable to load blender " + jimixBlender.getClass().getName(), e);
            }
        }
    }

    public JimixEffectInstance[] getAllEffects() {
        return effectMap.values().toArray(new JimixEffectInstance[effectMap.size()]);
    }

    public JimixEffectInstance getEffect(final String effectClassName) {
        return effectMap.get(effectClassName);
    }

    public JimixRendererInstance[] getAllRenderers() {
        return rendererMap.values().toArray(new JimixRendererInstance[rendererMap.size()]);
    }

    public JimixRendererInstance getRenderer(final String rendererClassName) {
        return rendererMap.get(rendererClassName);
    }

    public JimixBlenderInstance[] getAllBlenders() {
        return blenderMap.values().toArray(new JimixBlenderInstance[blenderMap.size()]);
    }

    public JimixBlenderInstance getBlender(final String blenderClassName) {
        return blenderMap.get(blenderClassName);
    }
}

package org.pcsoft.app.jimix.core.plugin;

import org.apache.commons.lang.StringUtils;
import org.pcsoft.app.jimix.commons.exception.JimixPluginException;
import org.pcsoft.app.jimix.core.plugin.type.JimixEffectHolder;
import org.pcsoft.app.jimix.core.plugin.type.JimixRendererHolder;
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

    private final Map<String, JimixEffectHolder> effectMap = new HashMap<>();
    private final Map<String, JimixRendererHolder> rendererMap = new HashMap<>();

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

        LOGGER.debug("Found {} effects, {} renderer", effectMap.size(), rendererMap.size());
    }

    private void loadEffects(final ClassLoader classLoader) {
        LOGGER.debug("> Load effect plugins");

        final ServiceLoader<JimixEffect> jimixEffects = ServiceLoader.load(JimixEffect.class, classLoader);
        for (final JimixEffect jimixEffect : jimixEffects) {
            try {
                effectMap.put(jimixEffect.getClass().getName(), new JimixEffectHolder(jimixEffect));
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
                rendererMap.put(jimixRenderer.getClass().getName(), new JimixRendererHolder(jimixRenderer));
                LOGGER.trace(">>> {}", jimixRenderer.getClass().getName());
            } catch (JimixPluginException e) {
                LOGGER.error("Unable to load renderer " + jimixRenderer.getClass().getName(), e);
            }
        }
    }

    public JimixEffectHolder[] getAllEffects() {
        return effectMap.values().toArray(new JimixEffectHolder[effectMap.size()]);
    }

    public JimixEffectHolder getEffect(final String effectClassName) {
        return effectMap.get(effectClassName);
    }

    public JimixRendererHolder[] getAllRenderers() {
        return rendererMap.values().toArray(new JimixRendererHolder[rendererMap.size()]);
    }

    public JimixRendererHolder getRenderer(final String rendererClassName) {
        return rendererMap.get(rendererClassName);
    }
}

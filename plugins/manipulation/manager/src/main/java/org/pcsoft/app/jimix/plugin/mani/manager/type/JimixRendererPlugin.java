package org.pcsoft.app.jimix.plugin.mani.manager.type;

import javafx.scene.image.Image;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DurationFormatUtils;
import org.apache.commons.lang.time.StopWatch;
import org.pcsoft.app.jimix.commons.exception.JimixPluginAnnotationException;
import org.pcsoft.app.jimix.commons.exception.JimixPluginException;
import org.pcsoft.app.jimix.commons.exception.JimixPluginExecutionException;
import org.pcsoft.app.jimix.plugin.common.manager.type.JimixPlugin;
import org.pcsoft.app.jimix.plugin.mani.api.JimixRenderer;
import org.pcsoft.app.jimix.plugin.mani.api.annotation.JimixRendererDescriptor;
import org.pcsoft.app.jimix.plugin.mani.api.config.JimixRendererConfiguration;
import org.pcsoft.app.jimix.plugin.mani.api.type.JimixSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public final class JimixRendererPlugin implements JimixPlugin<JimixRendererInstance> {
    private static final Logger LOGGER = LoggerFactory.getLogger(JimixRendererPlugin.class);
    private static final StopWatch STOP_WATCH = new StopWatch();

    private final JimixRenderer instance;
    private final JimixRendererDescriptor descriptor;
    private final ResourceBundle resourceBundle;
    private final Image icon;

    public JimixRendererPlugin(JimixRenderer instance) throws JimixPluginException {
        if (!instance.getClass().isAnnotationPresent(JimixRendererDescriptor.class))
            throw new JimixPluginAnnotationException("Unable to find needed annotation " + JimixRendererDescriptor.class.getName() + " on renderer class " + instance.getClass().getName());

        this.instance = instance;
        this.descriptor = instance.getClass().getAnnotation(JimixRendererDescriptor.class);
        if (!StringUtils.isEmpty(descriptor.resourceBundle())) {
            try {
                resourceBundle = ResourceBundle.getBundle(descriptor.resourceBundle(), Locale.getDefault(), instance.getClass().getClassLoader());
            } catch (MissingResourceException e) {
                throw new JimixPluginAnnotationException("Unable to find resource bundle " + descriptor.resourceBundle() + " for renderer " + instance.getClass().getName(), e);
            }
        } else {
            resourceBundle = null;
        }
        if (!StringUtils.isEmpty(descriptor.iconPath())) {
            try {
                icon = new Image(instance.getClass().getResourceAsStream(descriptor.iconPath()));
            } catch (Exception e) {
                throw new JimixPluginAnnotationException("Unable to find icon " + descriptor.iconPath() + " for filter " + instance.getClass().getName(), e);
            }
        } else {
            icon = null;
        }
    }

    Image apply(int width, int height, JimixRendererConfiguration configuration, JimixSource applySource) throws JimixPluginExecutionException {
        final Image resultImage;

        if (LOGGER.isTraceEnabled()) {
            STOP_WATCH.reset();
            STOP_WATCH.start();
        }

        try {
            resultImage = instance.apply(width, height, configuration, applySource);
        } catch (Exception e) {
            throw new JimixPluginExecutionException("Error while running renderer", e);
        }

        if (LOGGER.isTraceEnabled()) {
            STOP_WATCH.stop();
            LOGGER.trace("Renderer run time: " + DurationFormatUtils.formatDuration(STOP_WATCH.getTime(), "ss:SSS"));
        }

        return resultImage;
    }

    @Override
    public JimixRendererInstance createInstance() throws JimixPluginException {
        try {
            return new JimixRendererInstance(this, descriptor.configurationClass().newInstance());
        } catch (InstantiationException | IllegalAccessException e) {
            throw new JimixPluginException("Unable to create renderer instance", e);
        }
    }

    @Override
    public String getIdentifier() {
        return instance.getClass().getName();
    }

    @Override
    public String getName() {
        if (resourceBundle != null)
            return resourceBundle.getString(descriptor.name());

        return descriptor.name();
    }

    @Override
    public String getDescription() {
        if (resourceBundle != null)
            return resourceBundle.getString(descriptor.description());

        return descriptor.description();
    }

    public String getGroup() {
        if (StringUtils.isEmpty(descriptor.group()))
            return null;
        if (resourceBundle != null)
            return resourceBundle.getString(descriptor.group());

        return descriptor.group();
    }

    public Image getIcon() {
        return icon;
    }

    public Class<? extends JimixRendererConfiguration> getConfigurationClass() {
        return descriptor.configurationClass();
    }

    public boolean isUsableForPictures() {
        return descriptor.usableForPictures();
    }

    public boolean isUsableForMasks() {
        return descriptor.usableForMasks();
    }
}

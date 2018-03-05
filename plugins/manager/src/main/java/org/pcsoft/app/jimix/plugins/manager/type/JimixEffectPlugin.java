package org.pcsoft.app.jimix.plugins.manager.type;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DurationFormatUtils;
import org.apache.commons.lang.time.StopWatch;
import org.pcsoft.app.jimix.commons.exception.JimixPluginAnnotationException;
import org.pcsoft.app.jimix.commons.exception.JimixPluginException;
import org.pcsoft.app.jimix.commons.exception.JimixPluginExecutionException;
import org.pcsoft.app.jimix.plugins.api.JimixEffect;
import org.pcsoft.app.jimix.plugins.api.annotation.JimixEffectDescriptor;
import org.pcsoft.app.jimix.plugins.api.config.JimixEffectConfiguration;
import org.pcsoft.app.jimix.plugins.api.type.JimixEffectVariant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public final class JimixEffectPlugin implements JimixPlugin<JimixEffectInstance> {
    private static final Logger LOGGER = LoggerFactory.getLogger(JimixEffectPlugin.class);
    private static final StopWatch STOP_WATCH = new StopWatch();

    private final JimixEffect instance;
    private final JimixEffectDescriptor descriptor;
    private final ResourceBundle resourceBundle;
    private final Image icon;

    public JimixEffectPlugin(JimixEffect instance) throws JimixPluginException {
        if (!instance.getClass().isAnnotationPresent(JimixEffectDescriptor.class))
            throw new JimixPluginAnnotationException("Unable to find needed annotation " + JimixEffectDescriptor.class.getName() + " on effect class " + instance.getClass().getName());

        this.instance = instance;
        this.descriptor = instance.getClass().getAnnotation(JimixEffectDescriptor.class);
        if (!StringUtils.isEmpty(descriptor.resourceBundle())) {
            try {
                resourceBundle = ResourceBundle.getBundle(descriptor.resourceBundle(), Locale.getDefault(), instance.getClass().getClassLoader());
            } catch (MissingResourceException e) {
                throw new JimixPluginAnnotationException("Unable to find resource bundle " + descriptor.resourceBundle() + " for effect " + instance.getClass().getName(), e);
            }
        } else {
            resourceBundle = null;
        }
        if (!StringUtils.isEmpty(descriptor.iconPath())) {
            try {
                icon = new Image(instance.getClass().getResourceAsStream(descriptor.iconPath()));
            } catch (Exception e) {
                throw new JimixPluginAnnotationException("Unable to find icon " + descriptor.iconPath() + " for effect " + instance.getClass().getName(), e);
            }
        } else {
            icon = null;
        }
    }

    void apply(final Image image, final int x, final int y, final GraphicsContext gc, JimixEffectConfiguration configuration) throws JimixPluginExecutionException {
        if (LOGGER.isTraceEnabled()) {
            STOP_WATCH.reset();
            STOP_WATCH.start();
        }

        try {
            instance.apply(image, x, y, gc, configuration);
        } catch (Exception e) {
            throw new JimixPluginExecutionException("Error while running effect", e);
        }

        if (LOGGER.isTraceEnabled()) {
            STOP_WATCH.stop();
            LOGGER.trace("Effect run time: " + DurationFormatUtils.formatDuration(STOP_WATCH.getTime(), "ss:SSS"));
        }
    }

    public JimixEffectVariant[] getVariants() {
        return instance.getVariants();
    }

    @Override
    public JimixEffectInstance createInstance() throws JimixPluginException {
        try {
            return new JimixEffectInstance(this, descriptor.configurationClass().newInstance());
        } catch (InstantiationException | IllegalAccessException e) {
            throw new JimixPluginException("Unable to create effect instance", e);
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
}

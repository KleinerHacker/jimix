package org.pcsoft.app.jimix.plugin.manipulation.manager.type;

import javafx.scene.Node;
import javafx.scene.image.Image;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DurationFormatUtils;
import org.apache.commons.lang.time.StopWatch;
import org.pcsoft.app.jimix.commons.exception.JimixPluginAnnotationException;
import org.pcsoft.app.jimix.commons.exception.JimixPluginException;
import org.pcsoft.app.jimix.commons.exception.JimixPluginExecutionException;
import org.pcsoft.app.jimix.plugin.common.manager.type.JimixPlugin;
import org.pcsoft.app.jimix.plugin.manipulation.api.Jimix2DEffect;
import org.pcsoft.app.jimix.plugin.manipulation.api.annotation.JimixEffectDescriptor;
import org.pcsoft.app.jimix.plugin.manipulation.api.config.JimixEffectConfiguration;
import org.pcsoft.app.jimix.plugin.manipulation.api.type.JimixEffectVariant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Objects;
import java.util.ResourceBundle;

public final class Jimix2DEffectPlugin implements JimixPlugin<Jimix2DEffectInstance> {
    private static final Logger LOGGER = LoggerFactory.getLogger(Jimix2DEffectPlugin.class);
    private static final StopWatch STOP_WATCH = new StopWatch();

    private final Jimix2DEffect instance;
    private final JimixEffectDescriptor descriptor;
    private final ResourceBundle resourceBundle;
    private final Image icon;

    public Jimix2DEffectPlugin(Jimix2DEffect instance) throws JimixPluginException {
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

    Node apply(final Node node, final int x, final int y, final int width, final int height, JimixEffectConfiguration configuration) throws JimixPluginExecutionException {
        if (LOGGER.isTraceEnabled()) {
            STOP_WATCH.reset();
            STOP_WATCH.start();
        }

        final Node resultNode;
        try {
            resultNode = instance.apply(node, x, y, width, height, configuration);
        } catch (Exception e) {
            throw new JimixPluginExecutionException("Error while running effect", e);
        }

        if (LOGGER.isTraceEnabled()) {
            STOP_WATCH.stop();
            LOGGER.trace("Effect run time: " + DurationFormatUtils.formatDuration(STOP_WATCH.getTime(), "ss:SSS"));
        }

        return resultNode;
    }

    public JimixEffectVariant[] getVariants() {
        return instance.getVariants();
    }

    @Override
    public Jimix2DEffectInstance createInstance() throws JimixPluginException {
        try {
            return new Jimix2DEffectInstance(this, descriptor.configurationClass().newInstance());
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Jimix2DElementBuilderPlugin that = (Jimix2DElementBuilderPlugin) o;
        return Objects.equals(getIdentifier(), that.getIdentifier());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getIdentifier());
    }
}

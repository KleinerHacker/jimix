package org.pcsoft.app.jimix.core.plugin.type;

import javafx.scene.image.Image;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DurationFormatUtils;
import org.apache.commons.lang.time.StopWatch;
import org.pcsoft.app.jimix.commons.exception.JimixPluginAnnotationException;
import org.pcsoft.app.jimix.commons.exception.JimixPluginException;
import org.pcsoft.app.jimix.commons.exception.JimixPluginExecutionException;
import org.pcsoft.app.jimix.plugins.api.JimixFilter;
import org.pcsoft.app.jimix.plugins.api.annotation.JimixFilterDescriptor;
import org.pcsoft.app.jimix.plugins.api.config.JimixFilterConfiguration;
import org.pcsoft.app.jimix.plugins.api.type.JimixFilterType;
import org.pcsoft.app.jimix.plugins.api.type.JimixSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public final class JimixFilterPlugin implements JimixPlugin<JimixFilterInstance> {
    private static final Logger LOGGER = LoggerFactory.getLogger(JimixFilterPlugin.class);
    private static final StopWatch STOP_WATCH = new StopWatch();

    private final String uuid = UUID.randomUUID().toString();
    private final JimixFilter instance;
    private final JimixFilterDescriptor descriptor;
    private final ResourceBundle resourceBundle;
    private final Image icon;

    public JimixFilterPlugin(JimixFilter instance) throws JimixPluginException {
        if (!instance.getClass().isAnnotationPresent(JimixFilterDescriptor.class))
            throw new JimixPluginAnnotationException("Unable to find needed annotation " + JimixFilterDescriptor.class.getName() + " on filter class " + instance.getClass().getName());

        this.instance = instance;
        this.descriptor = instance.getClass().getAnnotation(JimixFilterDescriptor.class);
        if (!StringUtils.isEmpty(descriptor.resourceBundle())) {
            try {
                resourceBundle = ResourceBundle.getBundle(descriptor.resourceBundle(), Locale.getDefault(), instance.getClass().getClassLoader());
            } catch (MissingResourceException e) {
                throw new JimixPluginAnnotationException("Unable to find resource bundle " + descriptor.resourceBundle() + " for filter " + instance.getClass().getName(), e);
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

    Image apply(final Image image, JimixFilterConfiguration configuration, JimixSource source) throws JimixPluginExecutionException {
        final Image resultImage;

        if (LOGGER.isTraceEnabled()) {
            STOP_WATCH.reset();
            STOP_WATCH.start();
        }

        try {
            resultImage = instance.apply(image, configuration, source);
        } catch (Exception e) {
            throw new JimixPluginExecutionException("Error while running filter", e);
        }

        if (LOGGER.isTraceEnabled()) {
            STOP_WATCH.stop();
            LOGGER.trace("Filter run time: " + DurationFormatUtils.formatDuration(STOP_WATCH.getTime(), "ss:SSS"));
        }

        return resultImage;
    }

    @Override
    public JimixFilterInstance createInstance() throws JimixPluginException {
        try {
            return new JimixFilterInstance(this, descriptor.configurationClass().newInstance());
        } catch (InstantiationException | IllegalAccessException e) {
            throw new JimixPluginException("Unable to create filter instance", e);
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

    public Image getIcon() {
        return icon;
    }

    public JimixFilterType getType() {
        return descriptor.type();
    }

    public boolean isOnTopLevel() {
        return descriptor.onTopLevel();
    }

    public boolean isUsableForPictures() {
        return descriptor.usableForPictures();
    }

    public boolean isUsableForMasks() {
        return descriptor.usableForMasks();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JimixFilterPlugin that = (JimixFilterPlugin) o;
        return Objects.equals(uuid, that.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }

    @Override
    public String toString() {
        return "JimixFilterPlugin{" +
                "uuid='" + uuid + '\'' +
                ", descriptor=" + descriptor +
                '}';
    }
}

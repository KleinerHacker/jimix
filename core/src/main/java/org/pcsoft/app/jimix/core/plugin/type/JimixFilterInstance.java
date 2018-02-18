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

public final class JimixFilterInstance implements JimixInstance {
    private static final Logger LOGGER = LoggerFactory.getLogger(JimixFilterInstance.class);
    private static final StopWatch STOP_WATCH = new StopWatch();

    private final String uuid = UUID.randomUUID().toString();
    private final JimixFilter instance;
    private final JimixFilterDescriptor descriptor;
    private final ResourceBundle resourceBundle;

    public JimixFilterInstance(JimixFilter instance) throws JimixPluginException {
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
    }

    public Image apply(final Image image, JimixSource source) throws JimixPluginExecutionException {
        try {
            return apply(image, descriptor.configurationClass().newInstance(), source);
        } catch (InstantiationException | IllegalAccessException e) {
            throw new JimixPluginExecutionException("Unable to find empty filter configuration constructor", e);
        }
    }

    public Image apply(final Image image, JimixFilterConfiguration configuration, JimixSource source) throws JimixPluginExecutionException {
        final JimixPixelReaderImpl pixelReader = new JimixPixelReaderImpl(image.getPixelReader(), (int) image.getWidth(), (int) image.getHeight());
        final JimixPixelWriterImpl pixelWriter = new JimixPixelWriterImpl((int) image.getWidth(), (int) image.getHeight());

        if (LOGGER.isTraceEnabled()) {
            STOP_WATCH.reset();
            STOP_WATCH.start();
        }

        try {
            instance.apply(pixelReader, pixelWriter, configuration, source);
        } catch (Exception e) {
            throw new JimixPluginExecutionException("Error while running filter", e);
        }

        if (LOGGER.isTraceEnabled()) {
            STOP_WATCH.stop();
            LOGGER.trace("Filter run time: " + DurationFormatUtils.formatDuration(STOP_WATCH.getTime(), "ss:SSS"));
        }

        return pixelWriter.buildImage();
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

    public Class<? extends JimixFilterConfiguration> getConfigurationClass() {
        return descriptor.configurationClass();
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
        JimixFilterInstance that = (JimixFilterInstance) o;
        return Objects.equals(uuid, that.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }

    @Override
    public String toString() {
        return "JimixFilterInstance{" +
                "uuid='" + uuid + '\'' +
                ", descriptor=" + descriptor +
                '}';
    }
}

package org.pcsoft.app.jimix.core.plugin.type;

import org.apache.commons.lang.time.DurationFormatUtils;
import org.apache.commons.lang.time.StopWatch;
import org.pcsoft.app.jimix.commons.exception.JimixPluginAnnotationException;
import org.pcsoft.app.jimix.commons.exception.JimixPluginException;
import org.pcsoft.app.jimix.plugins.api.JimixRenderer;
import org.pcsoft.app.jimix.plugins.api.annotation.JimixRendererDescriptor;
import org.pcsoft.app.jimix.plugins.api.config.JimixRendererConfiguration;
import org.pcsoft.app.jimix.plugins.api.type.JimixSource;
import org.pcsoft.app.jimix.plugins.api.type.JimixPixelWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class JimixRendererInstance implements JimixInstance {
    private static final Logger LOGGER = LoggerFactory.getLogger(JimixRendererInstance.class);
    private static final StopWatch STOP_WATCH = new StopWatch();

    private final JimixRenderer instance;
    private final JimixRendererDescriptor descriptor;

    public JimixRendererInstance(JimixRenderer instance) throws JimixPluginException {
        if (!instance.getClass().isAnnotationPresent(JimixRendererDescriptor.class))
            throw new JimixPluginAnnotationException("Unable to find needed annotation " + JimixRendererDescriptor.class.getName() + " on renderer class " + instance.getClass().getName());

        this.instance = instance;
        this.descriptor = instance.getClass().getAnnotation(JimixRendererDescriptor.class);
    }

    public void apply(JimixPixelWriter pixelWriter, JimixRendererConfiguration configuration, JimixSource applySource) {
        if (LOGGER.isTraceEnabled()) {
            STOP_WATCH.reset();
            STOP_WATCH.start();
        }

        instance.apply(pixelWriter, configuration, applySource);

        if (LOGGER.isTraceEnabled()) {
            STOP_WATCH.stop();
            LOGGER.trace("Renderer run time: " + DurationFormatUtils.formatDuration(STOP_WATCH.getTime(), "ss:SSS"));
        }
    }

    @Override
    public String getIdentifier() {
        return instance.getClass().getName();
    }

    @Override
    public String getName() {
        return descriptor.name();
    }

    @Override
    public String getDescription() {
        return descriptor.description();
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

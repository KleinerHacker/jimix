package org.pcsoft.app.jimix.core.plugin.type;

import org.apache.commons.lang.time.DurationFormatUtils;
import org.apache.commons.lang.time.StopWatch;
import org.pcsoft.app.jimix.commons.exception.JimixPluginAnnotationException;
import org.pcsoft.app.jimix.commons.exception.JimixPluginException;
import org.pcsoft.app.jimix.plugins.api.JimixEffect;
import org.pcsoft.app.jimix.plugins.api.annotation.JimixEffectDescriptor;
import org.pcsoft.app.jimix.plugins.api.config.JimixEffectConfiguration;
import org.pcsoft.app.jimix.plugins.api.type.JimixApplySource;
import org.pcsoft.app.jimix.plugins.api.type.JimixPixelReader;
import org.pcsoft.app.jimix.plugins.api.type.JimixPixelWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class JimixEffectInstance implements JimixInstance {
    private static final Logger LOGGER = LoggerFactory.getLogger(JimixEffectInstance.class);
    private static final StopWatch STOP_WATCH = new StopWatch();

    private final JimixEffect instance;
    private final JimixEffectDescriptor descriptor;

    public JimixEffectInstance(JimixEffect instance) throws JimixPluginException {
        if (!instance.getClass().isAnnotationPresent(JimixEffectDescriptor.class))
            throw new JimixPluginAnnotationException("Unable to find needed annotation " + JimixEffectDescriptor.class.getName() + " on effect class " + instance.getClass().getName());

        this.instance = instance;
        this.descriptor = instance.getClass().getAnnotation(JimixEffectDescriptor.class);
    }

    public void apply(JimixPixelReader pixelReader, JimixPixelWriter pixelWriter, JimixEffectConfiguration configuration, JimixApplySource applySource) {
        if (LOGGER.isTraceEnabled()) {
            STOP_WATCH.reset();
            STOP_WATCH.start();
        }

        instance.apply(pixelReader, pixelWriter, configuration, applySource);

        if (LOGGER.isTraceEnabled()) {
            STOP_WATCH.stop();
            LOGGER.trace("Effect run time: " + DurationFormatUtils.formatDuration(STOP_WATCH.getTime(), "ss:SSS"));
        }
    }

    @Override
    public String getName() {
        return descriptor.name();
    }

    @Override
    public String getDescription() {
        return descriptor.description();
    }

    public Class<? extends JimixEffectConfiguration> getConfigurationClass() {
        return descriptor.configurationClass();
    }

    public boolean isUsableForPictures() {
        return descriptor.usableForPictures();
    }

    public boolean isUsableForMasks() {
        return descriptor.usableForMasks();
    }
}

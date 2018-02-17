package org.pcsoft.app.jimix.core.plugin.type;

import org.apache.commons.lang.time.DurationFormatUtils;
import org.apache.commons.lang.time.StopWatch;
import org.pcsoft.app.jimix.commons.exception.JimixPluginAnnotationException;
import org.pcsoft.app.jimix.commons.exception.JimixPluginException;
import org.pcsoft.app.jimix.plugins.api.JimixBlender;
import org.pcsoft.app.jimix.plugins.api.annotation.JimixBlenderDescriptor;
import org.pcsoft.app.jimix.plugins.api.type.JimixPixelReader;
import org.pcsoft.app.jimix.plugins.api.type.JimixPixelWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class JimixBlenderInstance implements JimixInstance {
    private static final Logger LOGGER = LoggerFactory.getLogger(JimixBlenderInstance.class);
    private static final StopWatch STOP_WATCH = new StopWatch();

    private final JimixBlender instance;
    private final JimixBlenderDescriptor descriptor;

    public JimixBlenderInstance(JimixBlender instance) throws JimixPluginException {
        if (!instance.getClass().isAnnotationPresent(JimixBlenderDescriptor.class))
            throw new JimixPluginAnnotationException("Unable to find needed annotation " + JimixBlenderDescriptor.class.getName() + " on blender class " + instance.getClass().getName());

        this.instance = instance;
        this.descriptor = instance.getClass().getAnnotation(JimixBlenderDescriptor.class);
    }

    public void apply(JimixPixelReader pixelReader1, JimixPixelReader pixelReader2, JimixPixelWriter pixelWriter) {
        if (LOGGER.isTraceEnabled()) {
            STOP_WATCH.reset();
            STOP_WATCH.start();
        }

        instance.apply(pixelReader1, pixelReader2, pixelWriter);

        if (LOGGER.isTraceEnabled()) {
            STOP_WATCH.stop();
            LOGGER.trace("Blender run time: " + DurationFormatUtils.formatDuration(STOP_WATCH.getTime(), "ss:SSS"));
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
}

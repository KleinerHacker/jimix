package org.pcsoft.app.jimix.core.plugin.type;

import javafx.scene.image.Image;
import org.apache.commons.lang.time.DurationFormatUtils;
import org.apache.commons.lang.time.StopWatch;
import org.pcsoft.app.jimix.commons.exception.JimixPluginAnnotationException;
import org.pcsoft.app.jimix.commons.exception.JimixPluginException;
import org.pcsoft.app.jimix.plugins.api.JimixEffect;
import org.pcsoft.app.jimix.plugins.api.annotation.JimixEffectDescriptor;
import org.pcsoft.app.jimix.plugins.api.config.JimixEffectConfiguration;
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

    public Image apply(final Image image, JimixEffectConfiguration configuration) {
        final JimixPixelReaderImpl pixelReader = new JimixPixelReaderImpl(image.getPixelReader(), (int) image.getWidth(), (int) image.getHeight());
        final JimixPixelWriterImpl pixelWriter = new JimixPixelWriterImpl((int) image.getWidth(), (int) image.getHeight());

        if (LOGGER.isTraceEnabled()) {
            STOP_WATCH.reset();
            STOP_WATCH.start();
        }

        instance.apply(pixelReader, pixelWriter, configuration);

        if (LOGGER.isTraceEnabled()) {
            STOP_WATCH.stop();
            LOGGER.trace("Effect run time: " + DurationFormatUtils.formatDuration(STOP_WATCH.getTime(), "ss:SSS"));
        }

        return pixelWriter.buildImage();
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

    public Class<? extends JimixEffectConfiguration> getConfigurationClass() {
        return descriptor.configurationClass();
    }
}

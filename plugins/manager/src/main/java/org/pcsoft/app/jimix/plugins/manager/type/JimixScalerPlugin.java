package org.pcsoft.app.jimix.plugins.manager.type;

import javafx.scene.image.Image;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DurationFormatUtils;
import org.apache.commons.lang.time.StopWatch;
import org.pcsoft.app.jimix.commons.exception.JimixPluginAnnotationException;
import org.pcsoft.app.jimix.commons.exception.JimixPluginException;
import org.pcsoft.app.jimix.commons.exception.JimixPluginExecutionException;
import org.pcsoft.app.jimix.plugins.api.JimixScaler;
import org.pcsoft.app.jimix.plugins.api.annotation.JimixScalerDescriptor;
import org.pcsoft.app.jimix.plugins.api.type.JimixSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public final class JimixScalerPlugin implements JimixPlugin<JimixScalerInstance> {
    private static final Logger LOGGER = LoggerFactory.getLogger(JimixScalerPlugin.class);
    private static final StopWatch STOP_WATCH = new StopWatch();

    private final JimixScaler instance;
    private final JimixScalerDescriptor descriptor;
    private final ResourceBundle resourceBundle;

    public JimixScalerPlugin(JimixScaler instance) throws JimixPluginException {
        if (!instance.getClass().isAnnotationPresent(JimixScalerDescriptor.class))
            throw new JimixPluginAnnotationException("Unable to find needed annotation " + JimixScalerDescriptor.class.getName() + " on scaler class " + instance.getClass().getName());

        this.instance = instance;
        this.descriptor = instance.getClass().getAnnotation(JimixScalerDescriptor.class);
        if (!StringUtils.isEmpty(descriptor.resourceBundle())) {
            try {
                resourceBundle = ResourceBundle.getBundle(descriptor.resourceBundle(), Locale.getDefault(), instance.getClass().getClassLoader());
            } catch (MissingResourceException e) {
                throw new JimixPluginAnnotationException("Unable to find resource bundle " + descriptor.resourceBundle() + " for scaler " + instance.getClass().getName(), e);
            }
        } else {
            resourceBundle = null;
        }
    }

    Image apply(Image image, int targetWidth, int targetHeight, JimixSource source) throws JimixPluginExecutionException {
        final Image resultImage;

        if (LOGGER.isTraceEnabled()) {
            STOP_WATCH.reset();
            STOP_WATCH.start();
        }

        try {
            resultImage = instance.apply(image, targetWidth, targetHeight, source);
        } catch (Exception e) {
            throw new JimixPluginExecutionException("Error while running renderer", e);
        }

        if (LOGGER.isTraceEnabled()) {
            STOP_WATCH.stop();
            LOGGER.trace("Scaler run time: " + DurationFormatUtils.formatDuration(STOP_WATCH.getTime(), "ss:SSS"));
        }

        return resultImage;
    }

    @Override
    public JimixScalerInstance createInstance() throws JimixPluginException {
        return new JimixScalerInstance(this);
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

    public boolean usableForPictures() {
        return descriptor.usableForPictures();
    }

    public boolean usableForMasks() {
        return descriptor.usableForMasks();
    }
}

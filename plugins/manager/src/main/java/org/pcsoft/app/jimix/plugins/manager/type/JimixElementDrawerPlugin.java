package org.pcsoft.app.jimix.plugins.manager.type;

import javafx.scene.image.Image;
import org.apache.commons.lang.time.DurationFormatUtils;
import org.apache.commons.lang.time.StopWatch;
import org.pcsoft.app.jimix.commons.exception.JimixPluginAnnotationException;
import org.pcsoft.app.jimix.commons.exception.JimixPluginException;
import org.pcsoft.app.jimix.commons.exception.JimixPluginExecutionException;
import org.pcsoft.app.jimix.plugins.api.JimixElementDrawer;
import org.pcsoft.app.jimix.plugins.api.annotation.JimixElementDrawerDescriptor;
import org.pcsoft.app.jimix.plugins.api.type.JimixPluginElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class JimixElementDrawerPlugin implements JimixPlugin<JimixElementDrawerInstance> {
    private static final Logger LOGGER = LoggerFactory.getLogger(JimixEffectPlugin.class);
    private static final StopWatch STOP_WATCH = new StopWatch();

    private final JimixElementDrawer instance;
    private final JimixElementDrawerDescriptor descriptor;

    public JimixElementDrawerPlugin(JimixElementDrawer instance) throws JimixPluginException {
        if (!instance.getClass().isAnnotationPresent(JimixElementDrawerDescriptor.class))
            throw new JimixPluginAnnotationException("Unable to find needed annotation " + JimixElementDrawerDescriptor.class.getName() + " on element drawer class " + instance.getClass().getName());

        this.instance = instance;
        this.descriptor = instance.getClass().getAnnotation(JimixElementDrawerDescriptor.class);
    }

    public Image draw(final JimixPluginElement elementModel, final int width, final int height) throws JimixPluginExecutionException {
        final Image resultImage;

        if (LOGGER.isTraceEnabled()) {
            STOP_WATCH.reset();
            STOP_WATCH.start();
        }

        try {
            resultImage = instance.draw(elementModel, width, height);
        } catch (Exception e) {
            throw new JimixPluginExecutionException("Error while running element drawer", e);
        }

        if (LOGGER.isTraceEnabled()) {
            STOP_WATCH.stop();
            LOGGER.trace("Element drawer run time: " + DurationFormatUtils.formatDuration(STOP_WATCH.getTime(), "ss:SSS"));
        }

        return resultImage;
    }

    public Class<? extends JimixPluginElement> getElementModelClass() {
        return descriptor.elementModelClass();
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public String getIdentifier() {
        return instance.getClass().getName();
    }

    @Override
    public JimixElementDrawerInstance createInstance() throws JimixPluginException {
        return new JimixElementDrawerInstance(this);
    }
}

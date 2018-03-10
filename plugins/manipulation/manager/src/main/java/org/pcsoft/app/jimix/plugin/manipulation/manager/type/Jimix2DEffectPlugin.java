package org.pcsoft.app.jimix.plugin.manipulation.manager.type;

import javafx.scene.Node;
import org.apache.commons.lang.time.DurationFormatUtils;
import org.apache.commons.lang.time.StopWatch;
import org.pcsoft.app.jimix.commons.exception.JimixPluginException;
import org.pcsoft.app.jimix.commons.exception.JimixPluginExecutionException;
import org.pcsoft.app.jimix.plugin.manipulation.api.Jimix2DEffect;
import org.pcsoft.app.jimix.plugin.manipulation.api.config.JimixEffectConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Jimix2DEffectPlugin extends JimixEffectPlugin<Jimix2DEffectInstance, Jimix2DEffect> {
    private static final Logger LOGGER = LoggerFactory.getLogger(Jimix2DEffectPlugin.class);
    private static final StopWatch STOP_WATCH = new StopWatch();

    public Jimix2DEffectPlugin(Jimix2DEffect instance) throws JimixPluginException {
        super(instance);
    }

    Node apply(final Node node, final int x, final int y, JimixEffectConfiguration configuration) throws JimixPluginExecutionException {
        if (LOGGER.isTraceEnabled()) {
            STOP_WATCH.reset();
            STOP_WATCH.start();
        }

        final Node resultNode;
        try {
            resultNode = instance.apply(node, x, y, configuration);
        } catch (Exception e) {
            throw new JimixPluginExecutionException("Error while running 2D effect", e);
        }

        if (LOGGER.isTraceEnabled()) {
            STOP_WATCH.stop();
            LOGGER.trace("2D Effect run time: " + DurationFormatUtils.formatDuration(STOP_WATCH.getTime(), "ss:SSS"));
        }

        return resultNode;
    }

    @Override
    public Jimix2DEffectInstance createInstance() throws JimixPluginException {
        try {
            return new Jimix2DEffectInstance(this, descriptor.configurationClass().newInstance());
        } catch (InstantiationException | IllegalAccessException e) {
            throw new JimixPluginException("Unable to create 2D effect instance", e);
        }
    }
}

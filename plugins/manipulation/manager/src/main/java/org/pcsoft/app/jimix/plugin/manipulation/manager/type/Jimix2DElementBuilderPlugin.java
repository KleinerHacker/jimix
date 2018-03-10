package org.pcsoft.app.jimix.plugin.manipulation.manager.type;

import javafx.scene.Node;
import org.apache.commons.lang.time.DurationFormatUtils;
import org.apache.commons.lang.time.StopWatch;
import org.pcsoft.app.jimix.commons.exception.JimixPluginException;
import org.pcsoft.app.jimix.commons.exception.JimixPluginExecutionException;
import org.pcsoft.app.jimix.plugin.common.api.type.JimixPlugin2DElement;
import org.pcsoft.app.jimix.plugin.manipulation.api.Jimix2DElementBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Jimix2DElementBuilderPlugin extends JimixElementBuilderPlugin<Jimix2DElementBuilderInstance, Jimix2DElementBuilder, JimixPlugin2DElement> {
    private static final Logger LOGGER = LoggerFactory.getLogger(Jimix2DEffectPlugin.class);
    private static final StopWatch STOP_WATCH = new StopWatch();

    public Jimix2DElementBuilderPlugin(Jimix2DElementBuilder instance) throws JimixPluginException {
        super(instance);
    }

    public Node buildNode(final JimixPlugin2DElement elementModel, final int x, final int y) throws JimixPluginExecutionException {
        final Node resultNode;

        if (LOGGER.isTraceEnabled()) {
            STOP_WATCH.reset();
            STOP_WATCH.start();
        }

        try {
            resultNode = instance.buildNode(elementModel, x, y);
        } catch (Exception e) {
            throw new JimixPluginExecutionException("Error while running 2D element builder", e);
        }

        if (LOGGER.isTraceEnabled()) {
            STOP_WATCH.stop();
            LOGGER.trace("2D Element builder run time: " + DurationFormatUtils.formatDuration(STOP_WATCH.getTime(), "ss:SSS"));
        }

        return resultNode;
    }

    @Override
    public Jimix2DElementBuilderInstance createInstance() throws JimixPluginException {
        return new Jimix2DElementBuilderInstance(this);
    }
}

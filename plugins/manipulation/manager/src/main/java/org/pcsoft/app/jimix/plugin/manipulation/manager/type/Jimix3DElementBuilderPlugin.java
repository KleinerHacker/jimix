package org.pcsoft.app.jimix.plugin.manipulation.manager.type;

import javafx.scene.Node;
import org.apache.commons.lang.time.DurationFormatUtils;
import org.apache.commons.lang.time.StopWatch;
import org.pcsoft.app.jimix.commons.exception.JimixPluginException;
import org.pcsoft.app.jimix.commons.exception.JimixPluginExecutionException;
import org.pcsoft.app.jimix.plugin.common.api.type.JimixPlugin3DElement;
import org.pcsoft.app.jimix.plugin.manipulation.api.Jimix3DElementBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Jimix3DElementBuilderPlugin extends JimixElementBuilderPlugin<Jimix3DElementBuilderInstance, Jimix3DElementBuilder, JimixPlugin3DElement> {
    private static final Logger LOGGER = LoggerFactory.getLogger(Jimix2DEffectPlugin.class);
    private static final StopWatch STOP_WATCH = new StopWatch();

    public Jimix3DElementBuilderPlugin(Jimix3DElementBuilder instance) throws JimixPluginException {
        super(instance);
    }

    public Node buildNode(final JimixPlugin3DElement elementModel, int width, int height) throws JimixPluginExecutionException {
        final Node resultNode;

        if (LOGGER.isTraceEnabled()) {
            STOP_WATCH.reset();
            STOP_WATCH.start();
        }

        try {
            resultNode = instance.buildNode(elementModel, width, height);
        } catch (Exception e) {
            throw new JimixPluginExecutionException("Error while running 3D element builder", e);
        }

        if (LOGGER.isTraceEnabled()) {
            STOP_WATCH.stop();
            LOGGER.trace("3D Element builder run time: " + DurationFormatUtils.formatDuration(STOP_WATCH.getTime(), "ss:SSS"));
        }

        return resultNode;
    }

    @Override
    public Jimix3DElementBuilderInstance createInstance() throws JimixPluginException {
        return new Jimix3DElementBuilderInstance(this);
    }
}

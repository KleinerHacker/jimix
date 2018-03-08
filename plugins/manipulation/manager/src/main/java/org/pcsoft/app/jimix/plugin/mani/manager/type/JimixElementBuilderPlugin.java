package org.pcsoft.app.jimix.plugin.mani.manager.type;

import javafx.scene.Node;
import javafx.scene.image.Image;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DurationFormatUtils;
import org.apache.commons.lang.time.StopWatch;
import org.pcsoft.app.jimix.commons.exception.JimixPluginAnnotationException;
import org.pcsoft.app.jimix.commons.exception.JimixPluginException;
import org.pcsoft.app.jimix.commons.exception.JimixPluginExecutionException;
import org.pcsoft.app.jimix.plugin.common.api.type.JimixPluginElement;
import org.pcsoft.app.jimix.plugin.common.manager.type.JimixPlugin;
import org.pcsoft.app.jimix.plugin.mani.api.JimixElementBuilder;
import org.pcsoft.app.jimix.plugin.mani.api.annotation.JimixElementBuilderDescriptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public final class JimixElementBuilderPlugin implements JimixPlugin<JimixElementBuilderInstance> {
    private static final Logger LOGGER = LoggerFactory.getLogger(JimixEffectPlugin.class);
    private static final StopWatch STOP_WATCH = new StopWatch();

    private final JimixElementBuilder instance;
    private final JimixElementBuilderDescriptor descriptor;
    private final ResourceBundle resourceBundle;
    private final Image icon;

    public JimixElementBuilderPlugin(JimixElementBuilder instance) throws JimixPluginException {
        if (!instance.getClass().isAnnotationPresent(JimixElementBuilderDescriptor.class))
            throw new JimixPluginAnnotationException("Unable to find needed annotation " + JimixElementBuilderDescriptor.class.getName() + " on element drawer class " + instance.getClass().getName());

        this.instance = instance;
        this.descriptor = instance.getClass().getAnnotation(JimixElementBuilderDescriptor.class);

        if (!StringUtils.isEmpty(descriptor.resourceBundle())) {
            try {
                resourceBundle = ResourceBundle.getBundle(descriptor.resourceBundle(), Locale.getDefault(), instance.getClass().getClassLoader());
            } catch (MissingResourceException e) {
                throw new JimixPluginAnnotationException("Unable to find resource bundle " + descriptor.resourceBundle() + " for filter " + instance.getClass().getName(), e);
            }
        } else {
            resourceBundle = null;
        }
        if (!StringUtils.isEmpty(descriptor.iconPath())) {
            try {
                icon = new Image(instance.getClass().getResourceAsStream(descriptor.iconPath()));
            } catch (Exception e) {
                throw new JimixPluginAnnotationException("Unable to find icon " + descriptor.iconPath() + " for filter " + instance.getClass().getName(), e);
            }
        } else {
            icon = null;
        }
    }

    public Node buildNode(final JimixPluginElement elementModel, final int x, final int y, final int width, final int height) throws JimixPluginExecutionException {
        final Node resultNode;

        if (LOGGER.isTraceEnabled()) {
            STOP_WATCH.reset();
            STOP_WATCH.start();
        }

        try {
            resultNode = instance.buildNode(elementModel, x, y, width, height);
        } catch (Exception e) {
            throw new JimixPluginExecutionException("Error while running element builder", e);
        }

        if (LOGGER.isTraceEnabled()) {
            STOP_WATCH.stop();
            LOGGER.trace("Element builder run time: " + DurationFormatUtils.formatDuration(STOP_WATCH.getTime(), "ss:SSS"));
        }

        return resultNode;
    }

    public Class<? extends JimixPluginElement> getElementModelClass() {
        return descriptor.elementModelClass();
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

    @Override
    public String getIdentifier() {
        return instance.getClass().getName();
    }

    public Image getIcon() {
        return icon;
    }

    @Override
    public JimixElementBuilderInstance createInstance() throws JimixPluginException {
        return new JimixElementBuilderInstance(this);
    }
}

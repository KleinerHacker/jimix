package org.pcsoft.app.jimix.plugin.manipulation.manager.type;

import javafx.scene.Node;
import javafx.scene.image.Image;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DurationFormatUtils;
import org.apache.commons.lang.time.StopWatch;
import org.pcsoft.app.jimix.commons.exception.JimixPluginAnnotationException;
import org.pcsoft.app.jimix.commons.exception.JimixPluginException;
import org.pcsoft.app.jimix.commons.exception.JimixPluginExecutionException;
import org.pcsoft.app.jimix.plugin.common.api.type.JimixPlugin2DElement;
import org.pcsoft.app.jimix.plugin.common.manager.type.JimixPlugin;
import org.pcsoft.app.jimix.plugin.manipulation.api.Jimix2DElementBuilder;
import org.pcsoft.app.jimix.plugin.manipulation.api.annotation.JimixElementBuilderDescriptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Objects;
import java.util.ResourceBundle;

public final class Jimix2DElementBuilderPlugin implements JimixPlugin<Jimix2DElementBuilderInstance> {
    private static final Logger LOGGER = LoggerFactory.getLogger(Jimix2DEffectPlugin.class);
    private static final StopWatch STOP_WATCH = new StopWatch();

    private final Jimix2DElementBuilder instance;
    private final JimixElementBuilderDescriptor descriptor;
    private final ResourceBundle resourceBundle;
    private final Image icon;

    public Jimix2DElementBuilderPlugin(Jimix2DElementBuilder instance) throws JimixPluginException {
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

    public Node buildNode(final JimixPlugin2DElement elementModel, final int x, final int y, final int width, final int height) throws JimixPluginExecutionException {
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

    @SuppressWarnings("unchecked")
    public Class<? extends JimixPlugin2DElement> getElementModelClass() {
        return (Class<? extends JimixPlugin2DElement>) descriptor.elementModelClass();
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

    public String getGroup() {
        if (StringUtils.isEmpty(descriptor.group()))
            return null;
        if (resourceBundle != null)
            return resourceBundle.getString(descriptor.group());

        return descriptor.group();
    }

    public boolean manualAddable() {
        return descriptor.manualAddable();
    }

    @Override
    public Jimix2DElementBuilderInstance createInstance() throws JimixPluginException {
        return new Jimix2DElementBuilderInstance(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Jimix2DElementBuilderPlugin that = (Jimix2DElementBuilderPlugin) o;
        return Objects.equals(getIdentifier(), that.getIdentifier());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getIdentifier());
    }
}

package org.pcsoft.app.jimix.plugin.manipulation.manager.type;

import javafx.scene.image.Image;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.StopWatch;
import org.pcsoft.app.jimix.commons.exception.JimixPluginAnnotationException;
import org.pcsoft.app.jimix.commons.exception.JimixPluginException;
import org.pcsoft.app.jimix.plugin.common.api.type.JimixElementType;
import org.pcsoft.app.jimix.plugin.common.api.type.JimixPluginElement;
import org.pcsoft.app.jimix.plugin.common.manager.type.JimixPlugin;
import org.pcsoft.app.jimix.plugin.manipulation.api.JimixElementBuilder;
import org.pcsoft.app.jimix.plugin.manipulation.api.annotation.JimixElementBuilderDescriptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Objects;
import java.util.ResourceBundle;

public abstract class JimixElementBuilderPlugin<T extends JimixElementBuilderInstance, E extends JimixElementBuilder, M extends JimixPluginElement> implements JimixPlugin<T> {
    private static final Logger LOGGER = LoggerFactory.getLogger(Jimix2DEffectPlugin.class);
    private static final StopWatch STOP_WATCH = new StopWatch();

    protected final E instance;
    protected final JimixElementBuilderDescriptor descriptor;
    protected final ResourceBundle resourceBundle;
    protected final Image icon;

    public JimixElementBuilderPlugin(E instance) throws JimixPluginException {
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

    @SuppressWarnings("unchecked")
    public Class<? extends M> getElementModelClass() {
        return (Class<? extends M>) descriptor.elementModelClass();
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

    public JimixElementType getType() {
        return instance.getType();
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
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Jimix2DElementBuilderPlugin that = (Jimix2DElementBuilderPlugin) o;
        return Objects.equals(getIdentifier(), that.getIdentifier());
    }

    @Override
    public final int hashCode() {
        return Objects.hash(getIdentifier());
    }
}

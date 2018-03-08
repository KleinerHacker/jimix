package org.pcsoft.app.jimix.plugin.manipulation.manager.type;

import javafx.scene.image.Image;
import org.apache.commons.lang.StringUtils;
import org.pcsoft.app.jimix.commons.exception.JimixPluginAnnotationException;
import org.pcsoft.app.jimix.commons.exception.JimixPluginException;
import org.pcsoft.app.jimix.plugin.common.manager.type.JimixPlugin;
import org.pcsoft.app.jimix.plugin.manipulation.api.JimixEffect;
import org.pcsoft.app.jimix.plugin.manipulation.api.annotation.JimixEffectDescriptor;
import org.pcsoft.app.jimix.plugin.manipulation.api.type.JimixEffectVariant;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Objects;
import java.util.ResourceBundle;

public abstract class JimixEffectPlugin<T extends JimixEffectInstance, E extends JimixEffect> implements JimixPlugin<T> {
    protected final E instance;
    protected final JimixEffectDescriptor descriptor;
    protected final ResourceBundle resourceBundle;
    protected final Image icon;

    public JimixEffectPlugin(E instance) throws JimixPluginException {
        if (!instance.getClass().isAnnotationPresent(JimixEffectDescriptor.class))
            throw new JimixPluginAnnotationException("Unable to find needed annotation " + JimixEffectDescriptor.class.getName() + " on effect class " + instance.getClass().getName());

        this.instance = instance;
        this.descriptor = instance.getClass().getAnnotation(JimixEffectDescriptor.class);
        if (!StringUtils.isEmpty(descriptor.resourceBundle())) {
            try {
                resourceBundle = ResourceBundle.getBundle(descriptor.resourceBundle(), Locale.getDefault(), instance.getClass().getClassLoader());
            } catch (MissingResourceException e) {
                throw new JimixPluginAnnotationException("Unable to find resource bundle " + descriptor.resourceBundle() + " for effect " + instance.getClass().getName(), e);
            }
        } else {
            resourceBundle = null;
        }
        if (!StringUtils.isEmpty(descriptor.iconPath())) {
            try {
                icon = new Image(instance.getClass().getResourceAsStream(descriptor.iconPath()));
            } catch (Exception e) {
                throw new JimixPluginAnnotationException("Unable to find icon " + descriptor.iconPath() + " for effect " + instance.getClass().getName(), e);
            }
        } else {
            icon = null;
        }
    }

    public JimixEffectVariant[] getVariants() {
        return instance.getVariants();
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

    public String getGroup() {
        if (StringUtils.isEmpty(descriptor.group()))
            return null;
        if (resourceBundle != null)
            return resourceBundle.getString(descriptor.group());

        return descriptor.group();
    }

    public Image getIcon() {
        return icon;
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

package org.pcsoft.app.jimix.plugins.manager.type;

import javafx.scene.image.Image;
import javafx.scene.input.Clipboard;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.StopWatch;
import org.pcsoft.app.jimix.commons.exception.JimixPluginAnnotationException;
import org.pcsoft.app.jimix.commons.exception.JimixPluginException;
import org.pcsoft.app.jimix.plugins.api.JimixClipboardProvider;
import org.pcsoft.app.jimix.plugins.api.annotation.JimixClipboardProviderDescriptor;
import org.pcsoft.app.jimix.plugins.api.type.JimixPluginElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public final class JimixClipboardProviderPlugin implements JimixPlugin<JimixClipboardProviderInstance> {
    private static final Logger LOGGER = LoggerFactory.getLogger(JimixEffectPlugin.class);
    private static final StopWatch STOP_WATCH = new StopWatch();

    private final JimixClipboardProvider instance;
    private final JimixClipboardProviderDescriptor descriptor;
    private final ResourceBundle resourceBundle;
    private final Image icon;

    public JimixClipboardProviderPlugin(JimixClipboardProvider instance) throws JimixPluginException {
        if (!instance.getClass().isAnnotationPresent(JimixClipboardProviderDescriptor.class))
            throw new JimixPluginAnnotationException("Unable to find needed annotation " + JimixClipboardProviderDescriptor.class.getName() + " on clipboard provider class " + instance.getClass().getName());

        this.instance = instance;
        this.descriptor = instance.getClass().getAnnotation(JimixClipboardProviderDescriptor.class);
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
                throw new JimixPluginAnnotationException("Unable to find icon " + descriptor.iconPath() + " for filter " + instance.getClass().getName(), e);
            }
        } else {
            icon = null;
        }
    }

    boolean acceptClipboardContent(Clipboard clipboard) {
        return instance.acceptClipboardContent(clipboard);
    }

    JimixPluginElement createElementFromClipboard(Clipboard clipboard) {
        return instance.createElementFromClipboard(clipboard);
    }

    @Override
    public JimixClipboardProviderInstance createInstance() throws JimixPluginException {
        return new JimixClipboardProviderInstance(this);
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

    public Image getIcon() {
        return icon;
    }
}

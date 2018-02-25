package org.pcsoft.app.jimix.core.plugin.type;

import javafx.scene.image.Image;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.StopWatch;
import org.pcsoft.app.jimix.commons.exception.JimixPluginAnnotationException;
import org.pcsoft.app.jimix.commons.exception.JimixPluginException;
import org.pcsoft.app.jimix.plugins.api.JimixFileTypeProvider;
import org.pcsoft.app.jimix.plugins.api.annotation.JimixFileTypeProviderDescriptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public final class JimixFileTypeProviderInstance implements JimixInstance {
    private static final Logger LOGGER = LoggerFactory.getLogger(JimixFileTypeProviderInstance.class);
    private static final StopWatch STOP_WATCH = new StopWatch();

    private final JimixFileTypeProvider instance;
    private final JimixFileTypeProviderDescriptor descriptor;
    private final ResourceBundle resourceBundle;

    public JimixFileTypeProviderInstance(JimixFileTypeProvider instance) throws JimixPluginException {
        if (!instance.getClass().isAnnotationPresent(JimixFileTypeProviderDescriptor.class))
            throw new JimixPluginAnnotationException("Unable to find needed annotation " + JimixFileTypeProviderDescriptor.class.getName() + " on file type provider class " + instance.getClass().getName());

        this.instance = instance;
        this.descriptor = instance.getClass().getAnnotation(JimixFileTypeProviderDescriptor.class);
        if (!StringUtils.isEmpty(descriptor.resourceBundle())) {
            try {
                resourceBundle = ResourceBundle.getBundle(descriptor.resourceBundle(), Locale.getDefault(), instance.getClass().getClassLoader());
            } catch (MissingResourceException e) {
                throw new JimixPluginAnnotationException("Unable to find resource bundle " + descriptor.resourceBundle() + " for effect " + instance.getClass().getName(), e);
            }
        } else {
            resourceBundle = null;
        }
    }

    public boolean acceptFile(final File file) throws IOException {
        byte[] magicBytes = new byte[descriptor.magicBytesCount()];
        try (final InputStream in = new FileInputStream(file)) {
            final int read = in.read(magicBytes, 0, magicBytes.length);
            if (read != magicBytes.length)
                return false;
        }

        return instance.acceptFile(magicBytes);
    }

    public void save(Image image, File file) throws IOException {
        instance.save(image, file);
    }

    public Image load(File file) throws IOException {
        return instance.load(file);
    }

    @Override
    public String getIdentifier() {
        return instance.getClass().getName();
    }

    @Override
    public String getName() {
        if (resourceBundle != null)
            return resourceBundle.getString(descriptor.description());

        return descriptor.description();
    }

    @Override
    public String getDescription() {
        if (resourceBundle != null)
            return resourceBundle.getString(descriptor.description());

        return descriptor.description();
    }

    public String[] getExtensions() {
        return descriptor.extensions();
    }
}

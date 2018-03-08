package org.pcsoft.app.jimix.plugin.system.manager.type;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.StopWatch;
import org.pcsoft.app.jimix.commons.exception.JimixPluginAnnotationException;
import org.pcsoft.app.jimix.commons.exception.JimixPluginException;
import org.pcsoft.app.jimix.plugin.common.manager.type.JimixPlugin;
import org.pcsoft.app.jimix.plugin.manipulation.manager.type.Jimix2DElementBuilderPlugin;
import org.pcsoft.app.jimix.plugin.system.api.JimixProjectFileTypeProvider;
import org.pcsoft.app.jimix.plugin.system.api.annotation.JimixFileTypeProviderDescriptor;
import org.pcsoft.app.jimix.project.JimixProjectModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Objects;
import java.util.ResourceBundle;

public final class JimixProjectFileTypeProviderPlugin implements JimixPlugin<JimixProjectFileTypeProviderInstance> {
    private static final Logger LOGGER = LoggerFactory.getLogger(JimixProjectFileTypeProviderPlugin.class);
    private static final StopWatch STOP_WATCH = new StopWatch();

    private final JimixProjectFileTypeProvider instance;
    private final JimixFileTypeProviderDescriptor descriptor;
    private final ResourceBundle resourceBundle;

    public JimixProjectFileTypeProviderPlugin(JimixProjectFileTypeProvider instance) throws JimixPluginException {
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

    boolean acceptFile(final File file) throws IOException {
        byte[] magicBytes = new byte[descriptor.magicBytesCount()];
        try (final InputStream in = new FileInputStream(file)) {
            final int read = in.read(magicBytes, 0, magicBytes.length);
            if (read != magicBytes.length)
                return false;
        }

        return instance.acceptFile(magicBytes);
    }

    void save(JimixProjectModel projectModel, File file) throws IOException {
        instance.save(projectModel, file);
    }

    JimixProjectModel load(File file) throws IOException {
        return instance.load(file);
    }

    @Override
    public JimixProjectFileTypeProviderInstance createInstance() throws JimixPluginException {
        return new JimixProjectFileTypeProviderInstance(this);
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

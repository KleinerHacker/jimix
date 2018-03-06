package org.pcsoft.app.jimix.plugin.system.api;

import org.pcsoft.app.jimix.project.JimixProjectModel;

import java.io.File;
import java.io.IOException;

public interface JimixProjectFileTypeProvider extends JimixFileTypeProvider {
    void save(final JimixProjectModel projectModel, final File file) throws IOException;
    JimixProjectModel load(final File file) throws IOException;
}

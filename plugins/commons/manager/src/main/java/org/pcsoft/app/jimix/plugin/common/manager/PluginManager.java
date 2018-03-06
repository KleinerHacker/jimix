package org.pcsoft.app.jimix.plugin.common.manager;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface PluginManager {
    void init(final List<File> pluginPathList) throws IOException;
}

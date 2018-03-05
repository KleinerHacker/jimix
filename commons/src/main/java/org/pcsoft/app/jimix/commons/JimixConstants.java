package org.pcsoft.app.jimix.commons;

import org.apache.commons.lang.SystemUtils;

import java.io.File;

public final class JimixConstants {
    public static final File DEFAULT_PLUGIN_PATH = new File("plugins");
    public static final File DEFAULT_JIMIX_PATH = new File(SystemUtils.getUserDir(),".jimix");
    public static final File DEFAULT_VARIANT_FILE = new File(DEFAULT_JIMIX_PATH, "variants.dat");

    static {
        if (!DEFAULT_PLUGIN_PATH.exists()) {
            DEFAULT_PLUGIN_PATH.mkdirs();
        }
        if (!DEFAULT_JIMIX_PATH.exists()) {
            DEFAULT_JIMIX_PATH.mkdirs();
        }
    }

    private JimixConstants() {
    }
}

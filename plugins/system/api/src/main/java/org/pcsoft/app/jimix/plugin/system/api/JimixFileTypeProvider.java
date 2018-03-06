package org.pcsoft.app.jimix.plugin.system.api;

public interface JimixFileTypeProvider {
    boolean acceptFile(final byte[] magicBytes);
}

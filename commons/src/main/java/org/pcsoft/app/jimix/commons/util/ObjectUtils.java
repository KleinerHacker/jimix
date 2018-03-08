package org.pcsoft.app.jimix.commons.util;

public final class ObjectUtils {
    @SuppressWarnings("unchecked")
    public static <T>T defaultIfNull(T value, T defaultValue) {
        return (T) org.apache.commons.lang.ObjectUtils.defaultIfNull(value, defaultValue);
    }

    private ObjectUtils() {
    }
}

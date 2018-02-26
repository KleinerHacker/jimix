package org.pcsoft.app.jimix.app.language;

import java.util.ResourceBundle;

public final class LanguageResources {
    private static final ResourceBundle BUNDLE = new MultipleResourceBundle(
            ResourceBundle.getBundle("language/app"),
            ResourceBundle.getBundle("language/view"),
            ResourceBundle.getBundle("language/dialog"),
            ResourceBundle.getBundle("language/common")
    );

    public static ResourceBundle getBundle() {
        return BUNDLE;
    }

    public static String getText(final String key) {
        return BUNDLE.getString(key);
    }

    public static String getTextFormat(final String key, final Object[] args) {
        if (!BUNDLE.containsKey(key))
            return null;
        
        return String.format(getText(key), args);
    }

    private LanguageResources() {
    }
}

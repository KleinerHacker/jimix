package org.pcsoft.app.jimix.commons.exception;

public class JimixPluginException extends JimixException {
    public JimixPluginException() {
    }

    public JimixPluginException(String message) {
        super(message);
    }

    public JimixPluginException(String message, Throwable cause) {
        super(message, cause);
    }

    public JimixPluginException(Throwable cause) {
        super(cause);
    }
}

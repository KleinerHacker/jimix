package org.pcsoft.app.jimix.commons.exception;

public class JimixPluginExecutionException extends JimixPluginException {
    public JimixPluginExecutionException() {
    }

    public JimixPluginExecutionException(String message) {
        super(message);
    }

    public JimixPluginExecutionException(String message, Throwable cause) {
        super(message, cause);
    }

    public JimixPluginExecutionException(Throwable cause) {
        super(cause);
    }
}

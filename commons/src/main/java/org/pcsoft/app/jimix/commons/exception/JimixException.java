package org.pcsoft.app.jimix.commons.exception;

public class JimixException extends RuntimeException {
    public JimixException() {
    }

    public JimixException(String message) {
        super(message);
    }

    public JimixException(String message, Throwable cause) {
        super(message, cause);
    }

    public JimixException(Throwable cause) {
        super(cause);
    }
}

package org.pcsoft.app.jimix.commons.exception;

public class JimixPropertyException extends JimixException {
    public JimixPropertyException() {
    }

    public JimixPropertyException(String message) {
        super(message);
    }

    public JimixPropertyException(String message, Throwable cause) {
        super(message, cause);
    }

    public JimixPropertyException(Throwable cause) {
        super(cause);
    }
}

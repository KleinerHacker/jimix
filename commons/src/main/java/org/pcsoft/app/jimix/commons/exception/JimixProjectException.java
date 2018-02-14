package org.pcsoft.app.jimix.commons.exception;

public class JimixProjectException extends JimixException {
    public JimixProjectException() {
    }

    public JimixProjectException(String message) {
        super(message);
    }

    public JimixProjectException(String message, Throwable cause) {
        super(message, cause);
    }

    public JimixProjectException(Throwable cause) {
        super(cause);
    }
}

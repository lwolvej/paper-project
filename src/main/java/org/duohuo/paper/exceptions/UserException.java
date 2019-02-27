package org.duohuo.paper.exceptions;

public class UserException extends RuntimeException {

    private static final long serialVersionUID = 1468053167419015025L;

    public UserException() {
        super();
    }

    public UserException(String message) {
        super(message);
    }

    public UserException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserException(Throwable cause) {
        super(cause);
    }
}

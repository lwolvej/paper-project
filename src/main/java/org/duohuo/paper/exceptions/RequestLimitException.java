package org.duohuo.paper.exceptions;

public class RequestLimitException extends RuntimeException {

    private static final long serialVersionUID = 2656501369045528623L;

    public RequestLimitException() {
    }

    public RequestLimitException(String message) {
        super(message);
    }

    public RequestLimitException(String message, Throwable cause) {
        super(message, cause);
    }

    public RequestLimitException(Throwable cause) {
        super(cause);
    }
}

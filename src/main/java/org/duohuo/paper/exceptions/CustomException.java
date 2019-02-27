package org.duohuo.paper.exceptions;

/**
 * @author lwolvej
 */
public class CustomException extends RuntimeException {

    private static final long serialVersionUID = 8951952134948551881L;

    public CustomException() {
        super();
    }

    public CustomException(String message) {
        super(message);
    }

    public CustomException(String message, Throwable cause) {
        super(message, cause);
    }

    public CustomException(Throwable cause) {
        super(cause);
    }
}

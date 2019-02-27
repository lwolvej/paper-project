package org.duohuo.paper.exceptions;

/**
 * @author lwolvej
 */
public class NotFoundException extends RuntimeException {

    private static final long serialVersionUID = 2249865276846373857L;

    public NotFoundException() {
        super();
    }

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundException(Throwable cause) {
        super(cause);
    }
}

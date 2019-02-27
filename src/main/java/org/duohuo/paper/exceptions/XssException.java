package org.duohuo.paper.exceptions;

/**
 * @author lwolvej
 */
public class XssException extends RuntimeException {

    private static final long serialVersionUID = -1167301333439384011L;

    public XssException() {
        super();
    }

    public XssException(String message) {
        super(message);
    }

    public XssException(String message, Throwable cause) {
        super(message, cause);
    }

    public XssException(Throwable cause) {
        super(cause);
    }
}

package org.duohuo.paper.exceptions;

/**
 * @author lwolvej
 */
public class CaptchaException extends RuntimeException {

    private static final long serialVersionUID = 2643222818187193152L;

    public CaptchaException() {
        super();
    }

    public CaptchaException(String message) {
        super(message);
    }

    public CaptchaException(String message, Throwable cause) {
        super(message, cause);
    }
}

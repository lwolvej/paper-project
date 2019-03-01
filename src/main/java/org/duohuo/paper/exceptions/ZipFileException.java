package org.duohuo.paper.exceptions;

public class ZipFileException extends RuntimeException {

    private static final long serialVersionUID = 470850299354830711L;

    public ZipFileException() {
    }

    public ZipFileException(String message) {
        super(message);
    }

    public ZipFileException(String message, Throwable cause) {
        super(message, cause);
    }

    public ZipFileException(Throwable cause) {
        super(cause);
    }
}

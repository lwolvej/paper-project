package org.duohuo.paper.exceptions;

public class BaseLineException extends RuntimeException {

    private static final long serialVersionUID = -3718568392229858228L;

    private Integer code;

    public BaseLineException(Integer code) {
        this.code = code;
    }

    public BaseLineException(String message, Integer code) {
        super(message);
        this.code = code;
    }

    public BaseLineException(String message, Throwable cause, Integer code) {
        super(message, cause);
        this.code = code;
    }

    public BaseLineException(Throwable cause, Integer code) {
        super(cause);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}

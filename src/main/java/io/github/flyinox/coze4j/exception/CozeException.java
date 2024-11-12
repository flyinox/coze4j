package io.github.flyinox.coze4j.exception;

public class CozeException extends Exception {
    private final CozeErrorCode errorCode;

    public CozeException(CozeErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public CozeException(CozeErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public CozeException(CozeErrorCode errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public CozeErrorCode getErrorCode() {
        return errorCode;
    }
}
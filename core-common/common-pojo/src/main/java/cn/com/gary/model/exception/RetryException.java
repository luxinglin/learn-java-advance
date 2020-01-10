package cn.com.gary.model.exception;

public class RetryException extends RuntimeException {
    public RetryException(Throwable cause) {
        super(cause);
    }

    public RetryException(String message) {
        super(message);
    }
}

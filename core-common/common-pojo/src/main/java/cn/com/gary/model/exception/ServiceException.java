package cn.com.gary.model.exception;


public class ServiceException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new Service exception.
     */
    public ServiceException() {
        super();
    }

    /**
     * Instantiates a new Service exception.
     *
     * @param message the message
     */
    public ServiceException(String message) {
        super(message);
    }

    /**
     * Instantiates a new Service exception.
     *
     * @param msg   the msg
     * @param cause the cause
     */
    public ServiceException(String msg, Throwable cause) {
        super(msg, cause);
    }

    /**
     * Instantiates a new Service exception.
     *
     * @param cause the cause
     */
    public ServiceException(Throwable cause) {
        super(cause);
    }
}

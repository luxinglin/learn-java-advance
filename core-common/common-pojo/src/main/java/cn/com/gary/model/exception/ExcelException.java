package cn.com.gary.model.exception;

public class ExcelException extends Exception {
    public final static String NO_DATA_EXPORT_CODE = "00";
    private String errorCode;

    public ExcelException() {
        // TODO Auto-generated constructor stub
    }

    public ExcelException(String message) {
        super(message);

        // TODO Auto-generated constructor stub
    }

    public ExcelException(String message, String errorCode) {
        super(message);
        this.setErrorCode(errorCode);
        // TODO Auto-generated constructor stub
    }

    public ExcelException(Throwable cause) {
        super(cause);
        // TODO Auto-generated constructor stub
    }

    public ExcelException(String message, Throwable cause) {
        super(message, cause);
        // TODO Auto-generated constructor stub
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}
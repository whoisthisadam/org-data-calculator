package exception;

public class RecurringEmailException extends Exception {

    private String customMessage;

    private Integer errorCode;

    public RecurringEmailException(String customMessage, Integer errorCode) {
        this.customMessage = customMessage;
        this.errorCode = errorCode;
    }

    public RecurringEmailException(String message, String customMessage, Integer errorCode) {
        super(message);
        this.customMessage = customMessage;
        this.errorCode = errorCode;
    }

    public RecurringEmailException(String message, Throwable cause, String customMessage, Integer errorCode) {
        super(message, cause);
        this.customMessage = customMessage;
        this.errorCode = errorCode;
    }

    public RecurringEmailException(Throwable cause, String customMessage, Integer errorCode) {
        super(cause);
        this.customMessage = customMessage;
        this.errorCode = errorCode;
    }

    public RecurringEmailException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, String customMessage, Integer errorCode) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.customMessage = customMessage;
        this.errorCode = errorCode;
    }
}

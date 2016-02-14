package uz.greenwhite.lib.error;

public class UserError extends RuntimeException {
    public UserError() {
    }

    public UserError(String detailMessage) {
        super(detailMessage);
    }

    public UserError(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public UserError(Throwable throwable) {
        super(throwable);
    }
}

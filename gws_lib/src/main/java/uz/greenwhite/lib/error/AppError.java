package uz.greenwhite.lib.error;

public class AppError extends RuntimeException {

    public AppError() {
    }

    public AppError(String detailMessage) {
        super(detailMessage);
    }

    public AppError(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public AppError(Throwable throwable) {
        super(throwable);
    }

    public static AppError Unsupported() {
        return new AppError("Unsupported");
    }

    public static AppError NullPointer() {
        return new AppError("NULL pointer");
    }

    public static AppError Required() {
        return new AppError("Required");
    }

}

package uz.greenwhite.lib.error;

public class HttpError extends RuntimeException {
    public HttpError() {
    }

    public HttpError(String detailMessage) {
        super(detailMessage);
    }

    public HttpError(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public HttpError(Throwable throwable) {
        super(throwable);
    }
}

package uz.greenwhite.lib.job;

public abstract class Promise<R> {

    public static final int PENDING = 1;
    public static final int RESOLVED = 2;
    public static final int REJECTED = 3;

    public abstract int getState();

    public abstract R getResult();

    public abstract Throwable getError();

    public boolean isPending() {
        return getState() == PENDING;
    }

    public boolean isResolved() {
        return getState() == RESOLVED;
    }

    public boolean isRejected() {
        return getState() == REJECTED;
    }

    public abstract Promise<R> done(OnDone<R> onDone);

    public abstract Promise<R> fail(OnFail onFail);

    public abstract Promise<R> always(OnAlways<R> onAlways);

    public interface OnDone<R> {
        void onDone(R result);
    }

    public interface OnFail {
        void onFail(Throwable error);
    }

    public interface OnAlways<R> {
        void onAlways(boolean resolved, R result, Throwable error);
    }
}
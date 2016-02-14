package uz.greenwhite.lib.job;

import uz.greenwhite.lib.job.internal.PromiseTemplate;

public class DelayedPromise<R> extends PromiseTemplate<R> {

    private final Promise<R> promise;
    private BindCallback boundCallback;

    public DelayedPromise(Promise<R> promise) {
        this.promise = promise;
    }

    @Override
    public int getState() {
        return promise.getState();
    }

    @Override
    public R getResult() {
        return promise.getResult();
    }

    @Override
    public Throwable getError() {
        return promise.getError();
    }

    public void rush() {
        if (boundCallback == null) {
            boundCallback = new BindCallback();
            promise.done(boundCallback);
            promise.fail(boundCallback);
        }
    }

    private class BindCallback implements OnDone<R>, OnFail {

        @Override
        public void onDone(R result) {
            notifyCallbacks();
        }

        @Override
        public void onFail(Throwable error) {
            notifyCallbacks();
        }

    }
}

package uz.greenwhite.lib.job;

import uz.greenwhite.lib.job.internal.PromiseTemplate;

public class Deferred<R> extends PromiseTemplate<R> {

    private int state = PENDING;
    private R result;
    private Throwable error;

    @Override
    public int getState() {
        return state;
    }

    @Override
    public R getResult() {
        if (state == RESOLVED) {
            return result;
        } else {
            throw new IllegalStateException();
        }
    }

    @Override
    public Throwable getError() {
        if (state == REJECTED) {
            return error;
        } else {
            throw new IllegalStateException();
        }
    }

    public void resolve(R result) {
        if (this.state == PENDING) {
            this.state = RESOLVED;
            this.result = result;
            notifyCallbacks();
        } else {
            throw new IllegalStateException();
        }
    }

    public void reject(Throwable error) {
        if (this.state == PENDING) {
            this.state = REJECTED;
            this.error = error;
            notifyCallbacks();
        } else {
            throw new IllegalStateException();
        }
    }

    public Promise<R> promise() {
        return new Promise<R>() {
            @Override
            public int getState() {
                return Deferred.this.getState();
            }

            @Override
            public R getResult() {
                return Deferred.this.getResult();
            }

            @Override
            public Throwable getError() {
                return Deferred.this.getError();
            }

            @Override
            public Promise<R> done(OnDone<R> onDone) {
                Deferred.this.done(onDone);
                return this;
            }

            @Override
            public Promise<R> fail(OnFail onFail) {
                Deferred.this.fail(onFail);
                return this;
            }

            @Override
            public Promise<R> always(OnAlways<R> onAlways) {
                Deferred.this.always(onAlways);
                return this;
            }
        };
    }

}

package uz.greenwhite.lib.job.internal;

import java.util.ArrayList;
import java.util.List;

import uz.greenwhite.lib.job.Promise;

public abstract class PromiseTemplate<R> extends Promise<R> {

    private List<OnDone<R>> doneCallbacks;
    private List<OnFail> failCallbacks;
    private List<OnAlways<R>> alwaysCallbacks;

    @Override
    public Promise<R> done(OnDone<R> onDone) {
        switch (getState()) {
            case PENDING:
                if (doneCallbacks == null) {
                    doneCallbacks = new ArrayList<OnDone<R>>(1);
                }
                doneCallbacks.add(onDone);
                break;

            case RESOLVED:
                if (doneCallbacks != null) {
                    throw new IllegalStateException();
                }
                onDone.onDone(getResult());
                break;
        }
        return this;
    }

    @Override
    public Promise<R> fail(OnFail onFail) {
        switch (getState()) {
            case PENDING:
                if (failCallbacks == null) {
                    failCallbacks = new ArrayList<OnFail>(1);
                }
                failCallbacks.add(onFail);
                break;

            case REJECTED:
                if (failCallbacks != null) {
                    throw new IllegalStateException();
                }
                onFail.onFail(getError());
                break;
        }
        return this;
    }

    @Override
    public Promise<R> always(OnAlways<R> onAlways) {
        switch (getState()) {
            case PENDING:
                if (alwaysCallbacks == null) {
                    alwaysCallbacks = new ArrayList<OnAlways<R>>(1);
                }
                alwaysCallbacks.add(onAlways);
                break;

            default:
                if (alwaysCallbacks != null) {
                    throw new IllegalStateException();
                }
                onAlways.onAlways(getState() == RESOLVED, getResult(), getError());
                break;
        }
        return this;
    }

    private void triggerDone() {
        R result = getResult();
        if (doneCallbacks != null) {
            for (OnDone<R> c : doneCallbacks) {
                c.onDone(result);
            }
        }
    }

    private void triggerFail() {
        Throwable error = getError();
        if (failCallbacks != null) {
            for (OnFail c : failCallbacks) {
                c.onFail(error);
            }
        }
    }

    private void triggerAlways() {
        boolean resolved = getState() == RESOLVED;
        R result = resolved ? getResult() : null;
        Throwable error = resolved ? null : getError();
        if (alwaysCallbacks != null) {
            for (OnAlways<R> c : alwaysCallbacks) {
                c.onAlways(resolved, result, error);
            }
        }
    }

    private void clearCallbacks() {
        doneCallbacks = null;
        failCallbacks = null;
        alwaysCallbacks = null;
    }

    protected void notifyCallbacks() {
        switch (getState()) {
            case RESOLVED:
                triggerDone();
                break;
            case REJECTED:
                triggerFail();
                break;
            default:
                throw new IllegalStateException();
        }
        triggerAlways();
        clearCallbacks();
    }
}
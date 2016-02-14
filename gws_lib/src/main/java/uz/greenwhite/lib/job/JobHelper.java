package uz.greenwhite.lib.job;

import android.app.Activity;
import android.app.ProgressDialog;

import uz.greenwhite.lib.job.internal.Manager;

public class JobHelper {

    private final Object TAG = new Object();

    @SuppressWarnings("unchecked")
    public <R> Promise<R> execute(Job<R> job) {
        Deferred<R> deferred = new Deferred<R>();
        Manager.getInstance().execute((Job<Object>)job, (Deferred<Object>) deferred, TAG);
        return deferred.promise();
    }

    public void stopListening() {
        Manager.getInstance().stopListening(TAG);
    }

    public JobParallel parallel() {
        return new JobParallel(TAG);
    }

    public <P, R> void listenKey(String key, JobListener<P, R> jobListener) {
        Manager.getInstance().listenKey(key, jobListener, TAG);
    }

    public <R> Promise<R> executeWithDialog(Activity activity, Job<R> job) {
        final ProgressDialog pd = new ProgressDialog(activity);
        pd.setMessage(activity.getString(uz.greenwhite.lib.R.string.please_wait));
        pd.setCancelable(false);
        pd.show();
        Deferred<R> deferred = new Deferred<R>();
        deferred.always(new Promise.OnAlways<R>() {
            @Override
            public void onAlways(boolean resolved, Object success, Throwable error) {
                pd.dismiss();
            }
        });
        Manager.getInstance().execute(job, deferred, TAG);
        return deferred.promise();
    }
}

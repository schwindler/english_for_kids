package uz.greenwhite.lib.job;

public interface JobListener<P, R> {

    void onStart();

    void onStop();

    void onProgress(P progress);

    void onSuccess(R result);

    void onFailure(Throwable error);

}

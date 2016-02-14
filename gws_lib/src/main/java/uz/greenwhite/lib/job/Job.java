package uz.greenwhite.lib.job;

public interface Job<R> {

    R execute() throws Exception;

}

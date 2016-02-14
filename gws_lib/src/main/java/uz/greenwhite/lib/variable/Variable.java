package uz.greenwhite.lib.variable;

public interface Variable {

    void readyToChange();

    boolean modified();

    ErrorResult getError();

}

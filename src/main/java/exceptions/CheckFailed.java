package exceptions;

public class CheckFailed extends RuntimeException {
    public CheckFailed(String s) {
        super(s);
    }
}

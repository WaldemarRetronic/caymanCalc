package pl.valdemar.wire;

/**
 * Created by ZacznijProgramowac.
 * https://www.youtube.com/zacznijprogramowac
 * http://zacznijprogramowac.net/
 */
public class ApplicationException extends RuntimeException {

    private String message;

    public ApplicationException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return "to jest my: " + message;
    }
}

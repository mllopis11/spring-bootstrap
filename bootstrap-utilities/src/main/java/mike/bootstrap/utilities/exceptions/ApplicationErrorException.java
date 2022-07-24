package mike.bootstrap.utilities.exceptions;

/**
 * ApplicationErrorException represents non recoverable exceptions and are not supposed to be
 * handled by the caller.
 * 
 * @author Mike (2021-02)
 *
 */
public class ApplicationErrorException extends RuntimeException {

    private static final long serialVersionUID = -7713170588610020648L;

    /**
     * @param message understandable contextual message
     * @param args    optional message arguments
     */
    public ApplicationErrorException(String message, Object... args) {
        super(String.format(message, args));
    }

    /**
     * @param throwable root cause of the exception
     * @param message   understandable contextual message
     * @param args      optional message arguments
     */
    public ApplicationErrorException(Throwable throwable, String message, Object... args) {
        super(String.format(message, args), throwable);
    }
}

package mike.bootstrap.utilities.exceptions;

/**
 * ApplicationException represents a functional or technical (typically IO error) exception and is
 * supposed to be recoverable exceptions.<br>
 * Developers can catch this kind of exception if necessary.
 * 
 * @author Mike (2021-02)
 *
 */
public class ApplicationException extends RuntimeException {

    private static final long serialVersionUID = 8936811799738970924L;

    /**
     * @param message understandable contextual message
     * @param args    optional message arguments
     */
    public ApplicationException(String message, Object... args) {
        super(String.format(message, args));
    }

    /**
     * @param throwable root cause of the exception
     * @param message   understandable contextual message
     * @param args      optional message arguments
     */
    public ApplicationException(Throwable throwable, String message, Object... args) {
        super(String.format(message, args), throwable);
    }
}

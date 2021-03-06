package mike.bootstrap.utilities.exceptions;

/**
 * 
 * @author Mike (2021-03)
 */
public class ResourceNotAvailableException extends RuntimeException {

	private static final long serialVersionUID = -4003755746010422420L;

	/**
     * @param message understandable contextual message
     * @param args    optional message arguments
     */
    public ResourceNotAvailableException(String message, Object... args) {
        super(String.format(message, args));
    }
    
    /**
     * @param throwable root cause of the exception
     * @param message   understandable contextual message
     * @param args      optional message arguments
     */
    public ResourceNotAvailableException(Throwable throwable, String message, Object... args) {
        super(String.format(message, args), throwable);
    }
}

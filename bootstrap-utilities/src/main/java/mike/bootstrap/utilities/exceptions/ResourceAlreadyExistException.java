package mike.bootstrap.utilities.exceptions;

/**
 * The resource already exists. 
 * 
 * @author Mike (2021-03)
 */
public class ResourceAlreadyExistException extends RuntimeException {

	private static final long serialVersionUID = -2544685617695879958L;

	/**
     * @param message understandable contextual message
     * @param args    optional message arguments
     */
    public ResourceAlreadyExistException(String message, Object... args) {
        super(String.format(message, args));
    }
    
    /**
     * @param throwable root cause of the exception
     * @param message   understandable contextual message
     * @param args      optional message arguments
     */
    public ResourceAlreadyExistException(Throwable throwable, String message, Object... args) {
        super(String.format(message, args), throwable);
    }
}

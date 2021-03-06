package mike.bootstrap.utilities.exceptions;

/**
 * 
 * @author Mike (2021-03)
 */
public class ResourceNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -7246889878412038163L;

	/**
     * @param message understandable contextual message
     * @param args    optional message arguments
     */
    public ResourceNotFoundException(String message, Object... args) {
        super(String.format(message, args));
    }
    
    /**
     * @param throwable root cause of the exception
     * @param message   understandable contextual message
     * @param args      optional message arguments
     */
    public ResourceNotFoundException(Throwable throwable, String message, Object... args) {
        super(String.format(message, args), throwable);
    }
}

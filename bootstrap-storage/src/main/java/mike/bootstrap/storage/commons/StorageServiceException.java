package mike.bootstrap.storage.commons;


/**
 * ApplicationException represents a functional or technical (typically IO error) exception and is
 * supposed to be recoverable exceptions.<br>
 * Developers can catch this kind of exception if necessary.
 *  
 * @author Mike (2022-08)
 *
 */
public class StorageServiceException extends RuntimeException {

    private static final long serialVersionUID = -3025768171384823650L;

    /**
     * @param message understandable contextual message
     * @param args    optional message arguments
     */
    public StorageServiceException(String message, Object... args) {
        super(String.format(message, args));
    }

    /**
     * @param throwable root cause of the exception
     * @param message   understandable contextual message
     * @param args      optional message arguments
     */
    public StorageServiceException(Throwable throwable, String message, Object... args) {
        super(String.format(message, args), throwable);
    }
}

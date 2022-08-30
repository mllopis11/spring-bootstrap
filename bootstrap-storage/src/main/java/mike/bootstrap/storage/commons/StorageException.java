package mike.bootstrap.storage.commons;


/**
 * StorageException represents non recoverable exceptions and are not supposed to be
 * handled by the caller.
 *  
 * @author Mike (2022-08)
 *
 */
public class StorageException extends RuntimeException {

    private static final long serialVersionUID = -3025768171384823650L;

    /**
     * @param message understandable contextual message
     * @param args    optional message arguments
     */
    public StorageException(String message, Object... args) {
        super(String.format(message, args));
    }

    /**
     * @param throwable root cause of the exception
     * @param message   understandable contextual message
     * @param args      optional message arguments
     */
    public StorageException(Throwable throwable, String message, Object... args) {
        super(String.format(message, args), throwable);
    }
}

package mike.bootstrap.utilities.helpers;

/**
 * General utility methods
 * 
 * @author Mike (2021-02)
 * @since 1.8
 */
public class Utils {

    /**
     * Private constructor (Prevent any instantiation)
     */
    private Utils() {}

    /**
     * @param port               network port
     * @param includeSystemPorts include the system port range (between 0 and 1023). Default is
     *                           false.
     * @return true the port number is valid
     */
    public static boolean isPortValid(int port, boolean... systemPorts) {
        var include = systemPorts.length > 0 ? systemPorts[0] : Boolean.FALSE;
        return port >= (include ? 0 : 1024) && port <= 65535;
    }

    /**
     * @param port               network port
     * @param includeSystemPorts include the system port range (between 0 and 1023). Default is
     *                           false.
     * @return true the port number is not valid
     * @see Utils#isPortValid(int, boolean...)
     */
    public static boolean isPortNotValid(int port, boolean... includeSystemPorts) {
        return !Utils.isPortValid(port, includeSystemPorts);
    }

    /**
     * @param value the value to check
     * @param min   the minimum value (inclusive)
     * @param max   the maximum value (inclusive)
     * @return true if the value is greater-equal the minimum value and less-equal the maximum value
     *         otherwise false
     */
    public static boolean between(int value, int min, int max) {
        return value >= min && value <= max;
    }

    public static <T> T nullAs(T object, T defObject) {

        if (defObject != null) {
            return object != null ? object : defObject;
        } else {
            throw new IllegalArgumentException("Utils: no such default object");
        }
    }
}

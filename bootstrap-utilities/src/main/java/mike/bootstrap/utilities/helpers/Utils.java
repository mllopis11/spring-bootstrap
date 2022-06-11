package mike.bootstrap.utilities.helpers;

import org.apache.commons.lang3.StringUtils;

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
     * @return line of the 80 characters
     */
    public static String lineOf(char ch) {
	return lineOf(ch, 80);
    }

    /**
     * @return line of the character with the provided size
     */
    public static String lineOf(char ch, int size) {
	return StringUtils.repeat(ch, size);
    }

    /**
     * @param port               network port
     * @param includeSystemPorts include the system port range (between 0 and 1023).
     *                           Default is false.
     * @return true the port number is valid
     */
    public static boolean isPortValid(int port, boolean... includeSystemPorts) {
	var include = includeSystemPorts.length > 0 ? includeSystemPorts[0] : Boolean.FALSE;
	return port >= (include ? 0 : 1024) && port <= 65535;
    }

    /**
     * @param port               network port
     * @param includeSystemPorts include the system port range (between 0 and 1023).
     *                           Default is false.
     * @return true the port number is not valid
     * @see Utils#isPortValid(int, boolean...)
     */
    public static boolean isPortNotValid(int port, boolean... includeSystemPorts) {
	return !Utils.isPortValid(port, includeSystemPorts);
    }

    /**
     * Extract value from key/value pair (i.e.: key=value)
     * 
     * @param arg      key/value pair
     * @param defValue default value if value is not present
     * @return the argument value or an empty string if not set
     */
    public static String getArgv(String arg, String... defValue) {
	var items = arg != null ? arg.split("=", 2) : new String[] {};
	return items.length == 2 && !items[0].isBlank() && !items[1].isBlank() ? items[1].trim()
		: Utils.defautValue(defValue);
    }

    /**
     * Extract value from key/value pair (i.e.: key=value)
     * 
     * @param arg      key/value pair
     * @param defValue default value if value is not present
     * @return the argument value
     * @see Utils#getArgv(String, String...)
     * @see Utils#toInteger(String, int...)
     */
    public static int getArgv(String arg, int defValue) {
	var str = Utils.getArgv(arg);
	return Utils.toInteger(str, defValue);
    }

    /**
     * @param value    value to strip
     * @param defValue default value if value is null
     * @return value with any leading and trailing whitespace or empty string if
     *         null
     */
    public static String strip(String value, String... defValue) {
	var str = value != null ? value.strip() : "";
	return !str.isEmpty() ? str : Utils.defautValue(defValue);
    }

    /**
     * @param value    number to convert
     * @param defValue default value if value is null or not a number (default is 0)
     * @return the converted value or in case of any error occurs during conversion,
     *         the default value otherwise a NumberFormatException is thrown
     * @throws NumberFormatException if a conversion error occurs and no default
     *                               value is provided
     */
    public static int toInteger(String value, int... defValue) {
	try {
	    return Integer.parseInt(Utils.strip(value));
	} catch (NumberFormatException nfe) {
	    return defValue.length > 0 ? defValue[0] : 0;
	}
    }

    /**
     * @param value    number to convert
     * @param defValue default value if value is null or not a number (default is 0)
     * @return the converted value or in case of any error occurs during conversion,
     *         the default value otherwise a NumberFormatException is thrown
     * @throws NumberFormatException if a conversion error occurs and no default
     *                               value is provided
     */
    public static long toLong(String value, long... defValue) {
	try {
	    return Long.parseLong(Utils.strip(value));
	} catch (NumberFormatException nfe) {
	    return defValue.length > 0 ? defValue[0] : 0;
	}
    }

    /**
     * @param value the value to check
     * @param min   the minimum value (inclusive)
     * @param max   the maximum value (inclusive)
     * @return true if the value is greater-equal the minimum value and less-equal
     *         the maximum value otherwise false
     */
    public static boolean between(int value, int min, int max) {
	return value >= min && value <= max;
    }

    /**
     * @param defValue optional default value
     * @return return the default value if present otherwise an empty value
     */
    private static String defautValue(String... defValue) {
	return defValue.length > 0 && defValue[0] != null ? defValue[0] : "";
    }
}

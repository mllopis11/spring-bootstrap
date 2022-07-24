package mike.bootstrap.utilities.helpers;

import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

public interface Strings {

    public static final String EMPTY = "";
    public static final String SPACE = " ";
    public static final Pattern REGEX_SPACES = Pattern.compile("\\s+");
    public static final Pattern REGEX_LINE_FEED = Pattern.compile("\\r?\\n");

    /**
     * @param value    The original value (may be null)
     * @param defValue Optional default value if original is null (default: empty string)
     * @return The resulting {@code String} or the default value if present.
     * @see Strings#nullAs(Object, String, String...)
     */
    public static String nullAs(String value, String... defValue) {
        return value != null ? value : Strings.defaultValue(defValue);
    }

    /**
     * @param value    The original value (may be null)
     * @param defValue Optional default value if original is null, empty or blank (default: empty
     *                 string)
     * @return The resulting {@code String} or the default value if present.
     */
    public static String blankAs(String value, String... defValue) {
        return value != null && !value.isBlank() ? value : Strings.defaultValue(defValue);
    }

    /**
     * Remove all leading and trailing white space.
     * 
     * @param str      String to strip (may be null)
     * @param defValue Optional default value if original is null (default: empty string)
     * @return The resulting {@code String} without leading and trailing whitespace or the default
     *         value if present.
     * @see String#strip()
     */
    public static String strip(String str, String... defValue) {
        var val = str != null ? str.strip() : EMPTY;
        return !val.isEmpty() ? val : Strings.defaultValue(defValue);
    }

    /**
     * Removes all newline(s) from a String
     * 
     * @return string value instance
     */
    public static String chomp(String str) {
        return sanitize(str, REGEX_LINE_FEED, " ");
    }

    /**
     * Remove with all white space.
     * 
     * <pre>
     * StringValue.shrink("  My   Value    ") => "MyValue"
     * </pre>
     * 
     * @return string value instance
     */
    public static String shrink(String value) {
        return sanitize(value, Strings.EMPTY);
    }

    /**
     * Replace all repeating spaces with a single space. Call {@link StringValue#strip()} first if
     * the leading and trailing must be removed.
     * 
     * <pre>
     * StringValue.sanitize("  My   Value    ")  => "  My Value    "
     * </pre>
     * 
     * @return string value instance
     */
    public static String sanitize(String value) {
        return sanitize(value, Strings.SPACE);
    }

    /**
     * Replace all repeating spaces with the replacement {@code String}.
     * 
     * @param replacement The replacement string
     * @return string value instance
     * @see StringValue#sanitize(Pattern, String)
     */
    public static String sanitize(String value, String replacement) {
        return sanitize(value, REGEX_SPACES, replacement);
    }

    /**
     * Replaces every subsequence of the input sequence that matches the pattern with the given
     * replacement string.
     * 
     * @param pattern     The matching pattern
     * @param replacement The replacement string (default: single white space)
     * @return string value instance
     */
    public static String sanitize(String value, Pattern pattern, String... replacement) {
        var rc = Strings.defaultValue(replacement);
        return pattern.matcher(value).replaceAll(rc);
    }

    /**
     * Gets a substring from the specified String avoiding exceptions.
     * 
     * @param value  the original value (may be null)
     * @param offset the substring offset
     * @return the resulting value.
     * @see Strings#offset(String, int, int)
     */
    public static String offset(String value, Offset offset) {
        return StringUtils.substring(value, offset.startAt(), offset.endAt());
    }

    /**
     * Gets a substring from the specified String avoiding exceptions.
     * 
     * @param value   the original value (may be null)
     * @param startAt the position to start from (min.: 0)
     * @param endAt   the position to end at (exclusive, min. 1)
     * @return the resulting value.
     * @see StringUtils#substring(String, int, int)
     */
    public static String offset(String value, int startAt, int endAt) {
        return StringUtils.substring(value, startAt, endAt);
    }

    /**
     * Extract value from key/value pair (i.e.: key=value)
     * 
     * <pre>
     * Strings.getArgv(key1=value1) => "value1"
     * </pre>
     * 
     * @param arg      key/value pair
     * @param defValue default value if value is not present
     * @return the argument value or an empty string if not set
     */
    public static String getArgv(String arg, String... defValue) {
        var items = arg != null ? arg.split("=", 2) : new String[] {};
        return items.length == 2 && !items[0].isBlank() && !items[1].isBlank() ? items[1].strip()
                : Strings.defaultValue(defValue);
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
        var str = Strings.getArgv(arg);
        return Strings.toInteger(str, defValue);
    }

    /**
     * @param value    number to convert
     * @param defValue default value if value is null or not a number (default is 0)
     * @return the converted value or in case of any error occurs during conversion, the default
     *         value otherwise a NumberFormatException is thrown
     * @throws NumberFormatException if a conversion error occurs and no default value is provided
     */
    public static int toInteger(String value, int... defValue) {

        try {
            return Integer.parseInt(Strings.strip(value));
        } catch (NumberFormatException nfe) {
            return defValue.length > 0 ? defValue[0] : 0;
        }
    }

    /**
     * @param value    number to convert
     * @param defValue default value if value is null or not a number (default is 0)
     * @return the converted value or in case of any error occurs during conversion, the default
     *         value otherwise a NumberFormatException is thrown
     * @throws NumberFormatException if a conversion error occurs and no default value is provided
     */
    public static long toLong(String value, long... defValue) {

        try {
            return Long.parseLong(Strings.strip(value));
        } catch (NumberFormatException nfe) {
            return defValue.length > 0 ? defValue[0] : 0;
        }
    }

    /**
     * @param defValue optional default value
     * @return return the default value if present otherwise an empty value
     */
    private static String defaultValue(String... defValue) {
        return defValue.length > 0 && defValue[0] != null ? defValue[0] : EMPTY;
    }
}

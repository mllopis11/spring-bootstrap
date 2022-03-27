package mike.bootstrap.utilities.helpers;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Simple console logger.
 * 
 * @author Mike (2021-02)
 */
public class Print {

    private static boolean timestamp = false;
    private static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    private Print() {
    }

    /**
     * Toggle timestamp on message
     */
    public static void timestamp() {
	timestamp = !timestamp;
    }

    /**
     * Set timestamp format
     * 
     * @param format timestamp format
     */
    public static void timestamp(DateTimeFormatter format) {
	dtf = format;
    }

    /**
     * Set timestamp format. If an invalid format is provided, the default format is
     * kept.
     * 
     * @param format timestamp format
     */
    public static void timestamp(String format) {
	try {
	    dtf = DateTimeFormatter.ofPattern(format);
	} catch (IllegalArgumentException e) {
	    /* Ignore: keep default format */ }
    }

    /**
     * Print simple message
     * 
     * @param o object to print
     */
    public static void out(Object o) {
	System.out.println(o);
    }

    /**
     * Print simple message
     * 
     * @param format message format
     * @param arg    message arguments
     */
    public static void out(String format, Object... arg) {
	System.out.println(String.format(format, arg));
    }

    /**
     * Print information message
     * 
     * @param o object to print
     */
    public static void info(Object o) {
	Print.out(prefix("INFO :  ") + o);
    }

    /**
     * Print information message
     * 
     * @param format message format
     * @param arg    message arguments
     */
    public static void info(String format, Object... arg) {
	Print.out(prefix("INFO :  ") + format, arg);
    }

    /**
     * Print warning message
     * 
     * @param o object to print
     */
    public static void warn(Object o) {
	Print.out(prefix("WARN :  ") + o);
    }

    /**
     * Print warning message
     * 
     * @param format message format
     * @param arg    message arguments
     */
    public static void warn(String format, Object... arg) {
	Print.out(prefix("WARN :  ") + format, arg);
    }

    /**
     * Print error message
     * 
     * @param o object to print
     */
    public static void error(Object o) {
	Print.out(prefix("ERROR:  ") + o);
    }

    /**
     * Print error message
     * 
     * @param format message format
     * @param arg    message arguments
     */
    public static void error(String format, Object... arg) {
	Print.out(prefix("ERROR:  ") + format, arg);
    }

    /**
     * Print fatal message
     * 
     * @param o object to print
     */
    public static void fatal(Object o) {
	Print.out(prefix("FATAL:  ") + o);
    }

    /**
     * Print fatal message
     * 
     * @param format message format
     * @param arg    message arguments
     */
    public static void fatal(String format, Object... arg) {
	Print.out(prefix("FATAL:  ") + format, arg);
    }

    /**
     * Set message prefix
     * 
     * @param level message level
     * @return message prefix
     */
    private static String prefix(String level) {
	return timestamp ? dtf.format(ZonedDateTime.now()) + " - " + level : level;
    }
}

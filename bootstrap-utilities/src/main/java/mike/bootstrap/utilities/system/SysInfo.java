package mike.bootstrap.utilities.system;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import java.util.Locale;
import java.util.stream.Stream;

import mike.bootstrap.utilities.exceptions.ApplicationErrorException;
import mike.bootstrap.utilities.helpers.Utils;

/**
 * Provides general informations about the system (pid, hostname ...) and
 * shortcuts for the JVM properties.
 * 
 * @author Mike (2021-02)
 * @since 1.8
 *
 */
public class SysInfo {

    /* *** JVM system variable shortcuts *** */
    public static final String USER_NAME = System.getProperty("user.name", "anonymous");
    public static final String USER_HOME = System.getProperty("user.home");
    public static final String USER_DIR = System.getProperty("user.dir");
    public static final String TMP_DIR = System.getProperty(USER_HOME + "/tmp", "java.io.tmpdir");
    public static final String OS_NAME = System.getProperty("os.name");
    public static final String FILE_SEP = System.getProperty("file.separator");
    public static final String LINE_FEED = System.getProperty("line.separator");

    /* ***** System info ***** */
    private static Class<?> mainClazz;
    private static String hostIP = "0.0.0.0";
    private static String hostname = "unknown";
    private static String machine = "unknown";
    private static String localhost = "localhost";
    private static String pid = "0";

    static {
	// Set default locale
	Locale.setDefault(Locale.ENGLISH);

	/* *** Process informations *** */
	String[] runtime = ManagementFactory.getRuntimeMXBean().getName().split("@", 2);
	pid = runtime.length >= 1 ? runtime[0] : "0";

	try {
	    InetAddress address = InetAddress.getLocalHost();
	    hostname = address.getCanonicalHostName();
	    hostIP = address.getHostAddress();
	    localhost = InetAddress.getByName(null).getHostAddress();
	    machine = hostname.contains(".") ? hostname.split("\\.")[0] : hostname;
	} catch (UnknownHostException e) {
	    /* Ignore */ }
    }

    private SysInfo() {
    }

    public static void setLocale(Locale locale) {
	Locale.setDefault(locale);
    }

    /**
     * Set main class informations. If the method is invoked multiple time, any
     * action is done.
     * 
     * @param clazz main clazz
     * @throws ClassCastException if the class does not contain a main method
     */
    public static void setMainClass(Class<?> clazz) {
	if (mainClazz == null) {
	    if (Stream.of(clazz.getMethods()).anyMatch(m -> m.getName().equals("main"))) {
		mainClazz = clazz;
	    }

	    if (mainClazz == null) {
		throw new ClassCastException(
			"SysInfo::setMainClass: no such 'main' method for class: " + clazz.getCanonicalName());
	    }
	}
    }

    /**
     * @return canonical name of the main class or an empty string if not set
     */
    public static String mainClass() {
	return mainClazz != null ? mainClazz.getCanonicalName() : "";
    }

    /**
     * @return package name of the main class or an empty string if not set
     */
    public static String mainClassPackage() {
	return mainClazz != null ? mainClazz.getPackage().getName() : "";
    }

    /**
     * @return name of the main class or an empty string if not set
     */
    public static String mainClassName() {
	return mainClazz != null ? mainClazz.getSimpleName() : "";
    }

    /**
     * @return true if system property "os.name" is LINUX, SUNOS or SOLARIS
     */
    public static boolean isUnix() {
	return OS_NAME.toLowerCase().startsWith("linux") || OS_NAME.toLowerCase().startsWith("sunos")
		|| OS_NAME.toLowerCase().startsWith("solaris");
    }

    /**
     * @return true if system property "os.name" is WINDOWS
     */
    public static boolean isWindows() {
	return OS_NAME.toLowerCase().startsWith("windows");
    }

    /**
     * @return some system informations (platform architecture, versions, user)
     */
    public static String platform() {
	return String.format("Platform: os=%s, arch=%s (%d cores), jre-verion=%s, username=%s", OS_NAME,
		System.getProperty("os.arch"), SysInfo.cores(), System.getProperty("java.specification.version"),
		USER_NAME);
    }

    /**
     * @return number of processors available to the Java virtual machine.
     * @see java.lang.Runtime#availableProcessors()
     */
    public static int cores() {
	return Runtime.getRuntime().availableProcessors();
    }

    /**
     * @return PID of the process (JVM)
     */
    public static String pid() {
	return pid;
    }

    /**
     * @return canonical host name
     */
    public static String hostname() {
	return hostname;
    }

    /**
     * @return host address
     */
    public static String hostIP() {
	return hostIP;
    }

    /**
     * @return local host address (127.0.0.1)
     */
    public static String localhost() {
	return localhost;
    }

    /**
     * @return host short name
     */
    public static String machine() {
	return machine;
    }

    /**
     * Check if the host address is already bound
     * 
     * @param hostname host to test
     * @param port     port to test
     * @throws ApplicationUncheckedException if the port is invalid or the address
     *                                       is not available
     */
    public static void hostAddressAlreadyBound(String hostname, String port) {

	String myHost = Utils.trim(hostname);
	var myPort = Utils.toInteger(port, 0);

	if (myHost.isBlank() || Utils.isPortNotValid(myPort)) {
	    throw new ApplicationErrorException("bindHostAddress: invalid hostname or port argument");
	}

	try (var socket = new ServerSocket(myPort, 0, InetAddress.getByName(hostname));) {
	    // Nothing to do here: Address is free
	} catch (IOException ioe) {
	    throw new ApplicationErrorException("bindHostAddress: address=%s:%s, causedBy: %s", hostname, port,
		    ioe.getMessage());
	}
    }
}

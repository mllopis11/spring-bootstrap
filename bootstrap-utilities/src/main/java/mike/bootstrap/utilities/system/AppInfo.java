package mike.bootstrap.utilities.system;

import java.io.IOException;
import java.util.Locale;
import java.util.stream.Stream;

import mike.bootstrap.utilities.helpers.Dates;
import mike.bootstrap.utilities.helpers.Print;
import mike.bootstrap.utilities.helpers.StringValue;
import mike.bootstrap.utilities.helpers.Timer;
import mike.bootstrap.utilities.nio.files.Resource;

/**
 * Provide general information the application (name, version, runtime-directory ...)
 * <p>
 * Application informations are read from "META-INF/build-info.properties". If
 * you are using MAVEN, the properties <i>application.name</i> and
 * <i>organization.name</i> should be set in your POM.
 * 
 * @author Mike (2021-02)
 * @since 15
 */
public class AppInfo {

    /* *** Application Properties Keywords *** */
    public static final String KW_APP_NODE = "container.node";
    public static final String KW_APP_WEBAPP = "container.webapp"; // Application
    public static final String KW_APP_RUNDIR = "container.runtime-basedir"; // Runtime base directory
    public static final String KW_APP_BUILD_INFO = "build.info.properties.file";

    /* *** Application info *** */
    private static String node = "local";
    private static String name = "WhiteApp";
    private static String module = "MyModule";
    private static String version = "x.x";
    private static String company = "My World Company";
    private static String release = Dates.zNow();

    private static String runtimeBaseDirectory = "./run/local";

    private static Timer tm = new Timer();

    /**
     * Constructor (prevent any instantiation)
     */
    private AppInfo() {
    }

    /* ***** Populate variables ***** */
    static {

	// Set JVM Locale to English
	Locale.setDefault(Locale.ENGLISH);

	node = StringValue.of(System.getProperty(KW_APP_NODE, "local")).sanitize("-").value().toLowerCase();
	runtimeBaseDirectory = System.getProperty(KW_APP_RUNDIR, String.format("./run/%s", node));

	/*
	 * *** Get application informations from Maven "built-info properties" file
	 * within the JAR
	 */
	final String buildInfoFile = System.getProperty(KW_APP_BUILD_INFO, "BOOT-INF/classes/build-info.properties");

	Stream.of(buildInfoFile, "META-INF/build-info.properties").map(Resource::of).filter(Resource::exists)
		.findFirst().ifPresentOrElse(res -> {
		    try {
			var infos = res.getProperties();
			release = infos.getProperty("build.time", release);
			name = infos.getProperty("build.application.name", name);
			module = infos.getProperty("build.module.name", module);
			version = infos.getProperty("build.version", version);
			company = infos.getProperty("build.organization.name", company);
		    } catch (IOException e) {
			Print.warn("AppInfo: cannot access to resource: %s. continuing ...", buildInfoFile);
		    }
		}, AppInfo::setMainClassName);
    }

    /* ***** Getter methods ***** */
    /**
     * @return application name
     */
    public static String name() {
	return name;
    }

    /**
     * @return application module
     */
    public static String module() {
	return module;
    }

    /**
     * @return version of the application
     */
    public static String version() {
	return version;
    }

    /**
     * @return application node (system variable: container.node) or 'local' if not
     *         set
     */
    public static String node() {
	return node;
    }

    /**
     * @return ISO_DATE_TIME compile timestamp
     */
    public static String releaseDttm() {
	return release;
    }

    /**
     * @return company name
     */
    public static String company() {
	return company;
    }

    /**
     * @return true if the system property 'container.webapp' is set to true
     */
    public static boolean isWebApplication() {
	return Boolean.parseBoolean(System.getProperty(KW_APP_WEBAPP, "false"));
    }

    /**
     * Returns the runtime directory specified by the property
     * 'container.run-directory'. If the property is not set, returns the default
     * directory './run/${node}'.
     * 
     * @return application runtime root directory
     */
    public static String runtimeDirectory() {
	return runtimeBaseDirectory;
    }

    /**
     * @return process as 'name':'pid'@'machine'
     */
    public static String process() {
	return name + ":" + SysInfo.pid() + "@" + SysInfo.machine();
    }

    /**
     * @return application banner (name, version, company, release date)
     */
    public static String banner() {
	return String.format("%s - %s %s (copyrights: %s) - released: %s", name(), module(), version(), company,
		release);
    }

    /**
     * @return starter message
     */
    public static String starter() {
	return String.format("%s (node: %s) started with pid %s on %s at %s", module(), node(), SysInfo.pid(),
		SysInfo.hostname(), Dates.zNow());
    }

    /**
     * @return completion message
     */
    public static String footer() {
	String uptime = isWebApplication() ? tm.upTime() : tm.elapsTime();
	return String.format("%s (node: %s) terminated at %s (uptime: %s)", module(), node(), Dates.zNow(), uptime);
    }

    /**
     * @return uptime
     * @see mike.commons.utilities.Timer#upTime()
     */
    public static String uptime() {
	return tm.upTime();
    }

    /*
     * ##### Private methods ######################################################
     */

    /**
     * Try to extract the application name from the command line
     */
    private static void setMainClassName() {
	try {
	    String[] command = System.getProperty("sun.java.command", name).split(" ");
	    name = Class.forName(command[0]).getSimpleName();
	} catch (ClassNotFoundException e) {
	    /* Ignore */ }
    }
}

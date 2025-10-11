package mike.bootstrap.app;

/**
 * Run spring-boot application
 * 
 * @author Mike
 */
public class Application {

    public static final String BOOT_APP_PACKAGE = "mike.bootstrap.app";
    public static final String BOOT_API_PACKAGE = "mike.bootstrap.api";

    private Application() {}

    public static void batch(Class<?> clazz, String[] args) {
        ApplicationBootstrap.batch(clazz, args).run(args);
    }

    public static void servlet(Class<?> clazz, String[] args) {
        ApplicationBootstrap.servlet(clazz, args).run(args);
    }
}

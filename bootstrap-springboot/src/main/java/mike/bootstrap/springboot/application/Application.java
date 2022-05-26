package mike.bootstrap.springboot.application;

/**
 * Run spring-boot application
 * 
 * @author Mike
 */
public class Application {

    public static final String BOOT_BASE_PACKAGE = "mike.bootstrap.springboot";

    private Application() {}

    public static void batch(Class<?> clazz, String[] args) {
        ApplicationBootstrap.batch(clazz, args).run(args);
    }

    public static void servlet(Class<?> clazz, String[] args) {
        ApplicationBootstrap.servlet(clazz, args).run(args);
    }
}

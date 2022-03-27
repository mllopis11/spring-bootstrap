package mike.bootstrap.springboot.application;

/**
 * Run spring-boot application
 * 
 * @author Mike
 */
public class Application {

    public static final String BOOT_BASE_PACKAGE = "mike.bootstrap.springboot";

    private Application() {}

    public static void standalone(Class<?> clazz, String[] args) {
	ApplicationBootstrap.standalone(clazz, args).run(args);
    }

    public static void servlet(Class<?> clazz, String[] args) {
	ApplicationBootstrap.servlet(clazz, args).run(args);
    }
}

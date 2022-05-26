package mike.bootstrap.springboot.application;

import java.util.List;
import java.util.Properties;

import org.springframework.boot.Banner.Mode;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;

import mike.bootstrap.utilities.helpers.Print;
import mike.bootstrap.utilities.helpers.Utils;
import mike.bootstrap.utilities.security.SSLCertificateConfiguration;
import mike.bootstrap.utilities.system.AppInfo;
import mike.bootstrap.utilities.system.SysInfo;

/**
 * Application bootstrap that normalize the SpringBoot application configuration
 * and startup.
 * 
 * @author Mike (2021-02)
 * 
 */
class ApplicationBootstrap {

    private ApplicationBootstrap() {}

    /**
     * Configure SpringBoot Application as a Batch application.
     * 
     * @param clazz the bean sources (the main class most of the time)
     * @param args  application arguments
     * @return org.springframework.boot#SpringApplication
     */
    public static SpringApplication batch(Class<?> clazz, String[] args) {
        return ApplicationBootstrap.configure(clazz, args, WebApplicationType.NONE);
    }

    /**
     * Configure SpringBoot Application as a servlet (MVC) Web application.
     * 
     * @param clazz the bean sources (the main class most of the time)
     * @param args  application arguments
     * @return org.springframework.boot#SpringApplication
     */
    public static SpringApplication servlet(Class<?> clazz, String[] args) {
        return ApplicationBootstrap.configure(clazz, args, WebApplicationType.SERVLET);
    }

    /**
     * Configure SpringBoot Application as a Reactive Web application.
     * 
     * @param clazz the bean sources (the main class most of the time)
     * @param args  application arguments
     * @return org.springframework.boot#SpringApplication
     */
    public static SpringApplication reactive(Class<?> clazz, String[] args) {
        return ApplicationBootstrap.configure(clazz, args, WebApplicationType.REACTIVE);
    }

    /**
     * Configure SpringBoot Application
     * 
     * @param clazz   the bean sources (the main class most of the time)
     * @param args    application arguments
     * @param appType Web application type
     * @return org.springframework.boot#SpringApplication
     */
    private static SpringApplication configure(Class<?> clazz, String[] args, WebApplicationType appType) {

        List<String> options = args != null ? List.of(args) : List.of();

        Print.out(AppInfo.banner());
        Print.out("Application starting with args: %s", options);

        if (clazz == null) {
            throw new IllegalArgumentException("Bootstrap::configure: no such class");
        }

        SysInfo.setMainClass(clazz);

        Properties configuration = parseCommandLineArguments(options);

        /* *** SSL Auto-Configuration *** */
        SSLCertificateConfiguration.configure();

        /* *** Build Spring Application *** */
        SpringApplication application = new SpringApplicationBuilder(clazz)
                .logStartupInfo(true).bannerMode(Mode.OFF)
                .properties(configuration)
                .initializers(new ApplicationBootstrapInitializer())
                .web(appType)
                .build();

        System.setProperty(AppInfo.KW_APP_WEBAPP, String.valueOf(appType != WebApplicationType.NONE));

        return application;
    }

    /**
     * Parse command line arguments.
     * 
     * @param options list of command line arguments
     */
    private static Properties parseCommandLineArguments(List<String> options) {

        var configuration = new Properties();

        for (String opt : options) {

            if (opt.startsWith("--node=")) {
                String node = Utils.getArgv(opt);
                configuration.setProperty(AppInfo.KW_APP_NODE, node);
                System.setProperty(AppInfo.KW_APP_NODE, node);
            }
        }

        if (configuration.getProperty(AppInfo.KW_APP_NODE, "").isEmpty()) {
            Print.fatal("Bootstrap::commandLine: no such argument: --node");
            System.exit(1);
        }

        return configuration;
    }
}

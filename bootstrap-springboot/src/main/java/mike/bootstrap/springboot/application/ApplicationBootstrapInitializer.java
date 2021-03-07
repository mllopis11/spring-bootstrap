package mike.bootstrap.springboot.application;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import mike.bootstrap.utilities.exceptions.ApplicationErrorException;
import mike.bootstrap.utilities.helpers.Print;
import mike.bootstrap.utilities.helpers.Utils;
import mike.bootstrap.utilities.security.SSLCertificateConfiguration;
import mike.bootstrap.utilities.system.AppInfo;
import mike.bootstrap.utilities.system.SysInfo;

/**
 * Intialize the Springboot application in a standard way.
 * 
 * @author Mike (2020-10)
 */
class ApplicationBootstrapInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

	private static final Logger log = LoggerFactory.getLogger(ApplicationBootstrapInitializer.class);
	
	@Override
	public void initialize(ConfigurableApplicationContext applicationContext) {
        ConfigurableEnvironment env = applicationContext.getEnvironment();

		/* *** Check server address *** */
        Optional<String> url = this.checkServerAddresses(env);
        
        /* *** SSL configuration if not already done *** */
        this.sslCertificateAutoConfiguration(env);
        
		/* ***** Display startup header ***** */
        this.startup(env, url);

        this.finalize(env);
	}
	
	private void startup(ConfigurableEnvironment env, Optional<String> url) {
		
		log.info(Utils.lineOf('#'));

        log.info(AppInfo.banner());
        log.info(AppInfo.starter());
        
        boolean logStartupInfo = env.getProperty("spring.main.log-startup-info", Boolean.class);
        
        if ( logStartupInfo) {
        	log.info(SysInfo.platform());
		}
        
        log.info("Application started in: {}", SysInfo.USER_DIR);
        log.info("Configuration loaded from: {}", env.getProperty("spring.config.location", "default"));
        log.info("Application context: node={}, webApplication={}, profiles={}", 
                AppInfo.node(), AppInfo.isWebApplication(), List.of(env.getActiveProfiles()));

        url.ifPresent( u -> log.info("Application URL: {}", url.get()) );
        
        String sslTrustStoreLocation = SSLCertificateConfiguration.getTrustStore();
        log.debug("Certificates location: {} (HTTPs protocols: {})", 
        		sslTrustStoreLocation.isEmpty() ? "<not set>" : sslTrustStoreLocation,
        		SSLCertificateConfiguration.getHttpsProtocols());
	}
	
	private void finalize(ConfigurableEnvironment env) {
		String runtimeDirectory = AppInfo.runtimeDirectory();
        
        if ( ! env.getProperty(AppInfo.KW_APP_RUNDIR, "").isEmpty() ) {
            System.setProperty(AppInfo.KW_APP_RUNDIR, env.getProperty(AppInfo.KW_APP_RUNDIR, runtimeDirectory));
        }
        
        log.info("Runtime root directory: {}", runtimeDirectory);
	}

	/**
     * Check if the host address is already bound
     * <p>
     * The method stop the program with a return code '1' if the server address
     * is already bound.
     *    
     * @param env application environment
     * @return application URL
     */
    private Optional<String> checkServerAddresses(ConfigurableEnvironment env) {

        if ( AppInfo.isWebApplication() ) {
        	
        	try {
                String hostname = env.getProperty("server.address", SysInfo.hostname());
                String port = env.getProperty("server.port", "8080");
                String contextPath = env.getProperty("server.servlet.context-path", "/");

                SysInfo.hostAddressAlreadyBound(hostname, port);

                return Optional.of(String.format("%s:%s%s", hostname, port, contextPath));
            } catch (ApplicationErrorException aee) {
                Print.fatal("ApplicationStartup: %s", aee.getMessage());
                System.exit(1);
            }
        }
        
        return Optional.empty();
    }
    
    /**
     * SSL configuration.
     * <p>
     * Looks for SSL trust store configuration with the following prefixes: application.ssl, server.ssl
     * 
     * @param env application environment
     */
    private void sslCertificateAutoConfiguration(ConfigurableEnvironment env) {

        if ( ! SSLCertificateConfiguration.isSSLConfigured() ) {
            String[] sslPropertyPrefixes = { "container.ssl", "server.ssl" };

            String trustStore;
            String trustStorePassword;

            for ( String prefix : sslPropertyPrefixes ) {
                trustStore = env.getProperty(prefix + ".truststore", "");
                trustStorePassword = env.getProperty(prefix + ".truststore-password", "");

                if ( ! trustStore.isEmpty() ) {
                    SSLCertificateConfiguration.setTrustStore(trustStore);
                    SSLCertificateConfiguration.setTrustStorePassword(trustStorePassword);
                    break;
                }
            }
        }
    }
}

package mike.bootstrap.utilities.security;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;

import javax.net.ssl.SSLContext;

import mike.bootstrap.utilities.exceptions.ApplicationErrorException;
import mike.bootstrap.utilities.helpers.Strings;
import mike.bootstrap.utilities.nio.files.PathUtils;
import mike.bootstrap.utilities.nio.files.Resource;

/**
 * SSL certificates auto configuration
 * <p>
 * This class configures the certificates trust store properties in JVM.
 * 
 * @author Mike (2018-09)
 * @since 1.8
 */
public class SSLCertificateConfiguration {

    public static final String KW_HTTPS_PROTOCOLS = "https.protocols";
    public static final String KW_HTTPS_PROTOCOL_DEFAULT = "TLSv1.2";

    public static final String KW_TRUSTSTORE = "javax.net.ssl.trustStore";
    public static final String KW_TRUSTSTORE_SECRET = "javax.net.ssl.trustStorePassword";

    public static final String KW_JVM_PROP_NAME = "ssl.properties";
    public static final String KW_DEFAULT_CERTIFICATES_CONFIG = "certificates.properties";

    static {
        SSLCertificateConfiguration.setHttpsProtocols();
    }

    /**
     * Private constructor (Prevent any instantiation)
     */
    private SSLCertificateConfiguration() {}

    /**
     * Configure SSL from the filename read from the JVM system property 'ssl.properties' if exists
     * otherwise from the default certificates filename 'certificates.properties'.
     * 
     * @throws UncheckedIOException in case of any error occurs while reading the file
     */
    public static void configure() {
        var filename = System.getProperty(KW_JVM_PROP_NAME, KW_DEFAULT_CERTIFICATES_CONFIG);
        SSLCertificateConfiguration.configure(filename);
    }

    /**
     * Configure SSL from the provided properties filename.
     * 
     * @param filename certificates properties filename
     * @throws UncheckedIOException in case of any error occurs while reading the file
     */
    public static void configure(String filename) {
        SSLCertificateConfiguration.configure(Paths.get(filename));
    }

    /**
     * Configure SSL from the provided properties file.
     * 
     * @param file certificates properties file
     * @throws UncheckedIOException in case of any error occurs while reading the file
     */
    public static void configure(Path file) {

        try {
            var resource = Resource.of(file);

            if (resource.exists()) {
                SSLCertificateConfiguration.configure(resource.getProperties());
            }
        } catch (IOException ioe) {
            throw new UncheckedIOException("SSL configuration exception", ioe);
        }
    }

    /**
     * Configure SSL from the given properties.
     * 
     * @param properties SSL properties
     */
    public static void configure(Properties properties) {

        if (!properties.isEmpty()) {
            SSLCertificateConfiguration.setHttpsProtocols();
            SSLCertificateConfiguration.setTrustStore(properties.getProperty(KW_TRUSTSTORE));
            SSLCertificateConfiguration
                    .setTrustStorePassword(properties.getProperty(KW_TRUSTSTORE_SECRET));
        }
    }

    public static String getHttpsProtocols() {

        try {
            return SSLContext.getDefault().getProtocol();
        } catch (NoSuchAlgorithmException e) {
            return System.getProperty(KW_HTTPS_PROTOCOLS, "");
        }
    }

    /**
     * Set HTTPS protocols to support. TLSv1.2 is set by default.
     */
    public static void setHttpsProtocols() {

        try {
            var sslCtx = SSLContext.getInstance(KW_HTTPS_PROTOCOL_DEFAULT);
            SSLContext.setDefault(sslCtx);
        } catch (NoSuchAlgorithmException e) {
            throw new ApplicationErrorException("SSL configuration HTTPs protocol: ",
                    KW_HTTPS_PROTOCOL_DEFAULT);
        }
    }

    /**
     * Set the JVM system property "javax.net.ssl.trustStore" with its default password.
     * 
     * @param trustStore trust store filename
     * @throws ApplicationErrorException if truststore file is not readable
     */
    public static void setTrustStore(String trustStore) {
        var store = Strings.strip(trustStore);

        if (!store.isEmpty()) {
            var absoluteStorePath = Paths.get(store).toAbsolutePath().normalize();

            if (Files.isReadable(absoluteStorePath)) {
                System.setProperty(KW_TRUSTSTORE, PathUtils.toUnixPath(absoluteStorePath));
                SSLCertificateConfiguration.setTrustStorePassword("changeit");
            } else {
                throw new ApplicationErrorException(
                        "no such readable SSL certificate truststore: %s", absoluteStorePath);
            }
        }
    }

    /**
     * @return the trust store filename or an empty string if not set
     */
    public static String getTrustStore() {
        return System.getProperty(KW_TRUSTSTORE, "");
    }

    /**
     * Set the JVM system property "javax.net.ssl.trustStorePassword".
     * 
     * @param trustStorePassword trust store password
     */
    public static void setTrustStorePassword(String trustStorePassword) {
        var pass = Strings.strip(trustStorePassword);

        if (!pass.isEmpty()) {
            System.setProperty(KW_TRUSTSTORE_SECRET, pass);
        }
    }

    /**
     * @return true if the trustStore JVM system property (javax.net.ssl.trustStore) is set
     *         otherwise false
     */
    public static boolean isSSLConfigured() {
        return !SSLCertificateConfiguration.getTrustStore().isEmpty();
    }
}

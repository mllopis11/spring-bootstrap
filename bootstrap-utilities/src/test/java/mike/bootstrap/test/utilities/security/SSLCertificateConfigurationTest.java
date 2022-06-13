package mike.bootstrap.test.utilities.security;

import static org.assertj.core.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import mike.bootstrap.test.utilities.constants.TestConstants;
import mike.bootstrap.utilities.nio.files.PathUtils;
import mike.bootstrap.utilities.security.SSLCertificateConfiguration;

@DisplayName("Security::SSLCertificateConfiguration")
@TestMethodOrder(OrderAnnotation.class)
class SSLCertificateConfigurationTest {

    private static final String cacertsFilename = String.format("%s/%s", TestConstants.TEST_ROOT_FOLDER, "cacerts");

    private static Path tempDir;
    private static Path sslTempFile;

    @BeforeAll
    static void init() throws IOException {
	Path cacertsFile = Path.of(cacertsFilename);
	tempDir = Path.of(PathUtils.dirname(cacertsFile));
	Files.createDirectories(tempDir);

	sslTempFile = Files.createFile(PathUtils.of(tempDir, "ssl_config.properties"));
	Files.createFile(cacertsFile);
    }

    @AfterAll
    static void cleanUp() throws IOException {
	Files.deleteIfExists(Path.of(cacertsFilename));
	Files.deleteIfExists(sslTempFile);
    }

    @Test
    @Order(1)
    void should_return_ssl_not_configured_when_certificate_file_not_exists() {
	assertThat(SSLCertificateConfiguration.isSSLConfigured()).isFalse();
    }

    @Test
    @Order(2)
    void should_apply_default_configuration_when_config_file_empty() throws IOException {

	assertThatNoException().isThrownBy(() -> SSLCertificateConfiguration.configure(sslTempFile));
	assertThat(SSLCertificateConfiguration.isSSLConfigured()).isFalse();
    }

    @Test
    @Order(3)
    void should_apply_configuration_when_config_file_exist() throws IOException {

	String[] sslConf = { SSLCertificateConfiguration.KW_TRUSTSTORE + "=" + cacertsFilename };

	Files.write(sslTempFile, Arrays.asList(sslConf));

	assertThatNoException().isThrownBy(() -> SSLCertificateConfiguration.configure(sslTempFile));
	assertThat(SSLCertificateConfiguration.getHttpsProtocols()).contains("TLSv1.2");
	assertThat(SSLCertificateConfiguration.isSSLConfigured()).isTrue();
    }
}

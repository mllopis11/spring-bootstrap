package mike.bootstrap.test.utilities.system;

import static org.assertj.core.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import mike.bootstrap.test.utilities.constants.TestConstants;
import mike.bootstrap.utilities.system.AppInfo;

@DisplayName("System::AppInfo")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AppInfoTest {

	@Test
    @Order(1)
    void should_return_expected_values_when_build_info_exists() throws IOException {
    	
		Path testDirectory = Path.of(TestConstants.TEST_ROOT_FOLDER);
		Files.createDirectories(testDirectory);
		
    	Path buildProprtiesFile = this.buildFilePath("build-info.properties");
    	
    	System.setProperty(AppInfo.KW_APP_NODE, "node-test");
    	System.setProperty(AppInfo.KW_APP_WEBAPP, "true");
    	System.setProperty(AppInfo.KW_APP_RUNDIR, TestConstants.TEST_ROOT_FOLDER);
    	System.setProperty(AppInfo.KW_APP_BUILD_INFO, buildProprtiesFile.toString());
    	
    	Files.write(buildProprtiesFile, 
				List.of(
						"build.time=2020-06-19T00:34:56+01:00",
						"build.application.name=AppTest",
						"build.module.name=TestModule",
						"build.version=0.1.3",
						"build.organization.name=MyBigCompany"),
				StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    	
    	assertThat(AppInfo.name()).isEqualTo("AppTest");
    	assertThat(AppInfo.module()).isEqualTo("TestModule");
    	assertThat(AppInfo.version()).isEqualTo("0.1.3");
    	assertThat(AppInfo.company()).isEqualTo("MyBigCompany");
    	assertThat(AppInfo.node()).isEqualTo("node-test");
    	assertThat(AppInfo.releaseDttm()).isEqualTo("2020-06-19T00:34:56+01:00");
    	assertThat(AppInfo.isWebApplication()).isTrue();
    	assertThat(AppInfo.runtimeDirectory()).isEqualTo(TestConstants.TEST_ROOT_FOLDER);
    	assertThat(AppInfo.process()).isNotEmpty();
    	assertThat(AppInfo.banner()).isNotEmpty();
    	assertThat(AppInfo.starter()).isNotEmpty();
    	assertThat(AppInfo.footer()).isNotEmpty();
    	assertThat(AppInfo.uptime()).isNotEmpty();
        
        boolean deleted = this.deleteFile(buildProprtiesFile);
        assertThat(deleted).isTrue();
    }
	
	private Path buildFilePath(String basename) throws IOException {
		
		Path testDirectory = Path.of(TestConstants.TEST_ROOT_FOLDER);
		Files.createDirectories(testDirectory);
		
		Path buildProprtiesPath = Path.of(TestConstants.TEST_ROOT_FOLDER, "basename");
				
		this.deleteFile(buildProprtiesPath);
		
    	return buildProprtiesPath;
	}
	
	private boolean deleteFile(Path file) throws IOException {
		return Files.deleteIfExists(file);
	}
}

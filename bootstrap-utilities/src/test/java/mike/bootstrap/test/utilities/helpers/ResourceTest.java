package mike.bootstrap.test.utilities.helpers;

import static org.assertj.core.api.Assertions.*;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import mike.bootstrap.utilities.helpers.Resource;

@DisplayName("Helpers::Resource")
class ResourceTest {

	@Test
	void should_throw_IllegalArgumentException_when_null_or_blank() {
		
		assertThatIllegalArgumentException()
			.isThrownBy( () -> new Resource("   ") );
		
		assertThatIllegalArgumentException()
			.isThrownBy( () -> { String s = null; new Resource(s); });
		
		assertThatIllegalArgumentException()
			.isThrownBy( () -> { Path p = null; new Resource(p); });
	}
	
	@Test
	void should_return_not_exists_when_resource_not_readable() {
		
		String name = "./data/my-props.properties";
		
		Resource resource = new Resource(name);
		
		assertThat(resource.notFound()).isTrue();
		assertThat(resource.getName()).isEqualTo(name);
		assertThat(resource.getURL()).isNull();
		assertThat(resource.getURI()).isNull();
		assertThatNoException().isThrownBy(() -> resource.getProperties());
		assertThatIOException().isThrownBy( () -> { 
			try ( InputStream is = resource.getInputStream(); ) {} 
		});
	}
	
	@Test
	void should_return_readable_resource_when_classpath_resource() throws IOException {
		
		String name = "data/my-test-properties.txt";
		
		Resource resource = new Resource(name);

		assertThat(resource.found()).isTrue();
		assertThat(resource.getName()).isEqualTo(name);
		assertThat(resource.getURL()).isNotNull();
		assertThat(resource.getURI()).isNotNull();
		assertThat(resource.getProperties()).isNotEmpty();
	}
	
	@Test
	void should_return_readable_resource_when_filesystem_resource() throws IOException {
		
		Path tempTestFile = Files.createTempFile("test-file", null);
        Files.write(tempTestFile, "my.prop=value".getBytes());
        
		Resource resource = new Resource(tempTestFile);

		assertThat(resource.found()).isTrue();
		assertThat(resource.getName()).isNotEmpty();
		assertThat(resource.getURL()).isNotNull();
		assertThat(resource.getProperties()).isNotEmpty();
	}
}

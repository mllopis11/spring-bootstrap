package mike.bootstrap.test.utilities.helpers;

import static org.assertj.core.api.Assertions.*;

import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import mike.bootstrap.utilities.helpers.StringSanitizer;

@DisplayName("Helpers::Sanitizer")
class StringSanitizerTest {

	@Test
	void should_return_sanitized_string_when_lowercase() {
		String input = "  My   String.test ";
		String expectedBase = "mystring.test";
		
		Stream.of(null, '-', '_').forEach( c -> {
			String expected = c == null ? expectedBase : expectedBase.replace("my", "my" + c);
			assertThat(StringSanitizer.LOWERCASE.apply(input, c)).isEqualTo(expected);
		});
	}
	
	@Test
	void should_return_sanitized_string_when_uppercase() {
		String input = "  My   String.test ";
		String expectedBase = "MYSTRING.TEST";
		
		Stream.of(null, '-', '_').forEach( c -> {
			String expected = c == null ? expectedBase : expectedBase.replace("MY", "MY" + c);
			assertThat(StringSanitizer.UPPERCASE.apply(input, c)).isEqualTo(expected);
		});
	}
	
	@Test
	void should_return_sanitized_string_when_noop() {
		String input = "  My   String.test ";
		String expectedBase = "MyString.test";
		
		Stream.of(null, '-', '_').forEach( c -> {
			String expected = c == null ? expectedBase : expectedBase.replace("My", "My" + c);
			assertThat(StringSanitizer.SPACE.apply(input, c)).isEqualTo(expected);
		});
	}
}

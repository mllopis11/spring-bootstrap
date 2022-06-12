package mike.bootstrap.test.utilities.helpers;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import mike.bootstrap.utilities.helpers.StringValue;
import mike.bootstrap.utilities.helpers.Strings;
import mike.bootstrap.utilities.system.SysInfo;

@DisplayName("Helpers::StringValue")
class StringValueTest {

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = "   ")
    void should_return_blank_or_empty_string_when_value(String value) {
	assertThat(StringValue.of(value).value()).isBlank();
    }
    
    @Test
    void should_return_foo_when_default_foo_and_value_is_null() {
	assertThat(StringValue.of(null, "foo").value()).isEqualTo("foo");
    }
    
    @ParameterizedTest
    @ValueSource(strings = { "   ", "fooValue", " My Foo Value   " })
    void should_return_string_without_space_when_value(String value) {
	var val = StringValue.of(value).shrink().value();
	assertThat(val).doesNotContainAnyWhitespaces();
    }
    
    @ParameterizedTest
    @ValueSource(strings = { "   ", "fooValue", " My Foo Value   " })
    void should_return_string_with_one_space_when_value(String value) {
	var expected = value.strip().replaceAll("\\s+", " ");
	
	assertThat(StringValue.of(value).strip().sanitize().value()).isEqualTo(expected);
    }
    
    @ParameterizedTest
    @ValueSource(strings = { "   ", "fooValue", " My Foo Value   " })
    void should_return_string_with_hyphens_when_value(String value) {
	var expected = Strings.sanitize(value.strip(), Strings.REGEX_SPACES, "-");
	
	assertThat(StringValue.of(value).strip().sanitize(Strings.REGEX_SPACES, "-").value()).isEqualTo(expected);
    }
    
    @Test
    void should_return_string_without_line_feed() {
	var message = ("a message" + SysInfo.LINE_FEED);

	assertThat(StringValue.of(message).chomp().value()).doesNotContain("\\r", "\\n");
    }
}

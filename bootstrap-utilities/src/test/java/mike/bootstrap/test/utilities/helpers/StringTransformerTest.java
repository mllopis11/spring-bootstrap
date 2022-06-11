package mike.bootstrap.test.utilities.helpers;

import static org.assertj.core.api.Assertions.*;

import java.util.Map;
import java.util.regex.Pattern;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import mike.bootstrap.utilities.helpers.StringTransformer;

@DisplayName("Helpers::StringTransformer")
class StringTransformerTest {

    private static final Pattern namePattern = Pattern.compile(
	    "([A-Z0-9\\-]{1,15})?(?<prefix>[A-Z]{2,3})(?<name>_\\w{10,64})(?<ext>\\.[a-zA-Z\\.]{3,8})$");
    
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = { "FOO", "PFX_FOO_DATA_20220612_DAT" })
    void should_throw_IllegalArgumentException_when_value_not_matching(String value) {
	
	assertThatIllegalArgumentException()
		.isThrownBy(() -> StringTransformer.of(namePattern).expression(value, "BAR").get())
		.withMessageStartingWith("value does not match pattern:");
    }
    
    @ParameterizedTest
    @NullAndEmptySource
    void should_throw_IllegalArgumentException_when_expression_not_set(String expression) {
	
	assertThatIllegalArgumentException()
		.isThrownBy(() -> StringTransformer.of(namePattern).expression("FOO", expression).get())
		.withMessageStartingWith("no such replacement expression");
    }
    
    @Test
    void should_throw_IllegalStateException_when_remaining_keys() {
	
	assertThatIllegalStateException()
		.isThrownBy(() -> StringTransformer.of(namePattern)
			.expression("PFX_FOO_DATA_20220612.DAT", "%type%${name}_%id%${ext}")
			.replace("type", "BAR")
			.get())
		.withMessageStartingWith("remaining unresolved key(s):");
	
    }
    
    @ParameterizedTest
    @ValueSource(strings = {
	    "PFX_BAR_DATA_20220612.DAT.gz",
	    "777-PFX_BAR_DATA_20220612.DAT.gz",
	    "100-530-000-PFX_BAR_DATA_20220612.DAT"
    })
    void should_return_transformed_value_when_value(String value) {
	var newName = StringTransformer.of(namePattern)
		.expression(value, "%type%${name}_%id%${ext}")
		.replace(Map.of("type", "FOO", "id", "01"))
		.get();
	
	assertThat(newName).matches("FOO_BAR_DATA_20220612_01\\.DAT(\\.gz)?");
    }
}

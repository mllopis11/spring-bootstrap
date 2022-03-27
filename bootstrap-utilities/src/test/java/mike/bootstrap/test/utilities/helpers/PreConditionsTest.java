package mike.bootstrap.test.utilities.helpers;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import mike.bootstrap.utilities.helpers.PreConditions;

@DisplayName("Helpers::PreConditions")
class PreConditionsTest {

    @ParameterizedTest
    @ValueSource(strings = { "Foo", " Bar  " })
    void notBlank_should_return_tested_value_when_value(String val) {	
	var rv = PreConditions.notBlank(val, "myVar");
	assertThat(rv).isEqualTo(val);
    }
    
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = "  ")
    void notBlank_should_throw_illegalArgumentException_when_value(String val) {
	assertThatIllegalArgumentException()
		.isThrownBy(() -> PreConditions.notBlank(val, "myVar"))
		.withMessageContainingAll("variable", "myVar", "null", "blank");
    }
    
    @Test
    void notNull_should_throw_illegalArgumentException_when_null_object() {
	String val = null;
	
	assertThatIllegalArgumentException()
		.isThrownBy(() -> PreConditions.notNull(val, "myVar"))
		.withMessageContainingAll("variable", "myVar", "null");
    }
    
    @Test
    void notNull_should_return_tested_object_when_object_not_null() {
	var rv = PreConditions.notNull("", "myVar");
	assertThat(rv).isEmpty();
    }
    
    @Test
    void notEmpty_should_return_tested_object_when_string_not_null_or_empty() {
	var rv = PreConditions.notEmpty("foo", "myVar");
	assertThat(rv).isEqualTo("foo");
    }
    
    @Test
    void notEmpty_should_return_tested_object_when_collection_not_null_or_empty() {

	var setVar = PreConditions.notEmpty(Set.of("foo", "bar"), "myVar");
	assertThat(setVar).isNotNull().isInstanceOf(Set.class).isNotEmpty();
	
	var listVar = PreConditions.notEmpty(List.of("foo", "bar"), "myVar");
	assertThat(listVar).isNotNull().isInstanceOf(List.class).isNotEmpty();
	
	var mapVar = PreConditions.notEmpty(Map.of("foo", "fooVal", "bar", "barVal"), "myVar");
	assertThat(mapVar).isNotNull().isInstanceOf(Map.class).isNotEmpty();
    }
    
    @Test
    void notEmpty_should_throw_illegalArgumentException_when_object_null_or_empty() {

	assertThatIllegalArgumentException()
		.isThrownBy(() -> PreConditions.notEmpty("", "myVar"))
		.withMessageContainingAll("variable", "myVar", "empty");
	
	assertThatIllegalArgumentException()
		.isThrownBy(() -> PreConditions.notEmpty(Set.of(), "myVar"))
		.withMessageContainingAll("collection", "myVar", "empty");
	
	assertThatIllegalArgumentException()
		.isThrownBy(() -> PreConditions.notEmpty(Map.of(), "myVar"))
		.withMessageContainingAll("collection", "myVar", "empty");
    }
    
    @Test
    void notEmpty_should_UnsupportedOperationException_when_invalid_object_type() {

	assertThatExceptionOfType(UnsupportedOperationException.class)
		.isThrownBy(() -> PreConditions.notEmpty(0, "myVar"))
		.withMessageContaining("object type not supported");
    }
}

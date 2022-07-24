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

    private static final String STR_ERR_MSG_NOT_BLANK = "variable 'myVar' not set (null or blank)";
    private static final String STR_ERR_MSG_NOT_NULL = "variable 'myVar' not set (null)";
    private static final String STR_ERR_MSG_EMPTY = "variable 'myVar' is empty";
    private static final String STR_ERR_COLLECTION_EMPTY = "collection 'myCollection' is empty";

    @ParameterizedTest
    @ValueSource(strings = { "Foo", " Bar  " })
    void notBlank_should_return_tested_value_when_value(String val) {
        var rv = PreConditions.notBlank(val);
        assertThat(rv).isEqualTo(val);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = "  ")
    void notBlank_should_throw_illegalArgumentException_when_value(String val) {
        assertThatIllegalArgumentException().isThrownBy(() -> PreConditions.notBlank(val))
                .withMessageContainingAll("string value not set", "null", "blank", "empty");

        assertThatIllegalArgumentException()
                .isThrownBy(() -> PreConditions.notBlank(val, STR_ERR_MSG_NOT_BLANK))
                .withMessageContainingAll("variable", "myVar", "null", "blank");
    }

    @Test
    void notNull_should_throw_illegalArgumentException_when_null_object() {
        String val = null;

        assertThatIllegalArgumentException().isThrownBy(() -> PreConditions.notNull(val))
                .withMessageContainingAll("object required (null)");

        assertThatIllegalArgumentException()
                .isThrownBy(() -> PreConditions.notNull(val, STR_ERR_MSG_NOT_NULL))
                .withMessageContainingAll("variable", "myVar", "null");
    }

    @Test
    void notNull_should_return_tested_object_when_object_not_null() {
        var rv = PreConditions.notNull("");
        assertThat(rv).isEmpty();
    }

    @Test
    void notEmpty_should_return_tested_object_when_string_not_null_or_empty() {
        var rv = PreConditions.notEmpty("foo", STR_ERR_MSG_EMPTY);
        assertThat(rv).isEqualTo("foo");
    }

    @Test
    void notEmpty_should_return_tested_object_when_collection_not_null_or_empty() {

        var setVar = PreConditions.notEmpty(Set.of("foo", "bar"));
        assertThat(setVar).isNotNull().isInstanceOf(Set.class).isNotEmpty();

        var listVar = PreConditions.notEmpty(List.of("foo", "bar"));
        assertThat(listVar).isNotNull().isInstanceOf(List.class).isNotEmpty();

        var mapVar = PreConditions.notEmpty(Map.of("foo", "fooVal", "bar", "barVal"));
        assertThat(mapVar).isNotNull().isInstanceOf(Map.class).isNotEmpty();
    }

    @Test
    void notEmpty_should_throw_illegalArgumentException_when_object_null_or_empty() {

        assertThatIllegalArgumentException()
                .isThrownBy(() -> PreConditions.notEmpty("", STR_ERR_MSG_EMPTY))
                .withMessageContainingAll("variable", "myVar", "empty");

        assertThatIllegalArgumentException()
                .isThrownBy(() -> PreConditions.notEmpty(Set.of(), STR_ERR_COLLECTION_EMPTY))
                .withMessageContainingAll("collection", "myCollection", "empty");

        assertThatIllegalArgumentException()
                .isThrownBy(() -> PreConditions.notEmpty(Map.of(), STR_ERR_COLLECTION_EMPTY))
                .withMessageContainingAll("collection", "myCollection", "empty");
    }

    @Test
    void notEmpty_should_UnsupportedOperationException_when_invalid_object_type() {

        assertThatExceptionOfType(UnsupportedOperationException.class)
                .isThrownBy(() -> PreConditions.notEmpty(0, "variable '%s' is Zero", "myVar"))
                .withMessageContaining("object type not supported");
    }
}

package mike.bootstrap.test.utilities.helpers;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import mike.bootstrap.utilities.helpers.Strings;

@DisplayName("Helpers::Strings")
class StringsTest {

    @Test
    void method_getArgv_should_return_default_value_when_null() {

        String defValue = null;

        assertThat(Strings.getArgv(null)).isEmpty();
        assertThat(Strings.getArgv(null, defValue)).isEmpty();
        assertThat(Strings.getArgv(null, -1)).isEqualTo(-1);
    }

    @ParameterizedTest
    @ValueSource(strings = { "", "  ", "=", " arg", "arg=", "=12" })
    void method_getArgv_should_return_default_value_when_malformed(String arg) {
        assertThat(Strings.getArgv(arg)).isEmpty();
        assertThat(Strings.getArgv(arg, "default")).isEqualTo("default");
    }

    @ParameterizedTest
    @ValueSource(strings = { "arg=value", "arg = value ", })
    void method_getargv_should_return_arg_value_when_well_formatted(String arg) {
        assertThat(Strings.getArgv(arg)).isEqualTo("value");
    }

    @Test
    void method_toInteger_should_return_default_value_when_not_integer() {
        assertThat(Strings.toInteger(null)).isZero();
        assertThat(Strings.toInteger(null, -1)).isEqualTo(-1);
        assertThat(Strings.toInteger(" ")).isZero();
        assertThat(Strings.toInteger(" ", -1)).isEqualTo(-1);
        assertThat(Strings.toInteger("2900000000")).isZero();
        assertThat(Strings.toInteger("2900000000", Integer.MAX_VALUE)).isEqualTo(Integer.MAX_VALUE);
    }

    @Test
    void method_toInteger_should_return_value_when_integer() {
        int val = 3456;

        assertThat(Strings.toInteger(String.valueOf(val))).isEqualTo(val);
        assertThat(Strings.toInteger(String.valueOf(val), -1)).isEqualTo(val);
    }

    @Test
    void method_toLong_should_return_default_value_when_not_long() {

        assertThat(Strings.toLong(null)).isZero();
        assertThat(Strings.toLong(null, -1)).isEqualTo(-1);
        assertThat(Strings.toLong(" ")).isZero();
        assertThat(Strings.toLong(" ", -1)).isEqualTo(-1);
        assertThat(Strings.toLong("12345678909876543210")).isEqualTo(0);
        assertThat(Strings.toLong("12345678909876543210", Long.MAX_VALUE))
                .isEqualTo(Long.MAX_VALUE);
    }

    @Test
    void method_toLong_should_return_value_when_long() {
        int val = 3456;

        assertThat(Strings.toLong(String.valueOf(val))).isEqualTo(val);
        assertThat(Strings.toLong(String.valueOf(val), -1)).isEqualTo(val);
    }
}

package mike.bootstrap.test.utilities.helpers;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import mike.bootstrap.utilities.helpers.Utils;

@DisplayName("Helpers::Utils")
class UtilsTest {

    @ParameterizedTest
    @ValueSource(ints = { 5, 11, 20 })
    void method_between_should_return_true_when_value_is_in_range_5_to_20(int value) {
        assertThat(Utils.between(value, 5, 20)).isTrue();
    }

    @ParameterizedTest
    @ValueSource(ints = { 4, 21 })
    void method_between_should_return_false_when_value_is_out_of_range_5_to_20(int value) {
        assertThat(Utils.between(value, 5, 20)).isFalse();
    }

    @ParameterizedTest
    @ValueSource(ints = { 1024, 8080, 65535 })
    void method_isPortValid_should_return_true_when_regular_port_and_system_ports_excluded(
            int port) {
        assertThat(Utils.isPortValid(port)).isTrue();
        assertThat(Utils.isPortValid(port, false)).isTrue();
        assertThat(Utils.isPortNotValid(port)).isFalse();
        assertThat(Utils.isPortNotValid(port, false)).isFalse();
    }

    @ParameterizedTest
    @ValueSource(ints = { -1, 0, 1023, 65536 })
    void method_isPortNotValid_should_return_true_when_not_regular_port_and_system_ports_excluded(
            int port) {
        assertThat(Utils.isPortNotValid(port)).isTrue();
        assertThat(Utils.isPortNotValid(port, false)).isTrue();
    }

    @ParameterizedTest
    @ValueSource(ints = { 1024, 8080, 65535, 0, 1023 })
    void method_isPortValid_should_return_true_when_system_ports_included(int port) {
        assertThat(Utils.isPortValid(port, true)).isTrue();
        assertThat(Utils.isPortNotValid(port, true)).isFalse();
    }

    @ParameterizedTest
    @ValueSource(ints = { -1, 65536 })
    void method_isPortValid_should_return_false_when_system_ports_included(int port) {
        assertThat(Utils.isPortNotValid(port, true)).isTrue();
        assertThat(Utils.isPortValid(port, true)).isFalse();
    }

    @Test
    void method_defaultValue_should_return_expected_object() {
        long len = 254;

        assertThat(Utils.nullAs(len, 0L)).isEqualTo(len);
        assertThat(Utils.nullAs(null, 0L)).isZero();
        assertThatIllegalArgumentException().isThrownBy(() -> Utils.nullAs(null, null));
    }
}

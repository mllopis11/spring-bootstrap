package mike.bootstrap.test.utilities.helpers;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import mike.bootstrap.utilities.helpers.Timer;
import mike.bootstrap.utilities.helpers.Utils;

@DisplayName("Helpers::Utils")
class UtilsTest {

	@BeforeAll
	static void init() {
		Timer.sleep(1);
	}
	
	@Test
    void method_getArgv_should_return_default_value_when_null() {
    	
    	String defValue = null;
    	
    	assertThat(Utils.getArgv(null)).isEmpty();
    	assertThat(Utils.getArgv(null, defValue)).isEmpty();
    	assertThat(Utils.getArgv(null, -1)).isEqualTo(-1);
    }
	
	@ParameterizedTest
    @ValueSource( strings = { "", "  ", "=", " arg", "arg=", "=12"})
    void method_getArgv_should_return_default_value_when_malformed(String arg) {
    	assertThat(Utils.getArgv(arg)).isEmpty();
    	assertThat(Utils.getArgv(arg, "default")).isEqualTo("default");
    }
    
    @ParameterizedTest
    @ValueSource( strings = { "arg=value", "arg = value ",})
    void method_getargv_should_return_arg_value_when_well_formatted(String arg) {
    	assertThat(Utils.getArgv(arg)).isEqualTo("value");
    }
    
    @Test
    void method_toInteger_should_return_default_value_when_not_integer() {
    	assertThat(Utils.toInteger(null)).isZero();
    	assertThat(Utils.toInteger(null, -1)).isEqualTo(-1);
    	assertThat(Utils.toInteger(" ")).isZero();
    	assertThat(Utils.toInteger(" ", -1)).isEqualTo(-1);
    	assertThat(Utils.toInteger("2900000000")).isZero();
    	assertThat(Utils.toInteger("2900000000", Integer.MAX_VALUE)).isEqualTo(Integer.MAX_VALUE);
    }
    
    @Test
    void method_toInteger_should_return_value_when_integer() {
    	int val = 3456;
    	
    	assertThat(Utils.toInteger(String.valueOf(val))).isEqualTo(val);
    	assertThat(Utils.toInteger(String.valueOf(val), -1)).isEqualTo(val);
    }
    
    @Test
    void method_toLong_should_return_default_value_when_not_long() {
    	
    	assertThat(Utils.toLong(null)).isZero();
    	assertThat(Utils.toLong(null, -1)).isEqualTo(-1);
    	assertThat(Utils.toLong(" ")).isZero();
    	assertThat(Utils.toLong(" ", -1)).isEqualTo(-1);
    	assertThat(Utils.toLong("12345678909876543210")).isEqualTo(0);
    	assertThat(Utils.toLong("12345678909876543210", Long.MAX_VALUE)).isEqualTo(Long.MAX_VALUE);
    }
    
    @Test
    void method_toLong_should_return_value_when_long() {
    	int val = 3456;
    	
    	assertThat(Utils.toLong(String.valueOf(val))).isEqualTo(val);
    	assertThat(Utils.toLong(String.valueOf(val), -1)).isEqualTo(val);
    }
    
    @ParameterizedTest
    @ValueSource( ints = { 5, 11, 20 })
    void method_between_should_return_true_when_value_is_in_range_5_to_20(int value) {
    	assertThat(Utils.between(value, 5, 20)).isTrue();
    }
    
    @ParameterizedTest
    @ValueSource( ints = { 4, 21 })
    void method_between_should_return_false_when_value_is_out_of_range_5_to_20(int value) {
    	assertThat(Utils.between(value, 5, 20)).isFalse();
    }
    
    @ParameterizedTest
    @ValueSource( ints = { 1024, 8080, 65535 } )
    void method_isPortValid_should_return_true_when_regular_port_and_system_ports_excluded(int port) {
    	assertThat(Utils.isPortValid(port)).isTrue();
    	assertThat(Utils.isPortValid(port, false)).isTrue();
    	assertThat(Utils.isPortNotValid(port)).isFalse();
    	assertThat(Utils.isPortNotValid(port, false)).isFalse();
    }
    
    @ParameterizedTest
    @ValueSource( ints = { -1, 0, 1023, 65536 } )
    void method_isPortNotValid_should_return_true_when_not_regular_port_and_system_ports_excluded(int port) {
    	assertThat(Utils.isPortNotValid(port)).isTrue();
    	assertThat(Utils.isPortNotValid(port, false)).isTrue();
    }
    
    @ParameterizedTest
    @ValueSource( ints = { 1024, 8080, 65535, 0, 1023 } )
    void method_isPortValid_should_return_true_when_system_ports_included(int port) {
    	assertThat(Utils.isPortValid(port, true)).isTrue();
    	assertThat(Utils.isPortNotValid(port, true)).isFalse();
    }
    
    @ParameterizedTest
    @ValueSource( ints = { -1, 65536 } )
    void method_isPortValid_should_return_false_when_system_ports_included(int port) {
    	assertThat(Utils.isPortNotValid(port, true)).isTrue();
    	assertThat(Utils.isPortValid(port, true)).isFalse();
    }
}

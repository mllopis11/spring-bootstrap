package mike.bootstrap.test.utilities.helpers;

import static org.assertj.core.api.Assertions.*;

import java.util.Locale;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import mike.bootstrap.utilities.helpers.ByteUnit;

@DisplayName("Helpers::ByteUnit")
class ByteUnitTest {

    @BeforeAll
    static void init() {
        /* Locale for decimal representation */
        Locale.setDefault(Locale.US);
    }
    
    @ParameterizedTest
    @CsvSource({"BYTE, 1", "KILO, 1024", "MEGA, 1048576", "GIGA, 1073741824", "TERA, 1099511627776"})
    void bytes_should_return_byte_unit_value_per_byte_unit(ByteUnit byteUnit, long expected) {
    	assertThat(byteUnit.base()).isEqualTo(expected);
    }
    
    @ParameterizedTest
    @CsvSource({
        "-12345, BYTE, 0", 
        "1024, BYTE, 1024", "1024, KILO, 1", 
        "1048576, KILO, 1024","1048576, MEGA, 1", "1572864, MEGA, 1.5", 
        "1073741824, GIGA, 1", "1073741824, MEGA, 1024", "1073741824, KILO, 1048576",
        "1099511627776, TERA, 1", "1099511627776, GIGA, 1024", "1099511627776, MEGA, 1048576"
    })
    void size_should_return_value_at_granularity_per_byte_unit(long bytes, ByteUnit byteUnit, double expected) {
    	assertThat(byteUnit.size(bytes)).isEqualTo(expected);
    }
    
    @ParameterizedTest
    @CsvSource({
        "-24567, 0b", "0, 0b", "1, 1b", "675, 675b", "1023, 1023b",
        "1024, 1kb", "7069, 6.9kb", 
        "1048576, 1mb", "1376509, 1.31mb",
        "1073741824, 1gb", "1375543069, 1.28gb",
        "1099511627776, 1tb", "2957986514293, 2.69tb"
    })
    void format_should_return_expected_representation(long bytes, String expected) {
    	assertThat(ByteUnit.format(bytes)).isEqualTo(expected);
    }
    
    @ParameterizedTest
    @CsvSource({
        "2T, 2199023255552", "2Tb, 2199023255552", 
        "25G, 26843545600", "25gb, 26843545600",
        "256m, 268435456", "256mb, 268435456", 
        "2048k, 2097152", "2048kb, 2097152",
        "512b, 512"
    })
    void from_should_return_bytes_value_when_valid_size_with_unit(String sizeWithUnit, long expected) {
    	assertThat(ByteUnit.from(sizeWithUnit)).isEqualTo(expected);
    }
    
    @ParameterizedTest
    @ValueSource( strings = {
            "", "  ", "g", "g2345", "34", "234.7mb", "-100k", "3 Tb", "56Pb", "12345678909876543210b" 
    })
    void from_should_throw_IllegalArgumentException_when_invalid_size_with_unit(String sizeWithUnit) {
    	assertThatIllegalArgumentException().isThrownBy(() -> ByteUnit.from(sizeWithUnit));
    }
    
    @ParameterizedTest
    @ValueSource( strings = {
            "9223372036854775808b", "9007199254740992kb", "8796093022208m", "8589934592g", "8388608Tb" 
    })
    void from_should_throw_IllegalArgumentException_when_exceed_max_value(String sizeWithUnit) {
    	assertThatIllegalArgumentException().isThrownBy(() -> ByteUnit.from(sizeWithUnit));
    }
}

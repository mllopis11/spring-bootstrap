package mike.bootstrap.test.utilities.helpers;

import static org.assertj.core.api.Assertions.*;

import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import mike.bootstrap.utilities.helpers.Uuid;

@DisplayName("Helpers::UUIDGen")
class UuidGenTest {

	/* Predefined namespace UUID */
    private static final String NAMESPACE_DNS = "6ba7b810-9dad-11d1-80b4-00c04fd430c8";
    
    private static final String UUID_REGEX = "[a-z0-9]{8}-[a-z0-9]{4}-%version%[a-z0-9]{3}-[a-z0-9]{4}-[a-z0-9]{12}";
    private static final String UUID_REGEX_STRIPPED = "[a-z0-9]{12}%version%[a-z0-9]{19}"; 
    
    @ParameterizedTest
    @MethodSource("uuidV3Values")
    void should_return_uuid_when_uuidV3(String value) {
        
        String regex = UUID_REGEX.replace("%version%", "3");
                
        assertThat(Uuid.uuidV3(value)).matches(regex);
        assertThat(Uuid.uuidV3(NAMESPACE_DNS, value)).matches(regex);
    }
    
    @Test
    void should_return_same_uuid_when_uuidV3_and_same_value() {
        
        String regex = UUID_REGEX.replace("%version%", "3");
        String name = "my_filename.txt";
        
        String uuid = Uuid.uuidV3(name);
        String uuid_ns = Uuid.uuidV3(NAMESPACE_DNS, name);
        
        assertThat(Uuid.uuidV3(name)).matches(regex).isEqualTo(uuid);
        assertThat(Uuid.uuidV3(NAMESPACE_DNS, name)).matches(regex).isEqualTo(uuid_ns);
    }
    
    @Test
    void should_return_uuid_when_uuidV4() {
        
        String regex = UUID_REGEX.replace("%version%", "4");
        String regex_stripped = UUID_REGEX_STRIPPED.replace("%version%", "4");
        
        assertThat(Uuid.uuidV4()).matches(regex);
        assertThat(Uuid.uuidV4Short()).isNotBlank().hasSize(8).matches("[a-z0-9]{8}");
        assertThat(Uuid.uuidV4Stripped()).matches(regex_stripped);
    }
    
    static Stream<String> uuidV3Values() {
       return Stream.of(null, "", "   ", "my-filename.txt");
    }
}

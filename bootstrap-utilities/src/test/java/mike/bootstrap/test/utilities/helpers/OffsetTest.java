package mike.bootstrap.test.utilities.helpers;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import mike.bootstrap.utilities.helpers.Offset;

@DisplayName("Helpers::Offset")
class OffsetTest {

    @ParameterizedTest
    @CsvSource({ "0, 0", "3, 0", "0, 3" })
    void should_throw_IllegalArgumentException_when_position_or_length_less_than_Zero(int pos,
            int len) {
        assertThatIllegalArgumentException().isThrownBy(() -> Offset.of(pos, len));
    }

    @Test
    void should_return_invalid_offset_when_position_and_length_greater_than_zero() {

        assertThat(Offset.of(5, 3)).satisfies(offset -> {
            assertThat(offset.position()).isEqualTo(5);
            assertThat(offset.length()).isEqualTo(3);
            assertThat(offset.startAt()).isEqualTo(4);
            assertThat(offset.endAt()).isEqualTo(7);
            assertThat(offset.toString()).startsWith("Offset [pos=").endsWith("]");
        });

        assertThat(Offset.of()).satisfies(offset -> {
            assertThat(offset.position()).isEqualTo(1);
            assertThat(offset.length()).isEqualTo(1);
            assertThat(offset.startAt()).isZero();
            assertThat(offset.endAt()).isEqualTo(1);
        });

        assertThat(Offset.of(5)).satisfies(offset -> {
            assertThat(offset.position()).isEqualTo(5);
            assertThat(offset.length()).isEqualTo(1);
            assertThat(offset.startAt()).isEqualTo(4);
            assertThat(offset.endAt()).isEqualTo(5);
        });
    }
}

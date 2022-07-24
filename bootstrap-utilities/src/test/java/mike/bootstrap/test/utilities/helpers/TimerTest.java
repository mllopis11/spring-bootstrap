package mike.bootstrap.test.utilities.helpers;

import static org.assertj.core.api.Assertions.*;

import java.util.Locale;
import java.util.regex.Pattern;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import mike.bootstrap.utilities.helpers.Timer;

@DisplayName("Helpers::Timer")
class TimerTest {

    private static final Pattern ELAPSED_PATTERN = Pattern
            .compile("[0-9]\\.[0-9]{3} second\\(s\\)");

    @BeforeAll
    static void init() {
        Locale.setDefault(Locale.ENGLISH);
    }

    @Test
    void should_return_elapsed_duration_when_1_seconds_pause() {

        Timer tm = new Timer();
        Timer.sleep(1);

        assertThat(tm.endTime()).isAfter(tm.startTime());
        assertThat(tm.isStopped()).isFalse();

        var elaps = tm.stop();
        Timer.sleep(1);

        assertThat(tm.isStopped()).isTrue();
        assertThat(tm.elaps()).isEqualByComparingTo(elaps);
        assertThat(tm.elapsToSeconds()).matches(ELAPSED_PATTERN)
                .startsWith(String.valueOf(elaps.getSeconds()));
        assertThat(tm.elapsTime()).matches("00:00:0[0-9]");
        assertThat(tm.upTime()).matches("0 day\\(s\\), 0 hour\\(s\\), 0 min\\., [0-9] sec\\.");

        tm.reset();

        assertThat(tm.isStopped()).isFalse();
    }

    @Test
    void should_return_checkpoint_duration_when_1_seconds_pause() {

        Timer tm = new Timer();
        Timer.sleep(1);

        tm.checkPoint();
        Timer.sleep(1);
        tm.stop();
        var split = tm.split();
        assertThat(tm.elaps().getSeconds()).isEqualTo(2);
        assertThat(split.getSeconds()).isEqualTo(1);

        assertThat(tm.splitToSeconds()).matches(ELAPSED_PATTERN)
                .startsWith(String.valueOf(split.getSeconds()));
    }
}

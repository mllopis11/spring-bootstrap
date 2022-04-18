package mike.bootstrap.test.utilities.helpers;

import static org.assertj.core.api.Assertions.*;

import java.nio.file.attribute.FileTime;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import mike.bootstrap.utilities.helpers.Dates;

@DisplayName("Helpers::Dates")
class DatesTest {

	private static LocalDateTime localDateTime = LocalDateTime.of(2019, 3, 15, 15, 9, 34);
    private static ZonedDateTime zonedDateTime = ZonedDateTime.of(2019, 3, 15, 15, 9, 34, 345000000, ZoneId.systemDefault());
    private static DateTimeFormatter defaultDateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm");
    private static DateTimeFormatter defaultDateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    
    @Test
    void dNow_should_return_formatted_date() {
    	assertThat(Dates.dNow()).isNotEmpty();
    	assertThat(Dates.dNow(defaultDateTimeFormatter)).isNotEmpty();
    	assertThat(Dates.dLogNano()).isNotEmpty();
    }

    @Test
    void dIso_should_return_formatted_date() {
    	assertThat(Dates.dIso()).isNotEmpty();
    }

    @Test
    void dLocal_should_return_formatted_date() {
    	assertThat(Dates.dLocal()).isNotEmpty();
    	assertThat(Dates.dLocal(defaultDateFormatter)).isNotEmpty();
    }

    @Test
    void zNow_should_return_formatted_date() {
    	assertThat(Dates.zNow()).isNotEmpty();
    	assertThat(Dates.zNow(defaultDateTimeFormatter)).isNotEmpty();
    }
    
    @Test
    void timestamp_should_return_Timestamp() {
    	assertThat(Dates.timestamp()).isNotNull();
    }

    @Test
    void toXxxDates_should_return_non_null_object() {
    	assertThat(Dates.toLocalDate(new Date())).isNotNull();
    	assertThat(Dates.toLocalDateTime(new Date())).isNotNull();
    	assertThat(Dates.toZonedDateTime(new Date())).isNotNull();
        
        var fileTime = FileTime.from(zonedDateTime.toInstant());
        assertThat(Dates.toLocalDateTime(fileTime)).isNotNull();
    	assertThat(Dates.toZonedDateTime(fileTime)).isNotNull();
    }
    
    @Test
    void toXxxDateEpoch_should_return_epoch_date_object() {
	var localDateTimeEpoch = LocalDateTime.ofInstant(Instant.EPOCH, ZoneId.systemDefault());
    	
    	assertThat(Dates.toLocalDateTimeEpoch()).isEqualTo(localDateTimeEpoch);
    	assertThat(Dates.toZonedDateTimeEpoch()).isEqualTo(ZonedDateTime.of(localDateTimeEpoch, ZoneId.systemDefault()));
    	assertThat(Dates.timestampEpoch()).isEqualTo(Timestamp.valueOf(localDateTimeEpoch));
    }
    
    @Test
    void format_LocalDateTime_should_return_formatted_date() {
    	assertThat(Dates.format(localDateTime)).isNotEmpty();
    	assertThat(Dates.format(localDateTime, defaultDateTimeFormatter)).isNotEmpty();
    }

    @Test
    void format_ZonedDateTime_should_return_formatted_date() {
	var zonedDateTimeFormatted = Dates.format(zonedDateTime);
    	
    	assertThat(zonedDateTimeFormatted)
    		.isNotEmpty()
    		.isEqualTo("2019-03-15T15:09:34.345+01:00");
        
    	assertThat(Dates.format(zonedDateTime, defaultDateTimeFormatter)).isNotEmpty();
        
    	var summerZonedDateTime = ZonedDateTime.of(2020, 10, 04, 15, 9, 34, 345000000, ZoneId.systemDefault());
        
        assertThat(Dates.format(summerZonedDateTime)).isEqualTo("2020-10-04T15:09:34.345+02:00");        
    }
    
    @Test
    void format_FileTime_should_return_formatted_date() {
	var fileTime = FileTime.from(zonedDateTime.toInstant());

        assertThat(Dates.format(fileTime)).isNotEmpty();
    	assertThat(Dates.format(fileTime, defaultDateTimeFormatter)).isNotEmpty();
    }
    
    @Test
    void format_Date_should_return_formatted_date() {
	var date = new Date();
        assertThat(Dates.format(date)).isNotEmpty();
        assertThat(Dates.format(date, defaultDateTimeFormatter)).isNotEmpty();
        assertThat(Dates.format(date, defaultDateFormatter)).isNotEmpty();
    }
    
    @Test
    void format_Epoch_should_return_formatted_date() {
        long epoch = System.currentTimeMillis();
        assertThat(Dates.format(epoch)).isNotEmpty();
    }
    
    @ParameterizedTest
    @CsvSource({
    	"15-03-2019 03:56, true",
    	"15-03-2019 03:56:06, false"
    })
    void isValid_should_return_expected_boolean_when_date_value(String dateValue, boolean expected) {
    	assertThat(Dates.isValid(dateValue, defaultDateTimeFormatter)).isEqualTo(expected);
    }
}

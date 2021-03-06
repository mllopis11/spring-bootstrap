package mike.bootstrap.utilities.helpers;

import java.nio.file.attribute.FileTime;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Date converter/formatter helpers
 * 
 * @author Mike (2021-02)
 */
public class Dates {

	/**
	 * ZonedDateTimeFormatter as '2020-10-04T15:34:56.345+0100'
	 */
	public static final DateTimeFormatter ISO_DTTM_NANO_OFFSET = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
	
	/**
	 * DateTimeFormatter as '2020-10-04 15:34:56.345'
	 */
    public static final DateTimeFormatter LOG_DTTM_NANO = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
    
    /**
     * Private constructor (Prevent any instantiation)
     */
    private Dates() {}
    
    /**
     * @return current LocalDateTime as {@link DateTimeFormatter#ISO_LOCAL_DATE_TIME}
     * @see DateTimeFormatter#ISO_LOCAL_DATE_TIME
     */
    public static String dNow() {
        return Dates.dNow(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    /**
     * @return current LocalDateTime as 'yyyy-MM-dd HH:mm:ss.SSS'
     * @see #LOG_DTTM_NANO
     */
    public static String dLogNano() {
        return Dates.dNow(LOG_DTTM_NANO);
    }
    
    /**
     * @param formatter the desired output format compatible with LocalDateTime
     * @return current LocalDateTime with the given format
     */
    public static String dNow(DateTimeFormatter formatter) {
        return LocalDateTime.now().format(formatter);
    }
    
    /**
     * @return current LocalDate as 'yyyyMMdd'
     * @see DateTimeFormatter#BASIC_ISO_DATE
     */
    public static String dIso() {
        return Dates.dLocal(DateTimeFormatter.BASIC_ISO_DATE);
    }

    /**
     * @return current LocalDate as yyyy-MM-dd
     * @see DateTimeFormatter#ISO_LOCAL_DATE
     */
    public static String dLocal() {
        return Dates.dLocal(DateTimeFormatter.ISO_LOCAL_DATE);
    }

    /**
     * @param formatter output format
     * @return current LocalDate with the given format
     */
    public static String dLocal(DateTimeFormatter formatter) {
        return LocalDate.now().format(formatter);
    }
    
    /**
     * @return current ZonedDateTime as {@link DateTimeFormatter.ISO_OFFSET_DATE_TIME}
     * @see DateTimeFormatter.ISO_OFFSET_DATE_TIME
     */
    public static String zNow() {
        return Dates.zNow(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    }
    
    /**
     * @param formatter output format
     * @return current ZonedDateTime with the given format
     */
    public static String zNow(DateTimeFormatter formatter) {
        return ZonedDateTime.now().format(formatter);
    }

    /**
     * @return SQL Timestamp from instant now.
     */
    public static Timestamp timestamp() {
    	return Timestamp.from(Instant.now());
    }
    
    /**
     * @param localDateTime local date time
     * @return the given LocalDateTime as {@link DateTimeFormatter.ISO_LOCAL_DATE_TIME}
     * @see DateTimeFormatter#ISO_LOCAL_DATE_TIME
     */
    public static String format(LocalDateTime localDateTime) {
        return Dates.format(localDateTime, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    /**
     * @param localDateTime local date time
     * @param formatter     desired format
     * @return the given LocalDateTime with the given format
     */
    public static String format(LocalDateTime localDateTime, DateTimeFormatter formatter) {
        return localDateTime.format(formatter);
    }
    
    /**
     * @param zonedDateTime zoned date time
     * @return the given ZonedDateTime as {@link DateTimeFormatter.ISO_OFFSET_DATE_TIME}
     * @see DateTimeFormatter#ISO_OFFSET_DATE_TIME
     */
    public static String format(ZonedDateTime zonedDateTime) {
        return Dates.format(zonedDateTime, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    }
    
    /**
     * @param zonedDateTime zoned date time
     * @param formatter     desired format
     * @return the given ZonedDateTime with the given format
     */
    public static String format(ZonedDateTime zonedDateTime, DateTimeFormatter formatter) {
        return zonedDateTime.format(formatter);
    }

    /**
     * @param fileTime file time
     * @return the given FileTime as {@link DateTimeFormatter.ISO_OFFSET_DATE_TIME}
     * @see DateTimeFormatter#ISO_OFFSET_DATE_TIME
     */
    public static String format(FileTime fileTime) {
    	return Dates.format(fileTime, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    }

    /**
     * @param fileTime  file time
     * @param formatter desired output text format
     * @return the given FileTime formatted with the given format
     */
    public static String format(FileTime fileTime, DateTimeFormatter formatter) {
        return formatter.withZone(ZoneId.systemDefault()).format(fileTime.toInstant());
    }

    /**
     * @param date a date
     * @return the given Date as 'yyyy-MM-ddTHH:mm:ss+HHmm'
     * @see #ISO_DTTM_OFFSET
     */
    public static String format(Date date) {
        return Dates.format(date, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    }

    /**
     * @param date      a date
     * @param formatter desired output text format
     * @return the given Date with the given format
     */
    public static String format(Date date, DateTimeFormatter formatter) {
        return formatter.withZone(ZoneId.systemDefault()).format(date.toInstant());
    }

    /**
     * @param epochMillis epoch in milliseconds
     * @return the given Epoch as 'yyyy-MM-ddTHH:mm:ss+HHmm'
     * @see #ISO_DTTM_NANO_OFFSET
     */
    public static String format(long epochMillis) {
        return Dates.format(epochMillis, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    }
    
    /**
     * @param epochMillis epoch in milliseconds
     * @return the given Epoch formatted with the given format
     */
    public static String format(long epochMillis, DateTimeFormatter formatter) {
        return formatter.withZone(ZoneId.systemDefault()).format(Instant.ofEpochMilli(epochMillis));
    }

    /**
     * @param date a date
     * @return the given date converted
     */
    public static LocalDate toLocalDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }
    
    /**
     * @param date a date
     * @return the given date converted
     */
    public static LocalDateTime toLocalDateTime(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }
    
    /**
     * @param date a date
     * @return the given date converted
     */
    public static LocalDateTime toLocalDateTime(FileTime fileTime) {
        return fileTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }
    
    /**
     * @param date a date
     * @return the given date converted
     */
    public static ZonedDateTime toZonedDateTime(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault());
    }
    
    /**
     * @param date a date
     * @return the given date converted
     */
    public static ZonedDateTime toZonedDateTime(FileTime fileTime) {
        return fileTime.toInstant().atZone(ZoneId.systemDefault());
    }
    
    /**
     * Verifies that the value has a valid format regarding the given formatter. 
     * 
     * @param value     a date(time) value
     * @param formatter the format to validate
     * @return true is the date value can be parse otherwise false
     * @see DateTimeFormatter#parse(CharSequence)
     */
    public static boolean isValid(String value, DateTimeFormatter formatter) {
    	
    	try {
    		formatter.parse(value);
    	} catch (DateTimeParseException ex) {
    		return false;
    	}
    	
    	return true;
    }
}

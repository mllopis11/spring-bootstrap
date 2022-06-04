package mike.bootstrap.utilities.helpers;

import java.nio.file.attribute.FileTime;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;

/**
 * Date converter/formatter helpers
 * 
 * @author Mike (2021-02)
 */
public class Dates {

    /**
     * ZonedDateTimeFormatter as '2020-10-04T15:34:56.345+01:00'
     */
    public static final DateTimeFormatter ISO_DTTM_NANO_OFFSET = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXXXX");

    /**
     * DateTimeFormatter as '2020-10-04 15:34:56.345'
     */
    public static final DateTimeFormatter LOG_DTTM_NANO = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    /**
     * Private constructor (Prevent any instantiation)
     */
    private Dates() {}

    /**
     * @return current LocalDateTime as
     *         {@link DateTimeFormatter#ISO_LOCAL_DATE_TIME}
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
     * @return current ZonedDateTime as {@link Dates#ISO_DTTM_NANO_OFFSET}
     * @see Dates#ISO_DTTM_NANO_OFFSET
     */
    public static String zNow() {
	return Dates.zNow(ISO_DTTM_NANO_OFFSET);
    }

    /**
     * @param formatter output format
     * @return current ZonedDateTime with the given format
     */
    public static String zNow(DateTimeFormatter formatter) {
	return ZonedDateTime.now().format(formatter);
    }

    /**
     * @param localDate local date
     * @return the given LocalDate as {@link DateTimeFormatter.ISO_LOCAL_DATE}
     * @see DateTimeFormatter#ISO_LOCAL_DATE_TIME
     */
    public static String format(LocalDate localDate) {
	return Dates.format(localDate, DateTimeFormatter.ISO_LOCAL_DATE);
    }

    /**
     * @param localDateTime local date
     * @param formatter     desired format
     * @return the given LocalDate with the given format
     */
    public static String format(LocalDate localDate, DateTimeFormatter formatter) {
	return localDate.format(formatter);
    }

    /**
     * @param localDateTime local date time
     * @return the given LocalDateTime as
     *         {@link DateTimeFormatter.ISO_LOCAL_DATE_TIME}
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
     * @return the given ZonedDateTime as {@link Dates.ISO_DTTM_NANO_OFFSET}
     * @see Dates#ISO_DTTM_NANO_OFFSET
     */
    public static String format(ZonedDateTime zonedDateTime) {
	return Dates.format(zonedDateTime, ISO_DTTM_NANO_OFFSET);
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
     * @return the given FileTime as {@link Dates.ISO_DTTM_NANO_OFFSET}
     * @see Dates#ISO_DTTM_NANO_OFFSET
     */
    public static String format(FileTime fileTime) {
	return Dates.format(fileTime, Dates.ISO_DTTM_NANO_OFFSET);
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
     * @return the given Date as {@link Dates.ISO_DTTM_NANO_OFFSET}
     * @see Dates#ISO_DTTM_NANO_OFFSET
     */
    public static String format(Date date) {
	return Dates.format(date, Dates.ISO_DTTM_NANO_OFFSET);
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
     * @return the given Epoch as {@link Dates.ISO_DTTM_NANO_OFFSET}
     * @see Dates#ISO_DTTM_NANO_OFFSET
     */
    public static String format(long epochMillis) {
	return Dates.format(epochMillis, Dates.ISO_DTTM_NANO_OFFSET);
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
     * @param long Number of milliseconds from the epoch of 1970-01-01T00:00:00Z
     * @return the given epoch converted
     */
    public static LocalDateTime toLocalDateTime(long millis) {
	return Instant.ofEpochMilli(millis).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    /**
     * @return the EPOCH local datetime (1970-01-01T01:00:00Z)
     */
    public static LocalDateTime toLocalDateTimeEpoch() {
	return LocalDateTime.ofInstant(Instant.EPOCH, ZoneId.systemDefault());
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
     * @param long Number of milliseconds from the epoch of 1970-01-01T01:00:00Z
     * @return the given date converted
     */
    public static ZonedDateTime toZonedDateTime(long millis) {
	return Instant.ofEpochMilli(millis).atZone(ZoneId.systemDefault());
    }

    /**
     * @return the EPOCH zoned datetime (1970-01-01T01:00:00Z)
     */
    public static ZonedDateTime toZonedDateTimeEpoch() {
	return Instant.EPOCH.atZone(ZoneId.systemDefault());
    }

    /**
     * @return SQL Timestamp from instant now.
     */
    public static Timestamp timestamp() {
	return Timestamp.from(Instant.now());
    }

    /**
     * @return SQL Timestamp from instant EPOCH (1970-01-01T01:00:00Z).
     */
    public static Timestamp timestampEpoch() {
	return Timestamp.from(Instant.EPOCH);
    }

    /**
     * @param localDateTime A local date-time
     * @return the number of milliseconds from the epoch of 1970-01-01T01:00:00Z
     */
    public static long toEpochMillis(LocalDateTime localDateTime) {
	return Dates.toEpochMillis(localDateTime.atZone(ZoneId.systemDefault()));
    }

    /**
     * @param zonedDateTime A zoned date-time
     * @return the number of milliseconds from the epoch of 1970-01-01T01:00:00Z
     */
    public static long toEpochMillis(ZonedDateTime zonedDateTime) {
	return zonedDateTime.toEpochSecond() * 1000;
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

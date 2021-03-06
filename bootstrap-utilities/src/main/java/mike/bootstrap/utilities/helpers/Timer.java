package mike.bootstrap.utilities.helpers;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.TimeUnit;

/**
 * Timer allows to evaluate elapsed time between two check-points.
 * 
 * @author Mike (2021-02)
 *
 */
public class Timer {

	private static final String SECONDS_FORMAT = "%.03f second(s)";

	private Instant startTime = Instant.now();
	private Instant splitTime = startTime;
	private Duration elapsedTime = Duration.ofMillis(0);
	private Duration splittedTime = Duration.ofMillis(0);
	private boolean stopped = false;

	/**
	 * Reset the timer to the current time
	 */
	public void reset() {
		startTime = Instant.now();
		splitTime = startTime;
		elapsedTime = Duration.ofMillis(0);
		splittedTime = Duration.ofMillis(0);
	}

	/**
	 * Stop the timer
	 * 
	 * @return elapsed duration in milliseconds
	 */
	public Duration stop() {
		elaps();
		split();
		stopped = true;
		return elapsedTime;
	}

	/**
	 * @return Timer start time
	 */
	public Instant startTime() {
		return startTime;
	}

	/**
	 * @return Elapsed duration
	 */
	public Duration elaps() {
		if (!stopped) {
			elapsedTime = Duration.between(startTime, Instant.now());
		}

		return elapsedTime;
	}

	/**
	 * @return Elapsed duration since the last checkpoint
	 */
	public Duration split() {
		if (!stopped)
			splittedTime = Duration.between(splitTime, Instant.now());

		return splittedTime;
	}

	/**
	 * Reset the last checkpoint
	 */
	public void checkPoint() {
		splitTime = Instant.now();
	}

	/**
	 * @return elapsed time in seconds.
	 */
	public double seconds(Duration time) {
		return time.toMillis() / 1000.;
	}

	/**
	 * @return total elapsed time in seconds.
	 */
	public double seconds() {
		return elaps().toMillis() / 1000.;
	}

	/**
	 * @return total elapsed time formatted as: 0.000 second(s)
	 */
	public String toSeconds() {
		return String.format(SECONDS_FORMAT, this.seconds());
	}

	/**
	 * @return time formatted as: 0.000 second(s)
	 */
	public String toSeconds(Duration time) {
		return String.format(SECONDS_FORMAT, this.seconds(time));
	}

	/**
	 * @return Split duration formatted as: 0.000 second(s)
	 */
	public String splitToSeconds() {
		return String.format(SECONDS_FORMAT, this.seconds(split()));
	}

	/**
	 * @return Split duration (format: hh:mm:ss:ms)
	 */
	public String splitTime() {
		return this.elapsTime(split().toMillis());
	}

	/**
	 * @return elapsed time (format: hh:mm:ss:ms)
	 */
	public String elapsTime() {
		return this.elapsTime(elaps().toMillis());
	}

	/**
	 * @param millis duration in milliseconds
	 * @return elapsed time (format: hh:mm:ss:ms)
	 */
	public String elapsTime(long millis) {
		return String.format("%02d:%02d:%02d.%03d", TimeUnit.MILLISECONDS.toHours(millis) % TimeUnit.DAYS.toHours(1),
				TimeUnit.MILLISECONDS.toMinutes(millis) % TimeUnit.HOURS.toMinutes(1),
				TimeUnit.MILLISECONDS.toSeconds(millis) % TimeUnit.MINUTES.toSeconds(1),
				millis % TimeUnit.SECONDS.toMillis(1));
	}

	/**
	 * @return elapsed time (format: days, hours, min., sec.)
	 */
	public String upTime() {
		return this.upTime(elaps().toMillis());
	}

	/**
	 * @param millis duration in milliseconds
	 * @return elapsed time (format: days, hours, min., sec.)
	 */
	public String upTime(long millis) {
		return String.format("%d day(s), %02d hour(s), %02d min., %02d sec.", TimeUnit.MILLISECONDS.toDays(millis),
				TimeUnit.MILLISECONDS.toHours(millis) % TimeUnit.DAYS.toHours(1),
				TimeUnit.MILLISECONDS.toMinutes(millis) % TimeUnit.HOURS.toMinutes(1),
				TimeUnit.MILLISECONDS.toSeconds(millis) % TimeUnit.MINUTES.toSeconds(1));
	}

	/**
	 * @param seconds number of seconds to sleep
	 */
	public void pause(int seconds) {
		try {
			TimeUnit.SECONDS.sleep(seconds);
		} catch (InterruptedException ie) {
			/* Ignored */ }
	}
}

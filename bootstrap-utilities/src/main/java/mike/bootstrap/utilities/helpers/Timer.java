package mike.bootstrap.utilities.helpers;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * Timer allows to evaluate elapsed time between two check-points.
 * 
 * @author Mike (2021-02)
 *
 */
public class Timer {

    private static final String SECONDS_FORMAT = "%.03f second(s)";

    private Instant startTime = Instant.now();
    private boolean stopped = false;
    private Duration elapsDuration = Duration.ofMillis(0);
    private Instant checkPoint = startTime;
    private Duration checkPointDuration = Duration.ofMillis(0);

    /**
     * Perform a sleep.
     * 
     * @param seconds number of seconds to sleep
     * @see TimeUnit#sleep(long)
     */
    public static void sleep(int seconds) {

        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Reset the timer to the current time
     */
    public void reset() {
        this.startTime = Instant.now();
        this.stopped = false;
        this.elapsDuration = Duration.ofMillis(0);
        this.checkPoint = this.startTime;
        this.checkPointDuration = Duration.ofMillis(0);
    }

    /**
     * @return true if the timer is stopped (i.e. the method {@link Timer#stop()} has been invoked)
     */
    public boolean isStopped() {
        return this.stopped;
    }

    /**
     * Stop the timer.
     * 
     * @return elapsed duration
     */
    public Duration stop() {
        elaps();
        split();
        this.stopped = true;
        return this.elapsDuration;
    }

    /**
     * @return Timer start time
     */
    public Instant startTime() {
        return this.startTime;
    }

    /**
     * @return Timer end time (start
     */
    public Instant endTime() {
        return this.startTime().plus(this.elaps());
    }

    /**
     * Reset the last checkpoint
     */
    public void checkPoint() {
        this.checkPoint = Instant.now();
    }

    /**
     * @return Elapsed duration since the last checkpoint
     */
    public Duration split() {

        if (!this.isStopped()) {
            this.checkPointDuration = Duration.between(this.checkPoint, Instant.now());
        }

        return checkPointDuration;
    }

    /**
     * @return Split duration formatted as: 0.000 second(s)
     */
    public final Supplier<String> splitInSeconds = () ->
            String.format(SECONDS_FORMAT, this.toSeconds(this.split()));

    /**
     * @return Cuurent elapsed duration
     */
    public Duration elaps() {

        if (!this.isStopped()) {
            this.elapsDuration = Duration.between(this.startTime, Instant.now());
        }

        return this.elapsDuration;
    }

    /**
     * @return current elapsed time formatted as: 0.000 second(s)
     */
    public final Supplier<String> elapsInSeconds = () ->
            String.format(SECONDS_FORMAT, this.toSeconds(this.elaps()));

    /**
     * @return elapsed time (format: hh:mm:ss:ms)
     */
    public final Supplier<String> elapsTime = () -> {
        var millis = this.elaps().toMillis();
        return String.format("%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(millis) % TimeUnit.DAYS.toHours(1),
                TimeUnit.MILLISECONDS.toMinutes(millis) % TimeUnit.HOURS.toMinutes(1),
                TimeUnit.MILLISECONDS.toSeconds(millis) % TimeUnit.MINUTES.toSeconds(1));
    };

    /**
     * @return elapsed time (format: days, hours, min., sec.)
     */
    public final Supplier<String> upTime = () -> {
        var millis = this.elaps().toMillis();

        return String.format("%d day(s), %d hour(s), %d min., %d sec.",
                TimeUnit.MILLISECONDS.toDays(millis),
                TimeUnit.MILLISECONDS.toHours(millis) % TimeUnit.DAYS.toHours(1),
                TimeUnit.MILLISECONDS.toMinutes(millis) % TimeUnit.HOURS.toMinutes(1),
                TimeUnit.MILLISECONDS.toSeconds(millis) % TimeUnit.MINUTES.toSeconds(1));
    };

    /* ****************************** PRIVATE METHODS ****************************** */

    /**
     * @return elapsed time in seconds.
     */
    private double toSeconds(Duration time) {
        return time.toMillis() / 1000.;
    }
}

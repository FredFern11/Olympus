package Apollo;

import java.time.Instant;
import java.util.Arrays;
import java.util.LinkedList;

/**
 * Instants are recorded and can be further analysed
 */
public class TimeFrame {
    enum Unit {milli, second, minute, hour}
    private static long origin;
    private LinkedList<Long> moments;

    public TimeFrame() {
        moments = new LinkedList<>();
        save();
    }

    public LinkedList<Long> getMoments() {
        return moments;
    }

    /**
     * Save this instant.
     */
    public void save() {
        moments.add(Instant.now().toEpochMilli() - origin);
    }

    /**
    Start the clock so that it represent time 0.
     */
    public static void setOrigin() {
        origin = Instant.now().toEpochMilli();
    }

    /**
     * Get the elapsed time between the first and last instant saved
     * @param unit time mesurement unit (milliseconds, seconds, minutes or hour)
     * @return the time elapsed
     */
    public long elapsed(Unit unit) {
        if (unit.equals(Unit.milli)) return milliElapsed();
        else if (unit.equals(Unit.second)) return milliElapsed()/1000;
        else if (unit.equals(Unit.minute)) return milliElapsed()/1000/60;
        else if (unit.equals(Unit.hour)) return milliElapsed()/1000/60/60;
        return -1;
    }


    /**
     * Get the elapsed time between every instant saved
     * @return array containing the elapsed point between the instant n+1 and n
     */
    public long[] analyse() {
        long[] elapsed = moments.stream().mapToLong(Long::longValue).toArray();
        for (int i = 0; i < elapsed.length-1; i++) {
            elapsed[i] = elapsed[i+1] - elapsed[i];
        }
        return Arrays.copyOf(elapsed, elapsed.length-1);
    }

    private long milliElapsed() {
        return moments.getLast() - moments.getFirst();
    }

    /**
     * Creates a new TimeFrame containing the same instants
     * @return TimeFrame containing the same instants
     */
    @Override
    public TimeFrame clone() {
        TimeFrame timeFrame = new TimeFrame();
        timeFrame.moments = this.moments;
        return timeFrame;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " " + moments;
    }
}

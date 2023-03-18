package Hermes;

import Apollo.TimeFrame;

/**
 * Answer to a {@link Request}. It provides all the data requested.
 */
public class Reply {
    private final Reference reference;
    private Result[] results;
    private TimeFrame timeFrame;

    public Reply(Reference reference, Result[] results, TimeFrame timeFrame) {
        this.reference = reference;
        this.results = results;
        this.timeFrame = timeFrame;
    }

    /**
     * Add a result to the reply repository
     * @param result result to be sent
     * @param location same index as the protocol in the request
     */
    public void add(Result result, int location) {
        results[location] = result;
    }

    /**
     * Check if the reply possesses as many results as the request
     * @return if all the results are stored
     */
    public boolean full() {
        for (Result result: results) {
            if (result == null) return false;
        }
        return true;
    }


    public TimeFrame getTimeFrame() {
        return timeFrame;
    }

    public Reference getReference() {
        return reference;
    }

    public Result getResult(int location) {
        return results[location];
    }


    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || object.getClass() != getClass()) return false;
        return reference.equals(((Reply) object).getReference());
    }

    @Override
    public String toString() {
        return "Reply <" + reference + ">";
    }
}

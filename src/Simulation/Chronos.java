package Simulation;

public class Chronos {
    private long start;
    private int convert;

    public Chronos(int convert) {
        start = System.currentTimeMillis();
        this.convert = convert;
    }

    public int getTimeSlot() {
        return (int) (realElapsed() / convert);
    }

    public long realElapsed() {
        return System.currentTimeMillis() - start;
    }

    public Date getDate() {
        return new Date(getTimeSlot() * convert);
    }


}

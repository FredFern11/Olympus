package Hermes;

import Apollo.TimeFrame;

/**
 * Data retrieve after performing an entire {@link Protocol} on a subordinate function.
 */
public class Result {
    private final Reference pack;
    private final int location;
    private Object[] assets;
    private final Reference reference;
    private TimeFrame timeFrame;

    public Result(Object[] assets, Reference reference, Reference pack, int location, TimeFrame timeFrame) {
        this.assets = assets;
        this.reference = reference;
        this.pack = pack;
        this.location = location;
        this.timeFrame = timeFrame;
    }

    /**
     * Add a new asset to this result.
     * @param asset data returned by the subordinate function perfomring the protocol
     * @param location index of the protocol in the request
     */
    public void add(Object asset, int location) {
        assets[location] = asset;
    }


    public int getLenght() {
        return assets.length;
    }

    public int getLocation() {
        return location;
    }

    public Reference getPack() {
        return pack;
    }

    public Reference getReference() {
        return reference;
    }

    public Object getAsset(int location) {
        return assets[location];
    }

    public TimeFrame getTimeFrame() {
        return timeFrame;
    }


    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || object.getClass() != getClass()) return false;
        return reference.equals(((Result) object).getReference());
    }
}

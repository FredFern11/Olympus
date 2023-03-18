package Gaia;

import Apollo.Point;

public class Soil extends Block{
    public int contamination = 0;
    public int stability = 0;
    public int mineral = 0;
    public int humidity = 0;
    public int temperature = 0;

    public Soil(Point position) {
        super(position);
    }

    /**
     * Get an array containing all the data from the block.
     * @return an array containing contamination, stability, mineral, humidity and temperature level.
     */
    public int[] extract() {
        return new int[]{contamination, stability, mineral, humidity, temperature};
    }
}

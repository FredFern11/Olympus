package Gaia;

import Apollo.Map;
import Apollo.Point;

import java.util.Random;

public class Lithosphere extends Map<Soil> {

    public Lithosphere(int width, int height) {
        super(width, height, Soil.class);
        Random random = new Random();
        // generate a number of mine center (point with the highest concentration of metal)
        Point[] mineralMap = mineGen();
        // generate in the soil surrounding the centers different amount of metal concentration
        for (int i = 0; i < mineralMap.length; i++) {
            disperse(mineralMap[i], random.nextInt(9,13));
        }
    }

    public void run() {
        while (true) {
            Random random = new Random();
            Soil soil = new Soil(new Point(75, 37));
            soil.mineral = random.nextInt(100);
            setBlock(soil);
            try {
                Thread.sleep(150);
            } catch (Exception e) {
                System.out.println(e.getStackTrace());
            }
        }
    }

    /**
     * Disperse the minerals around a center creating a mine. THe minerals are disperced following a
     * circular pattern with each ring having less and less minerals as the distance grow between the center and the ring.
     * @param center center of the mine where the concentration of mineral will be 100
     * @param radius number of concentric squares around the center
     */
    private void disperse(Point center, int radius) {
        // contains the places visited when we traverse counter clock wise the map around the center
        Soil[] soils = rotate(center, radius).toArray(new Soil[0]);
        for (Soil place : soils) {
            if (place == null) return;
            Soil soil = place;
            // find the ring on which the block exist
            int ring = Math.max(Math.abs(soil.position.y - center.y), Math.abs(soil.position.x - center.x));
            // calculate the amount of mineral that the soil should possess according to the distance (or ring) from the center of the mine
            soil.mineral = mineralAmount(ring, radius);
            // TODO: 2022-12-14 because it is reference maybe not need to set block
            setBlock(soil);
        }
    }

    /**
     * Generate the concentration of mineral of a given block. The greater the distance between the bloc and the origin, represented by the ring,
     * the lesser the amount of mineral. The amount of mineral in the same ring follow a normal distribution.
     * @param ring number of block between the center and the block
     * @param radius total number of concentric squares
     * @return mineral concentration of the block
     */
    private int mineralAmount(int ring, int radius) {
        Random random = new Random();
        // the mean is a linear function depending on the ring -> mean [0, 90]
        int mean = 90 - (90/(radius-1)) * (ring-1);
        // from a normally distributed set, generate concentration
        int concentration = (int) random.nextGaussian(mean, 9);
        // return the amount below the max but to scale -> 110 is 90
        if (concentration > 100) {return 2*mean - concentration;}
        else if (concentration < 0) {return 0;}

        if (concentration > 100) {
            System.out.println("Concentration is more than 100");
            throw new RuntimeException("Concentration is more than 100");
        }
        return concentration;
    }

    /**
     * Check the proximity of two mine centers
     * @param point1 origin of the first mine
     * @param point2 origin of the second mine
     * @param radius minimal distance
     * @return if the mine centers are closer than the radius
     */
    private boolean checkProx(Point point1, Point point2, int radius) {
        return point1.distance(point2) > radius;
    }

    /**
     * Generate a random point in map with padding of 10 blocks
     * @return random point withing the maps bounds
     */
    private Point genPoint() {
        Random random = new Random();
        // point has to be inside the map with padding of 10 blocks
        int x = random.nextInt(10, width-10);
        int y = random.nextInt(10, height-10);
        return new Point(x, y);
    }

    /**
     * Create mine centers with maximum concentration of mineral.
     * @return centers of the mines
     */
    private Point[] mineGen() {
        Point[] mineralMap = centerGen();
        while (mineralMap == null) {
            mineralMap = centerGen();
        }
        // concentration is 100 for mine centers
        for (int i = 0; i < mineralMap.length; i++) {
            Point center = mineralMap[i];
            Soil block = getBlock(center);
            // TODO: 2022-11-19 investigate why changing mineral to 200 doesn't throw error.
            //  This code is actually irrelevant because the value of the mine center gets change later.
            block.set("mineral", 100);
            setBlock(block);
        }
        return mineralMap;
    }

    /**
     * Find 2 or 3 mine centers at locations that are at least the map width divided by 3 far appart from each other
     * @return mine centers
     */
    private Point[] centerGen() {
        // epicenters refers to the center of the mine with concentration of 100
        Random random = new Random();
        // choose 2 to 4 points randomly
        Point[] epicenters = new Point[random.nextInt(2, 4)];
        // tries keep track of the amount of points tried to embrace the constraint (distance between epicenters)
        int tries = 0;
        // find the random amount of epicenters stated above
        for (int i = 0; i < epicenters.length; i++) {
            boolean searching = true;
            // search for a point that would qualify to be an epicenter
            while (searching) {
                // if this configuration of points do not work, start over
                if (tries > 100) {return null;}
                // check how many epicenters respect the contraints with respect to the new epicenter
                int match = 0;
                // generate a new random point on the map
                Point position = genPoint();

                // check with every other epicenters
                for (int j = 0; j < i; j++) {
                    // if the epicenter is too close to the others
                    if (!checkProx(epicenters[j], position, width/3)) {
                        tries++;
                        break;
                    } else {match++;}
                }
                // if the epicenter is validly distanced from the others
                if (match == i) {
                    // new epicenter is the point
                    epicenters[i] = position;
                    // it has found a point, so it isn't searching anymore
                    searching = false;
                }
            }
        }
        return epicenters;
    }
}

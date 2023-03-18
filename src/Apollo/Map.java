package Apollo;

import Gaia.Block;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class Map <T extends Block> {
    /*          _ _ _ _ (+) Down is positive and right is positive
                |(0, 0)     When displacement in y is positive it goes down
                |
                |
                (+)
     */

    private T[][] terrain;
    public final int width;
    public final int height;
    private Queue<T> changes = new LinkedList<>();

    public Map(int width, int height, Class<? extends Block> clazz) {
        this.width = width;
        this.height = height;
        this.terrain = (T[][]) new Block[height][width];

        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                try {
                    terrain[i][j] = (T) clazz.getDeclaredConstructor(Point.class).newInstance(new Point(j, i));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public Map(Map<T> map) {
        this.height = map.height;
        this.width = map.width;
        this.terrain = map.terrain;
    }

    // return the block on the map at a location
    public T getBlock (Point point) {
        return terrain[point.y][point.x];
    }

    // change a block of the map
    public void setBlock(T block) {
        terrain[block.position.y][block.position.x] = block;
        changes.add(block);
    }

    /**
     * Get all the blocks surrounding the origin. It collect the block counter clockwise starting from the top of the origin.
     *
     * @param origin axis of rotation
     * @param radius number of concentric squares around the origin
     * @return array of block with the first element being the block at origin
     */
    public ArrayList<T> rotate(Point origin, int radius) {
        // calculate the max amount of blocks that can be enclosed by a radius
        int maxLenght = 4 * radius * (radius + 1) + 1;
        // contains all the blocks visited during the traveling
        T[] places = (T[]) new Block[maxLenght];
        places[0] = getBlock(origin);

        double pi = Math.PI;
        int index = 1;

        // second place is below the origin
        Point position = new Point(origin.x, origin.y+1);

        while (true) {
            // check if the block is inside the bounds of the map
            if (position.y >= 0 && position.y < this.height && position.x >= 0 && position.x < this.width) {
                // add the block to the array of visited blocks
                Block block = getBlock(position);
                places[index++] = (T) block;
            }
            // displacement in the x-axis
            double deltaX = position.x-origin.x;
            // displacement in the y-axis
            double deltaY = position.y-origin.y;
            // if total displacement is straight up -> angle = pi/2
            // if total displacement is straight down -> angle = -pi/2
            // otherwise angle = arc tan(y displacement / x displacement)
            double rawAngle = deltaX == 0 ? pi/2 * deltaY/Math.abs(deltaY) : Math.atan(deltaY/deltaX);
            // because of the range restriction of inverse trigonometric functions, adjustments need to be made
            double angle = deltaX < 0 ? pi + rawAngle : rawAngle;
            // make angle positive
            if (angle < 0) {angle += 2 * pi;}
            // at the end of this inside rotation
            if (angle == pi/4) {
                // distance from the center of the mine to the block
                double distance1 = position.distance(origin);
                // radius of the last circle = radius of the first circle (sqrt(2)) scaled with the amount of circles
                double distance2 = radius * Math.sqrt(2);
                // check if the block is inside the last concentric square -> stops revolving if yes
                // because functions aren't exact, we look if they have fairly close output
                if (Math.abs(distance1 - distance2) < 0.00001) {break;}
                // go to the next concentric square
                position.y++;
                continue;
            }
            // see the diagram to right representing the flow of positions
            if (angle >= pi/4 && angle < 3*pi/4) {position.x--;}             /* ←←←←← */
            else if (angle >= 3*pi/4 && angle < 5*pi/4) {position.y--;}      /* ↓   ↑ */
            else if (angle >= 5*pi/4 && angle < 7*pi/4) {position.x++;}      /* ↓   ↑ */
            else {position.y++;}                                             /* →→→→→ */
        }
        return clean(places);
    }

    /**
     * If rotates is called on a point near the borders, the block out of bounds
     * will be collected as null. This function removes those null blocks
     *
     * @param array generate by {@link #rotate(Point, int)}
     * @return array of blocks without any being null
     */
    private ArrayList<T> clean(T[] array) {
        ArrayList<T> list = new ArrayList<>();
        Arrays.stream(array).forEach(e -> {if (e != null) list.add(e);});
        return list;
    }

    /**
     * Get the changes made to this map
     * @return the blocks that have been changed
     */
    public Queue<T> getChanges() {
        Queue<T> temp = changes;
        changes = new LinkedList<>();
        return temp;
    }

    public Map<T> clone() {
        return new Map<T>(this);
    }
}

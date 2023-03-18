package Apollo;

import java.util.Objects;

/**
 * Point represents a coordinate in space.
 */
public class Point {
    public int x;
    public int y;
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Find the distance between two points
     * @param point
     * @return a positive distance
     */
    public double distance(Point point) {
        return Math.sqrt(Math.pow((this.y - point.y), 2) + Math.pow((this.x - point.x), 2));
    }

    /**
     * Creates a new point containing the same coordinates.
     * @return point with same coordinates
     */
    public Point clone() {
        return new Point(this.x, this.y);
    }

    public String toString() {
        return getClass().getSimpleName() + "(" + this.x + "," + this.y + ")";
    }

    /**
     * Displace a point. Basically adding two vectors.
     * Will move the point (x, y) to (x+a, y+b).
     * @param displacement
     * @return moved point
     */
    public Point offset(Point displacement) {
        this.x += displacement.x;
        this.y += displacement.y;
        return this;
    }

    /**
     * Displace a point. Basically vector scaling.
     * Will move the point (x, y) to (ax, ay)
     * @param lambda
     * @return moved point
     */
    public Point scale(int lambda) {
        this.x *= lambda;
        this.y *= lambda;
        return this;
    }

    /**
     * Check if this object possess the same coordinates as the object
     * @param object
     * @return if this possesses the same coordinates as the object
     */
    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        Point point = (Point) object;
        return x == point.x && y == point.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}

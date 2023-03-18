package Athena;

import Apollo.Point;
import Gaia.Athmosphere;
import Gaia.Lithosphere;
import Gaia.Soil;

public class Change {
    private final Class<?> sphere;
    private final int timeSlot;
    private final Point position;
    private final String attribute;
    private final int value;

    public Change(Class<?> sphere, int timeSlot, Point position, String attribute, int value) {
        this.sphere = sphere;
        this.timeSlot = timeSlot;
        this.position = position;
        this.attribute = attribute;
        this.value = value;
    }

    public Class<?> getSphere() {
        return sphere;
    }

    public int getTimeSlot() {
        return timeSlot;
    }

    public Point getPosition() {
        return position;
    }

    public String getAttribute() {
        return attribute;
    }

    public int getValue() {
        return value;
    }
}

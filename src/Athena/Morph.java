package Athena;

import Gaia.Athmosphere;
import Gaia.Lithosphere;

import java.util.LinkedList;

public class Morph {
    private int timeSlot;
    private LinkedList<Change> lithosphere;
    private LinkedList<Change> athmosphere;

    public Morph(int timeSlot) {
        this.timeSlot = timeSlot;
        this.lithosphere = new LinkedList<>();
        this.athmosphere = new LinkedList<>();
    }

    public Morph add(Change change) {
        if (change.getSphere() == Lithosphere.class) lithosphere.add(change);
        else if (change.getSphere() == Athmosphere.class) athmosphere.add(change);
        return this;
    }

    public LinkedList<Change> getAthmo() {
        return athmosphere;
    }

    public LinkedList<Change> getLitho() {
        return lithosphere;
    }
}

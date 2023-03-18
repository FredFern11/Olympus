package Athena;

import Apollo.Map;
import Gaia.Air;
import Gaia.Athmosphere;
import Gaia.Lithosphere;
import Gaia.Soil;

import java.util.Arrays;

public class State {
    private Map<Soil> lithosphere;
    private Map<Air> athmosphere;
    private int timeSlot;

    public State(Map<Soil> lithosphere, Map<Air> athmosphere, int timeSlot) {
        this.lithosphere = lithosphere.clone();
        this.athmosphere = athmosphere.clone();
        this.timeSlot = timeSlot;
    }

    public void collapse(Morph morph) {
        while (!morph.getLitho().isEmpty()) {
            modify(morph.getLitho().remove());
        }

        while (!morph.getAthmo().isEmpty()) {
            modify(morph.getAthmo().remove());
        }
    }

    public void modify(Change change) {
        Map map = null;
        if (change.getSphere() == Lithosphere.class) {
            map = lithosphere;
        } else if (change.getSphere() == Athmosphere.class) {
            map = athmosphere;
        }

        map.getBlock(change.getPosition()).set(change.getAttribute(), change.getValue());
    }

    public Map<Soil> getLithosphere() {
        return lithosphere.clone();
    }

    public Map<Air> getAthmosphere() {
        return athmosphere.clone();
    }

    public int getTimeSlot() {
        return timeSlot;
    }
}

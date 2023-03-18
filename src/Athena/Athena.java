package Athena;

import Hermes.Channel;

import java.util.LinkedList;

public class Athena {
    private Channel channel;
    private LinkedList<Morph> evolution;
    public LinkedList<State> states;

    public Athena() {
        channel = new Channel(this);
        evolution = new LinkedList<>();
        states = new LinkedList<>();
    }

    public void addState(State state) {
        states.add(state);
    }

    public void addMorph(Morph morph) {
        evolution.add(morph);
    }
}

package Gaia;

import Athena.*;
import Hermes.Channel;
import Hermes.Request;

/**
 * Subordinate function in charge of simulating earth. It will simulate the lithosphere, the biosphere,
 * and the atmosphere.
 */
public class Gaia {
    private Channel channel;

    private Lithosphere lithosphere;
    private Athmosphere athmosphere;

    public Gaia(int width, int height) {
        channel = new Channel(this);

        lithosphere = new Lithosphere(width, height);
        athmosphere = new Athmosphere(width, height);


        State state = new State(lithosphere, athmosphere, 0);

        Request request = channel.generate(Athena.class).add(0, "addState", state);
        channel.send(request);
    }
}

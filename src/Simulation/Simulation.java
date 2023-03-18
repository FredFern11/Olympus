package Simulation;

import Athena.Athena;
import Gaia.Gaia;
import Theia.Theia;

import java.awt.*;

// TODO: 2022-12-17 Implement Athena Architecture (Modify Theia)
// TODO: 2022-12-17 Implement diffie-Helman key exchange + AES encryption

// TODO: 2022-10-07 Simplify, format, clean and comment code
// TODO: 2022-09-24 Test all the above before moving to the construction of initial map

// TODO: 2022-09-24 Add all the attributes of block via a generating function
// TODO: 2022-09-24 Change Theia so that she can display different attribute via buttons
public class Simulation {
    public static void main(String[] args) {
        Athena athena = new Athena();
        Gaia gaia = new Gaia(150, 75);
//        Theia theia = new Theia(150, 75);
        System.out.println(athena.states.getFirst());
    }
}

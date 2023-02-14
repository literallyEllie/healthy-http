package de.elliepotato.hhttp;

import de.elliepotato.hhttp.bootstrap.ServerBootstrap;

/**
 * A Javalin web server.
 * </br>
 * Includes additional wellness probes.
 */
public class Main {

    public static void main(String[] args) {
        ServerBootstrap.run();
    }

}

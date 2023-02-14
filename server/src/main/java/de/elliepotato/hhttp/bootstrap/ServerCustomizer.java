package de.elliepotato.hhttp.bootstrap;

import io.javalin.Javalin;

import java.util.function.Consumer;

/**
 * A way to modify and set up a Javalin instance.
 */
@FunctionalInterface
public interface ServerCustomizer extends Consumer<Javalin> {
}

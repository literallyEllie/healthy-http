package de.elliepotato.hhttp.probe;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

/**
 * Represents something which can be probed.
 */
public interface ComponentProbe {

    /**
     * @return ID of the component.
     */
    @NotNull
    String id();

    /**
     * @return If this component is critical.
     */
    boolean critical();

    /**
     * @return Probe the response and return a response.
     */
    CompletableFuture<ComponentStatus> probe();

}

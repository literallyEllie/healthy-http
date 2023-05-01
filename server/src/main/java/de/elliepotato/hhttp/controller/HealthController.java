package de.elliepotato.hhttp.controller;

import de.elliepotato.hhttp.probe.ComponentState;
import de.elliepotato.hhttp.probe.ComponentStatus;
import de.elliepotato.hhttp.probe.HealthProbeResponse;
import io.avaje.http.api.Controller;
import io.avaje.http.api.Get;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import jakarta.inject.Inject;
import de.elliepotato.hhttp.probe.ComponentProbe;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Endpoint to probe the health of a service.
 */
@Controller("/health")
public class HealthController {
    private static final Logger LOGGER = LoggerFactory.getLogger(HealthController.class);

    private final Map<String, ComponentProbe> probes;

    @Inject
    public HealthController(List<ComponentProbe> probes) {
        this.probes = probes.stream()
                .collect(Collectors.toMap(componentProbe -> componentProbe.id().toLowerCase(), Function.identity()));
    }

    /**
     * Health probe provider for a service.
     * </br>
     * This probes all the components asynchronously,
     * then returns the response.
     * </br>
     * The http code will reflect the {@link HealthProbeResponse#masterState()},
     * where UP is 200 OK, and DOWN is 503 Service Unavailable.
     * </br>
     * If there is an error probing a component, it will be marked as DOWN,
     * with the error attached.
     *
     * @param ctx Request context.
     */
    @Get
    @NotNull
    public CompletableFuture<HealthProbeResponse> probe(Context ctx) {
        Map<String, CompletableFuture<ComponentStatus>> futureStatuses = probes.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, o -> o.getValue().probe()));

        return CompletableFuture.allOf(futureStatuses.values().toArray(new CompletableFuture[0]))
                .thenApply(unused -> {
                    Map<String, ComponentStatus> statuses = new HashMap<>(futureStatuses.size());

                    futureStatuses.forEach((key, future) -> {
                        ComponentStatus status;

                        try {
                            status = future.join();
                        } catch (Throwable e) {
                            LOGGER.error("failed to probe {}", key);
                            e.printStackTrace();

                            status = ComponentStatus.down(e.getMessage())
                                    .critical(probes.get(key).critical()).build();
                        }

                        // reflect in http status.
                        if (status.state() == ComponentState.DOWN && status.isCritical()) {
                            ctx.status(HttpStatus.SERVICE_UNAVAILABLE);
                        }

                        statuses.put(key, status);
                    });

                    return new HealthProbeResponse(statuses);
                });
    }

    /**
     * Query liveness for the service.
     * </br>
     * This is just the {@link HealthController#probe(Context)}},
     * but reduced to a primitive status report with a single
     * component "liveness", reflecting the master state of all other components.
     *
     * @param ctx Request context.
     * @return Health response.
     * @see HealthController#liveness(Context)
     */
    @Get("/liveness")
    @NotNull
    public CompletableFuture<HealthProbeResponse> liveness(Context ctx) {
        return probe(ctx).thenApply(healthProbeResponse -> {
            ComponentState state = healthProbeResponse.masterState();

            return new HealthProbeResponse(
                    state,
                    Map.of("liveness", ComponentStatus.builder().status(state).build())
            );
        });
    }

    /**
     * Health probe provider for a component in a service.
     * </br>
     * If there is no such probe, it will return an error and a 404 Not Found.
     * </br>
     * Otherwise, the http code will reflect the {@link ComponentStatus#state()}},
     * where UP is 200 OK, and DOWN is 503 Service Unavailable.
     * </br>
     * If there is an error probing the component, it will be marked as DOWN,
     * with the error attached.
     *
     * @param ctx Request context.
     * @param componentId Component id to get the status of.
     * @return Health response.
     */
    @Get("/{componentId}")
    @NotNull
    public CompletableFuture<ComponentStatus> probe(Context ctx, @NotNull String componentId) {
        ComponentProbe component = probes.get(componentId.toLowerCase());
        if (component == null) {
            ctx.status(HttpStatus.NOT_FOUND);
            return CompletableFuture.failedFuture(new NullPointerException("no such component"));
        }

        return component.probe()
                .exceptionally(throwable -> ComponentStatus.down(throwable.getMessage())
                        .critical(component.critical()).build())
                .thenApply(status -> {

                    // reflect in http status
                    if (status.state() == ComponentState.UP) {
                        ctx.status(HttpStatus.OK);
                    } else if (status.state() == ComponentState.DOWN) {
                        ctx.status(HttpStatus.SERVICE_UNAVAILABLE);
                    }

                    return status;
                });
    }

}

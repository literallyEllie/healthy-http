package de.elliepotato.hhttp.probe;

import io.avaje.jsonb.Json;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

/**
 * Overall health response of a service.
 */
@Json
public record HealthProbeResponse(ComponentState state, @Nullable Map<String, ComponentStatus> components) {

    public HealthProbeResponse(ComponentState state, @Nullable Map<String, ComponentStatus> components) {
        this.state = state;
        this.components = components;
    }

    public HealthProbeResponse(ComponentState state) {
        this(state, null);
    }

    public HealthProbeResponse(@Nullable Map<String, ComponentStatus> components) {
        this(components != null
                        ? components.values().stream()
                        .filter(ComponentStatus::isCritical)
                        .anyMatch(status -> status.state() == ComponentState.DOWN) ? ComponentState.DOWN : ComponentState.UP
                        : null,
                components);
    }

    /**
     * @return Overall state of the service.
     */
    public ComponentState masterState() {
        return state;
    }

    @Override
    public String toString() {
        return "HealthProbeResponse{" +
                "state=" + state +
                ", components=" + components +
                '}';
    }
}

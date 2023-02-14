package de.elliepotato.hhttp.probe;

import org.jetbrains.annotations.Nullable;

import java.util.Map;

/**
 * Overall health response of a service.
 */
public class HealthProbeResponse {

    private ComponentState state;
    private @Nullable Map<String, ComponentStatus> components;

    public HealthProbeResponse() {
    }

    public HealthProbeResponse(ComponentState masterState, @Nullable Map<String, ComponentStatus> components) {
        this.state = masterState;
        this.components = components;
    }

    public HealthProbeResponse(ComponentState serviceStatus) {
        this(serviceStatus, null);
    }

    public HealthProbeResponse(@Nullable Map<String, ComponentStatus> components) {
        this(null, components);

        // infer state from subcomponents
        if (components != null) {
            state = components.values().stream()
                    .filter(ComponentStatus::isCritical)
                    .anyMatch(status -> status.getState() == ComponentState.DOWN) ? ComponentState.UP : ComponentState.DOWN;
        }
    }

    /**
     * @return Overall state of the service.
     */
    public ComponentState getMasterState() {
        return state;
    }

    /**
     * @return Components of the service.
     */
    @Nullable
    public Map<String, ComponentStatus> getComponents() {
        return components;
    }

}

package de.elliepotato.hhttp.probe;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents the state of a component, and its subcomponents.
 */
public class ComponentStatus {

    private ComponentState state;
    private boolean critical;
    private @Nullable Map<String, String> details;
    private @Nullable Map<String, ComponentStatus> subComponents;

    public static Builder builder() {
        return new Builder();
    }

    public static Builder up() {
        return builder().status(ComponentState.UP);
    }

    public static Builder down(String error) {
        return builder().status(ComponentState.DOWN).error(error);
    }

    public ComponentStatus(
            ComponentState state, boolean critical,
            @Nullable Map<String, String> details, @Nullable Map<String, ComponentStatus> subComponents
    ) {
        this.state = state;
        this.critical = critical;
        this.details = details;
        this.subComponents = subComponents;
    }

    public ComponentStatus() {
    }

    public static class Builder {
        private ComponentState state;
        private boolean critical;
        private @Nullable Map<String, String> details;
        private @Nullable Map<String, ComponentStatus> subComponents;

        public Builder status(ComponentState state) {
            this.state = state;
            return this;
        }

        public Builder detail(String key, String value) {
            if (details == null) {
                details = new HashMap<>();
            }

            details.put(key, value);
            return this;
        }

        public Builder error(String error) {
            return detail("error", error);
        }

        public Builder subComponent(String componentId, ComponentStatus status) {
            if (subComponents == null) {
                subComponents = new HashMap<>();
            }

            subComponents.put(componentId, status);
            return this;
        }

        public Builder critical(boolean critical) {
            this.critical = critical;
            return this;
        }

        public Builder critical() {
            return critical(true);
        }

        public ComponentStatus build() {
            if (state == null && (subComponents == null || subComponents.isEmpty())) {
                throw new IllegalStateException("cannot infer state");
            }

            if (state == null) {
                // infer state by checking status of critical subcomponents
                state = subComponents.values().stream()
                        .filter(ComponentStatus::isCritical)
                        .anyMatch(status -> status.state == ComponentState.DOWN) ? ComponentState.DOWN : ComponentState.UP;
            }

            return new ComponentStatus(state, critical, details, subComponents);
        }

    }

    @NotNull
    public ComponentState getState() {
        return state;
    }

    public boolean isCritical() {
        return critical;
    }

    @Nullable
    public Map<String, String> getDetails() {
        return details;
    }

    @Nullable
    public Map<String, ComponentStatus> getSubComponents() {
        return subComponents;
    }

}

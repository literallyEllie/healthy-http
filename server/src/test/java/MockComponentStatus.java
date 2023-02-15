import de.elliepotato.hhttp.probe.ComponentProbe;
import de.elliepotato.hhttp.probe.ComponentState;
import de.elliepotato.hhttp.probe.ComponentStatus;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public record MockComponentStatus(String id, boolean critical) implements ComponentProbe {
    @Override
    public @NotNull String id() {
        return id;
    }

    @Override
    public boolean critical() {
        return critical;
    }

    @Override
    public CompletableFuture<ComponentStatus> probe() {
        // todo this is dumb
        return CompletableFuture.supplyAsync(() -> {
            ComponentStatus.Builder rootBuilder = ComponentStatus.builder();

            for (int i = 0; i < 10; i++) {
                ComponentStatus.Builder subBuilder = ComponentStatus.builder();

                if (i % 2 == 0) {
                    subBuilder.status(ComponentState.UP);

                    if (i == 6) {
                        subBuilder.critical();
                    }
                } else {
                    subBuilder.status(ComponentState.DOWN);

                    if (i == 5) {
                        subBuilder.critical();
                    }
                }

                rootBuilder.subComponent("sub-" + i, subBuilder.build());
            }

            return rootBuilder.build();
        });
    }
}

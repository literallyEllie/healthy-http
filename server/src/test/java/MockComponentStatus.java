import de.elliepotato.hhttp.probe.ComponentProbe;
import de.elliepotato.hhttp.probe.ComponentStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public record MockComponentStatus(String id, boolean critical, @Nullable List<ComponentProbe> children)
        implements ComponentProbe {
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
//         TODO
//        for (ComponentProbe child : children) {
//            child.probe()
//        }

        return null;
    }
}

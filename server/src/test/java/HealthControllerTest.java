import de.elliepotato.hhttp.controller.HealthController;
import de.elliepotato.hhttp.probe.ComponentProbe;
import de.elliepotato.hhttp.probe.HealthProbeResponse;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class HealthControllerTest {

    private final Context ctx = Mockito.mock(Context.class);

    private final List<ComponentProbe> parent = List.of(
            new MockComponentStatus("datasources", true)
    );
    private final HealthController controller = new HealthController(parent);

    // todo

    @Test
    public void testProbeStateAndLivenessStateAreSame() throws ExecutionException, InterruptedException {
        HealthProbeResponse allProbe = controller.probe(ctx).get();
        HealthProbeResponse liveness = controller.liveness(ctx).get();

        Assertions.assertEquals(allProbe.getMasterState(), liveness.getMasterState());
    }

    @Test
    public void tryProbeInvalidSpecificComponent_ExpectNotFound() {
        controller.probe(ctx, "bad componentId");
        verify(ctx).status(HttpStatus.NOT_FOUND);
    }

    @Test
    public void tryProbeValidSpecificComponent_ExpectServiceUnavailable() throws ExecutionException, InterruptedException {
        controller.probe(ctx, "datasources").get();
        verify(ctx).status(HttpStatus.SERVICE_UNAVAILABLE);
    }

}

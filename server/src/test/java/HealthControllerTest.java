import de.elliepotato.hhttp.controller.HealthController;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

public class HealthControllerTest {

    private final Context ctx = Mockito.mock(Context.class);
    private final HealthController controller = new HealthController(List.of());

    // TODO


    @Test
    public void tryProbeInvalidSpecificComponent_ExpectNotFound() {
        controller.probe(ctx, "bad componentId");
        verify(ctx).status(HttpStatus.NOT_FOUND);
    }



}

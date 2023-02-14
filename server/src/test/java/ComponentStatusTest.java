import de.elliepotato.hhttp.probe.ComponentState;
import de.elliepotato.hhttp.probe.ComponentStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class ComponentStatusTest {

    private List<MockComponentStatus> parent = List.of(
            new MockComponentStatus("mysql", true, null)
    );
    private final ComponentStatus okComponent = ComponentStatus.up().build();
    private final ComponentStatus okCriticalComponent = ComponentStatus.up().critical().build();
    private final ComponentStatus failedComponent = ComponentStatus.down("null").build();
    private final ComponentStatus failedCriticalComponent = ComponentStatus.down("null").critical().build();

    @Test
    public void testComponentStateInfer() {
        // no way to infer
        Assertions.assertThrows(IllegalStateException.class, () -> ComponentStatus.builder().build());

        Assertions.assertEquals(
                ComponentState.UP,
                ComponentStatus.builder()
                        .status(ComponentState.UP)
                        .subComponent("aFailedComponent", failedComponent)
                        .build().getState()
        );

        // Infer down because failed critical component
        Assertions.assertEquals(
                ComponentState.DOWN,
                ComponentStatus.builder()
                        .subComponent("anOkCriticalComponent", okCriticalComponent)
                        .subComponent("aFailedCriticalComponent", failedCriticalComponent)
                        .build().getState()
        );

        // Infer up even if failed
        Assertions.assertEquals(
                ComponentState.UP,
                ComponentStatus.builder()
                        .subComponent("anOkComponent", okComponent)
                        .subComponent("aFailedComponent", failedComponent)
                        .build().getState()
        );
    }

}
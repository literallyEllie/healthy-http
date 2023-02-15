import de.elliepotato.hhttp.probe.ComponentState;
import de.elliepotato.hhttp.probe.ComponentStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ComponentStatusTest {

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
                        .build().state()
        );

        // Infer down because failed critical component
        Assertions.assertEquals(
                ComponentState.DOWN,
                ComponentStatus.builder()
                        .subComponent("anOkCriticalComponent", okCriticalComponent)
                        .subComponent("aFailedCriticalComponent", failedCriticalComponent)
                        .build().state()
        );

        // Infer up even if failed
        Assertions.assertEquals(
                ComponentState.UP,
                ComponentStatus.builder()
                        .subComponent("anOkComponent", okComponent)
                        .subComponent("aFailedComponent", failedComponent)
                        .build().state()
        );
    }

}

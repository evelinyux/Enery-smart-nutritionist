package enery.use_case.health_assessment;

import enery.interface_adapter.health_assessment.HealthAssessmentController;
import enery.use_case.health_assessment.HealthAssessmentInputBoundary;
import enery.use_case.health_assessment.HealthAssessmentInputData;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class HealthAssessmentControllerTest {
    @Test
    void testExecute_PassesUserIdToInteractor() {
        HealthAssessmentInputBoundary mockInteractor = mock(HealthAssessmentInputBoundary.class);
        HealthAssessmentController controller = new HealthAssessmentController(mockInteractor);

        controller.execute("yuxin-id", null);

        ArgumentCaptor<HealthAssessmentInputData> captor = ArgumentCaptor.forClass(HealthAssessmentInputData.class);
        verify(mockInteractor).execute(captor.capture());

        assertEquals("yuxin-id", captor.getValue().getUserId());
    }
}

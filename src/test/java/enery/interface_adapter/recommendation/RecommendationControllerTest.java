package enery.interface_adapter.recommendation;

import enery.use_case.recommendation.RecommendationInputBoundary;
import enery.use_case.recommendation.RecommendationInputData;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class RecommendationControllerTest {

    @Test
    void executePassesUsernameToInteractor() {
        final RecommendationInputBoundary interactor =
                mock(RecommendationInputBoundary.class);

        final RecommendationController controller =
                new RecommendationController(interactor);

        controller.execute("test_user");

        final ArgumentCaptor<RecommendationInputData> captor =
                ArgumentCaptor.forClass(
                        RecommendationInputData.class
                );

        verify(interactor).execute(captor.capture());

        assertEquals(
                "test_user",
                captor.getValue().getUsername()
        );
    }
}

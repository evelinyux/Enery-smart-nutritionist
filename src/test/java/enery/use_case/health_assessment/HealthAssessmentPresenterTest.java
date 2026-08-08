package enery.use_case.health_assessment;

import enery.interface_adapter.health_assessment.HealthAssessmentPresenter;
import enery.interface_adapter.health_assessment.HealthAssessmentState;
import enery.interface_adapter.health_assessment.HealthAssessmentViewModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

public class HealthAssessmentPresenterTest {
    private HealthAssessmentViewModel viewModel;
    private HealthAssessmentPresenter presenter;

    @BeforeEach
    void setUp() {
        viewModel = new HealthAssessmentViewModel();
        presenter = new HealthAssessmentPresenter(viewModel);
    }

    @Test
    void testPrepareSuccessView_UpdatesStateAndFiresPropertyChange() {
        HealthAssessmentOutputData response = new HealthAssessmentOutputData(
                "ass-id", 80, "Low", 85, new ArrayList<>(), new ArrayList<>()
        );

        presenter.prepareSuccessView(response);

        HealthAssessmentState updatedState = viewModel.getState();
        assertEquals("ass-id", updatedState.getAssessmentId());
        assertEquals(85, updatedState.getDietScore());
        assertNull(updatedState.getAssessmentError());
    }

    @Test
    void testPrepareFailView_SetsErrorInState() {
        presenter.prepareFailView("Database connection failed");

        HealthAssessmentState updatedState = viewModel.getState();
        assertEquals("Database connection failed", updatedState.getAssessmentError());
    }
}

package enery.use_case.health_assessment;

import enery.use_case.tracking.TrackingOutputData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.mockito.Mockito.*;

public class HealthAssessmentInteractorTest {
    private HealthAssessmentOutputBoundary mockPresenter;
    private HealthAssessmentDataAccessInterface mockDAO;
    private HealthAssessmentInteractor interactor;

    @BeforeEach
    void setUp() {
        mockPresenter = mock(HealthAssessmentOutputBoundary.class);
        mockDAO = mock(HealthAssessmentDataAccessInterface.class);
        interactor = new HealthAssessmentInteractor(mockPresenter, mockDAO);
    }

    @Test
    void testExecute_NoDataFound_CallsFailView() {
        TrackingOutputData emptyData = mock(TrackingOutputData.class);

        HealthAssessmentInputData input = new HealthAssessmentInputData("user123", emptyData);
        when(emptyData.getProteinInGrams()).thenReturn(0.0);
        when(emptyData.getCarbohydratesInGrams()).thenReturn(0.0);
        when(emptyData.getFatInGrams()).thenReturn(0.0);

        when(mockDAO.getTrackingData(eq("user123"), any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(emptyData);

        interactor.execute(input);

        verify(mockPresenter).prepareFailView(contains("No meal records found"));
        verify(mockPresenter, never()).prepareSuccessView(any());
    }
}

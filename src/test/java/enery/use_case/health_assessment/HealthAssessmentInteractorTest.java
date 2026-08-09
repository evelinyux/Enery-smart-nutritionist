package enery.use_case.health_assessment;

import enery.use_case.tracking.TrackingOutputData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class HealthAssessmentInteractorTest {

    private HealthAssessmentOutputBoundary mockPresenter;
    private HealthAssessmentDataAccessInterface mockDAO;
    private HealthAssessmentInteractor interactor;

    @BeforeEach
    void setUp() {
        mockPresenter =
                mock(HealthAssessmentOutputBoundary.class);

        mockDAO =
                mock(HealthAssessmentDataAccessInterface.class);

        interactor =
                new HealthAssessmentInteractor(
                        mockPresenter,
                        mockDAO
                );
    }

    @Test
    void testExecute_NoDataFound_CallsFailView() {

        TrackingOutputData emptyData = null;

        HealthAssessmentInputData input =
                new HealthAssessmentInputData(
                        "user123",
                        emptyData
                );

        when(mockDAO.getTrackingData(
                eq("user123"),
                any(LocalDate.class),
                any(LocalDate.class)))
                .thenReturn(null);

        interactor.execute(input);

        verify(mockPresenter)
                .prepareFailView(
                        contains("No meal records found")
                );

        verify(mockPresenter, never())
                .prepareSuccessView(any());
    }
}

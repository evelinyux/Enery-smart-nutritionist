package enery.use_case.health_assessment;

import java.time.LocalDate;
import java.util.List;

import enery.data_access.HealthAssessment_database.NutrientStandards;
import enery.use_case.tracking.TrackingOutputData;
import enery.use_case.tracking.TrackingPeriod;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class HealthAssessmentInteractorTest {

    private HealthAssessmentOutputBoundary presenter;
    private HealthAssessmentDataAccessInterface dataAccess;
    private HealthAssessmentInteractor interactor;

    @BeforeEach
    void setUp() {
        presenter =
                mock(HealthAssessmentOutputBoundary.class);

        dataAccess =
                mock(HealthAssessmentDataAccessInterface.class);

        interactor =
                new HealthAssessmentInteractor(
                        presenter,
                        dataAccess
                );
    }

    @Test
    void noTrackingDataCallsFailViewTest() {
        when(
                dataAccess.getTrackingData(
                        eq("user123"),
                        any(LocalDate.class),
                        any(LocalDate.class)
                )
        ).thenReturn(null);

        interactor.execute(
                new HealthAssessmentInputData(
                        "user123",
                        null
                )
        );

        verify(presenter)
                .prepareFailView(
                        contains("No meal records found")
                );

        verify(
                presenter,
                never()
        ).prepareSuccessView(any());
    }

    @Test
    void zeroNutritionTrackingDataStillProducesAssessmentTest() {
        final TrackingOutputData trackingData =
                createTrackingData(
                        500.0,
                        0.0,
                        0.0,
                        0.0
                );

        when(
                dataAccess.getTrackingData(
                        eq("user123"),
                        any(LocalDate.class),
                        any(LocalDate.class)
                )
        ).thenReturn(trackingData);

        when(
                dataAccess.getNutrientStandards()
        ).thenReturn(
                new NutrientStandards()
        );

        interactor.execute(
                new HealthAssessmentInputData(
                        "user123",
                        null
                )
        );

        final ArgumentCaptor<HealthAssessmentOutputData>
                captor =
                ArgumentCaptor.forClass(
                        HealthAssessmentOutputData.class
                );

        verify(presenter)
                .prepareSuccessView(
                        captor.capture()
                );

        verify(
                presenter,
                never()
        ).prepareFailView(any());

        final HealthAssessmentOutputData output =
                captor.getValue();

        assertNotNull(output);
        assertNotNull(output.assessmentId);

        assertFalse(
                output.dietInsights.isEmpty()
        );
    }

    @Test
    void successfulAssessmentCallsSuccessViewTest() {
        final TrackingOutputData trackingData =
                createTrackingData(
                        1800.0,
                        50.0,
                        130.0,
                        10.0
                );

        when(
                dataAccess.getTrackingData(
                        eq("user123"),
                        any(LocalDate.class),
                        any(LocalDate.class)
                )
        ).thenReturn(trackingData);

        when(
                dataAccess.getNutrientStandards()
        ).thenReturn(
                new NutrientStandards()
        );

        interactor.execute(
                new HealthAssessmentInputData(
                        "user123",
                        null
                )
        );

        final ArgumentCaptor<HealthAssessmentOutputData>
                captor =
                ArgumentCaptor.forClass(
                        HealthAssessmentOutputData.class
                );

        verify(presenter)
                .prepareSuccessView(
                        captor.capture()
                );

        verify(
                presenter,
                never()
        ).prepareFailView(any());

        final HealthAssessmentOutputData output =
                captor.getValue();

        assertNotNull(output);
        assertNotNull(output.assessmentId);

        assertFalse(
                output.assessmentId.isBlank()
        );

        assertFalse(
                output.dietInsights.isEmpty()
        );
    }

    @Test
    void dataAccessExceptionCallsFailViewTest() {
        when(
                dataAccess.getTrackingData(
                        eq("user123"),
                        any(LocalDate.class),
                        any(LocalDate.class)
                )
        ).thenThrow(
                new RuntimeException(
                        "database unavailable"
                )
        );

        interactor.execute(
                new HealthAssessmentInputData(
                        "user123",
                        null
                )
        );

        verify(presenter)
                .prepareFailView(
                        "Fail to assess: database unavailable"
                );

        verify(
                presenter,
                never()
        ).prepareSuccessView(any());
    }

    @Test
    void nutrientStandardsFailureCallsFailViewTest() {
        final TrackingOutputData trackingData =
                createTrackingData(
                        1800.0,
                        50.0,
                        130.0,
                        10.0
                );

        when(
                dataAccess.getTrackingData(
                        eq("user123"),
                        any(LocalDate.class),
                        any(LocalDate.class)
                )
        ).thenReturn(trackingData);

        when(
                dataAccess.getNutrientStandards()
        ).thenThrow(
                new RuntimeException(
                        "standards unavailable"
                )
        );

        interactor.execute(
                new HealthAssessmentInputData(
                        "user123",
                        null
                )
        );

        verify(presenter)
                .prepareFailView(
                        "Fail to assess: standards unavailable"
                );

        verify(
                presenter,
                never()
        ).prepareSuccessView(any());
    }

    private static TrackingOutputData createTrackingData(
            double calories,
            double protein,
            double carbohydrates,
            double fat) {

        final LocalDate today =
                LocalDate.now();

        return new TrackingOutputData(
                TrackingPeriod.WEEKLY,
                today.minusDays(6),
                today,

                calories,
                0.0,
                calories,

                protein,
                carbohydrates,
                fat,

                0.0,
                0.0,
                0.0,

                calories / 7.0,
                0.0,
                calories / 7.0,

                List.of()
        );
    }
}

package enery.interface_adapter.tracking;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;

import enery.use_case.tracking.DailyTrackingSummary;
import enery.use_case.tracking.TrackingOutputData;
import enery.use_case.tracking.TrackingPeriod;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TrackingPresenterTest {

    @Test
    void preparesSuccessState() {
        final TrackingViewModel viewModel =
                new TrackingViewModel();

        final TrackingPresenter presenter =
                new TrackingPresenter(viewModel);

        final LocalDate startDate =
                LocalDate.of(2026, 7, 20);

        final LocalDate endDate =
                LocalDate.of(2026, 7, 26);

        final DailyTrackingSummary monday =
                new DailyTrackingSummary(
                        startDate,
                        2000.0,
                        300.0,
                        1700.0
                );

        presenter.prepareSuccessView(
                new TrackingOutputData(
                        TrackingPeriod.WEEKLY,
                        startDate,
                        endDate,
                        2000.0,
                        300.0,
                        1700.0,
                        120.0,
                        250.0,
                        65.0,
                        30.0,   // fiber
                        40.0,   // sugar
                        1500.0, // sodium
                        2000.0 / 7.0,
                        300.0 / 7.0,
                        1700.0 / 7.0,
                        List.of(monday)
                )
        );

        final TrackingState state =
                viewModel.getState();

        assertEquals(
                TrackingPeriod.WEEKLY,
                state.getPeriod());

        assertEquals(
                startDate,
                state.getStartDate());

        assertEquals(
                endDate,
                state.getEndDate());

        assertEquals(
                2000.0,
                state.getCaloriesConsumed());

        assertEquals(
                300.0,
                state.getCaloriesBurned());

        assertEquals(
                1700.0,
                state.getNetCalories());

        assertEquals(
                120.0,
                state.getProteinInGrams());

        assertEquals(
                250.0,
                state.getCarbohydratesInGrams());

        assertEquals(
                65.0,
                state.getFatInGrams());

        assertEquals(
                2000.0 / 7.0,
                state.getAverageCaloriesConsumed(),
                0.0001);

        assertEquals(
                1,
                state.getDailySummaries().size());

        assertEquals(
                "",
                state.getErrorMessage());

        assertEquals(
                30.0,
                state.getFiberInGrams());

        assertEquals(
                40.0,
                state.getSugarInGrams());

        assertEquals(
                1500.0,
                state.getSodiumInMilligrams());
    }

    @Test
    void preparesFailureState() {
        final TrackingViewModel viewModel =
                new TrackingViewModel();

        final TrackingPresenter presenter =
                new TrackingPresenter(viewModel);

        presenter.prepareFailView(
                "A tracking period is required.");

        assertEquals(
                "A tracking period is required.",
                viewModel.getState().getErrorMessage());
    }
}

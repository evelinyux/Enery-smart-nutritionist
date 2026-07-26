package enery.interface_adapter.tracking;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import enery.use_case.tracking.TrackingOutputData;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TrackingPresenterTest {

    @Test
    void preparesSuccessState() {
        final TrackingViewModel viewModel =
                new TrackingViewModel();

        final TrackingPresenter presenter =
                new TrackingPresenter(viewModel);

        final LocalDate date =
                LocalDate.of(2026, 7, 26);

        presenter.prepareSuccessView(
                new TrackingOutputData(
                        date,
                        2000.0,
                        300.0,
                        1700.0,
                        120.0,
                        250.0,
                        65.0
                )
        );

        final TrackingState state =
                viewModel.getState();

        assertEquals(date, state.getDate());
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
        assertEquals("", state.getErrorMessage());
    }

    @Test
    void preparesFailureState() {
        final TrackingViewModel viewModel =
                new TrackingViewModel();

        final TrackingPresenter presenter =
                new TrackingPresenter(viewModel);

        presenter.prepareFailView(
                "A date is required.");

        assertEquals(
                "A date is required.",
                viewModel.getState().getErrorMessage());
    }
}

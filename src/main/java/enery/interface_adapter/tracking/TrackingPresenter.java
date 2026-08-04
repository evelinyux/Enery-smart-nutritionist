package enery.interface_adapter.tracking;

import enery.use_case.tracking.TrackingOutputBoundary;
import enery.use_case.tracking.TrackingOutputData;

/**
 * Converts tracking output into view-model state.
 */
public final class TrackingPresenter
        implements TrackingOutputBoundary {

    private final TrackingViewModel viewModel;

    public TrackingPresenter(
            TrackingViewModel viewModel) {

        this.viewModel = viewModel;
    }

    @Override
    public void prepareSuccessView(
            TrackingOutputData outputData) {

        final TrackingState state =
                viewModel.getState();

        state.setPeriod(outputData.getPeriod());
        state.setStartDate(outputData.getStartDate());
        state.setEndDate(outputData.getEndDate());

        state.setCaloriesConsumed(
                outputData.getCaloriesConsumed());
        state.setCaloriesBurned(
                outputData.getCaloriesBurned());
        state.setNetCalories(
                outputData.getNetCalories());

        state.setProteinInGrams(
                outputData.getProteinInGrams());
        state.setCarbohydratesInGrams(
                outputData.getCarbohydratesInGrams());
        state.setFatInGrams(
                outputData.getFatInGrams());
        state.setFiberInGrams(
                outputData.getFiberInGrams());
        state.setSugarInGrams(
                outputData.getSugarInGrams());
        state.setSodiumInMilligrams(
                outputData.getSodiumInMilligrams());

        state.setAverageCaloriesConsumed(
                outputData.getAverageCaloriesConsumed());
        state.setAverageCaloriesBurned(
                outputData.getAverageCaloriesBurned());
        state.setAverageNetCalories(
                outputData.getAverageNetCalories());

        state.setDailySummaries(
                outputData.getDailySummaries());

        state.setErrorMessage("");

        viewModel.setState(state);
        viewModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String errorMessage) {
        final TrackingState state =
                viewModel.getState();

        state.setErrorMessage(errorMessage);

        viewModel.setState(state);
        viewModel.firePropertyChanged();
    }
}

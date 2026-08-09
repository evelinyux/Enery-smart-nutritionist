package enery.interface_adapter.progress;

import enery.interface_adapter.shared.ViewManagerModel;
import enery.use_case.progress.ProgressOutputBoundary;
import enery.use_case.progress.ProgressOutputData;

public class ProgressPresenter implements ProgressOutputBoundary {
    private final ViewManagerModel viewManagerModel;
    private final ProgressViewModel progressViewModel;

    public ProgressPresenter(ViewManagerModel viewManagerModel, ProgressViewModel progressViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.progressViewModel = progressViewModel;
    }

    @Override
    public void prepareSuccessView(ProgressOutputData outputData) {
        final ProgressState progressState = progressViewModel.getState();

        progressState.setTodaysCalories(outputData.getTodaysCalories());
        progressState.setDailyCalorieGoal(outputData.getDailyCalorieGoal());
        progressState.setDailyProgressPercent(outputData.getDailyProgressPercent());

        progressState.setProgressError(null);
        progressState.setSuccessMessage("");

        progressViewModel.setState(progressState);
        progressViewModel.firePropertyChanged("state");
    }

    @Override
    public void prepareFailView(String errorMessage) {
        final ProgressState progressState = progressViewModel.getState();

        progressState.setProgressError(errorMessage);

        progressState.setSuccessMessage(null);

        progressViewModel.setState(progressState);

        progressViewModel.firePropertyChanged("error");
    }
}

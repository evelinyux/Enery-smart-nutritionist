package enery.interface_adapter.daily_check;

import enery.interface_adapter.shared.ViewManagerModel;
import enery.use_case.daily_check.DailyCheckOutputBoundary;
import enery.use_case.daily_check.DailyCheckOutputData;

public class DailyCheckPresenter implements DailyCheckOutputBoundary {
    private final ViewManagerModel viewManagerModel;
    private final DailyCheckViewModel dailyCheckViewModel;

    public DailyCheckPresenter(ViewManagerModel viewManagerModel, DailyCheckViewModel dailyCheckViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.dailyCheckViewModel = dailyCheckViewModel;
    }

    @Override
    public void prepareSuccessView(DailyCheckOutputData outputData) {
        final DailyCheckState dailyCheckState = dailyCheckViewModel.getState();

        dailyCheckState.setBreakFastLogged(outputData.isBreakfastLogged());
        dailyCheckState.setLunchLogged(outputData.isLunchLogged());
        dailyCheckState.setDinnerLogged(outputData.isDinnerLogged());
        dailyCheckState.setExerciseLogged(outputData.isExerciseLogged());

        dailyCheckState.setDailyCheckError(null);
        dailyCheckState.setSuccessMessage("");

        dailyCheckViewModel.setState(dailyCheckState);
        dailyCheckViewModel.firePropertyChanged();
    }

    @Override
    public void prepareFailureView(String errorMessage) {
        final DailyCheckState dailyCheckState = dailyCheckViewModel.getState();

        dailyCheckState.setDailyCheckError(errorMessage);
        dailyCheckState.setSuccessMessage(null);

        dailyCheckViewModel.setState(dailyCheckState);
        dailyCheckViewModel.firePropertyChanged();
    }
}

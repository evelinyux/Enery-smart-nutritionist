package enery.interface_adapter.daily_check;

import enery.use_case.daily_check.DailyCheckInputBoundary;
import enery.use_case.daily_check.DailyCheckInputData;

/**
 * Controller for the Daily Check feature.
 * Takes input from the View and passes it to the Use Case Interactor.
 */
public class DailyCheckController {

    private final DailyCheckInputBoundary dailyCheckInteractor;

    public DailyCheckController(DailyCheckInputBoundary dailyCheckInteractor) {
        this.dailyCheckInteractor = dailyCheckInteractor;
    }

    /**
     * Executes the daily check use case for a specific user.
     * @param username The identifier for the logged-in user.
     */
    public void execute(String username) {
        DailyCheckInputData inputData = new DailyCheckInputData(username);
        dailyCheckInteractor.execute(inputData);
    }
}
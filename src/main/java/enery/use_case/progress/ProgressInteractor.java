package enery.use_case.progress;

public final class ProgressInteractor implements ProgressInputBoundary {
    private final ProgressDataInterface dataAccessObject;
    private final ProgressOutputBoundary presenter;

    public ProgressInteractor(
            ProgressDataInterface dataAccessObject,
            ProgressOutputBoundary presenter) {
        this.dataAccessObject = dataAccessObject;
        this.presenter = presenter;
    }

    @Override
    public void execute(ProgressInputData inputData) {
        String username = inputData.getUsername();

        if (username == null) {
            presenter.prepareFailView("A logged-in user is required.");
            return;
        }

        if (dataAccessObject.getUserProfile(username) == null) {
            presenter.prepareFailView("A plan is required.");
            return;
        }

        final double todaysCalories = dataAccessObject.getTodaysCalories(username);
        final double dailyCalorieGoal = dataAccessObject.getDailyCalorieGoal(username);

        if (dailyCalorieGoal == 0) {
            presenter.prepareFailView("Calorie Goal is 0.");
            return;
        }

        final double dailyProgressPercent = calculateDailyProgressPercent(todaysCalories, dailyCalorieGoal);

        final double todaysProtein = dataAccessObject.getTodaysProtein(username);
        final double todaysCarbohydrates = dataAccessObject.getTodaysCarbohydrates(username);
        final double todaysFats = dataAccessObject.getTodaysFats(username);

        final double totalCalorieGoal = dataAccessObject.getTotalCalorieGoal(username);
        final double totalCalorieConsumed = dataAccessObject.getTotalCaloriesConsumed(username);

        if (totalCalorieGoal == 0) {
            presenter.prepareFailView("Plan Calorie Goal is 0.");
            return;
        }

        final double totalProgressPercent = totalCalorieConsumed / totalCalorieGoal;

        final int daysSoFar = dataAccessObject.getDaysSoFar(username);

        presenter.prepareSuccessView(new ProgressOutputData(
                todaysCalories,
                dailyCalorieGoal,
                dailyProgressPercent,
                todaysProtein,
                todaysCarbohydrates,
                todaysFats,
                totalCalorieGoal,
                totalCalorieConsumed,
                totalProgressPercent,
                daysSoFar));
    }

    private double calculateDailyProgressPercent(double todaysCalories, double calorieGoal) {
        return todaysCalories / calorieGoal;
    }
}

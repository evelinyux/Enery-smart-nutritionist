package enery.use_case.progress;

public final class ProgressInteractor implements ProgressInputBoundary {
    private final ProgressDataInterface dataAccessObject;
    private ProgressOutputBoundary presenter;

    public ProgressInteractor(
            ProgressDataInterface dataAccessObject,
            ProgressOutputBoundary presenter) {
        this.dataAccessObject = dataAccessObject;
        this.presenter = presenter;
    }

    @Override
    public void execute(ProgressInputData inputData) {
        if (inputData.getUsername() == null) {
            presenter.prepareFailView("A logged-in user is required.");
            return;
        }

        if (dataAccessObject.getUserProfile() == null) {
            presenter.prepareFailView("A plan is required.");
            return;
        }

        final double todaysCalories = dataAccessObject.getTodaysCalories();
        final double dailyCalorieGoal = dataAccessObject.getDailyCalorieGoal();

        if (dailyCalorieGoal == 0) {
            presenter.prepareFailView("Calorie Goal is 0.");
            return;
        }

        double dailyProgressPercent = calculateDailyProgressPercent(todaysCalories, dailyCalorieGoal);

        presenter.prepareSuccessView(new ProgressOutputData(todaysCalories, dailyCalorieGoal, dailyProgressPercent));
    }

    private double calculateDailyProgressPercent(double todaysCalories, double calorieGoal) {
        return todaysCalories / calorieGoal;
    }
}

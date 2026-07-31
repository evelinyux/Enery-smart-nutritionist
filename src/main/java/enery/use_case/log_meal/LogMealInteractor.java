package enery.use_case.log_meal;

import enery.entity.meal.MealEntry;
import enery.entity.meal.MealEntryFactory;

/**
 * Interactor for logging a meal.
 */
public final class LogMealInteractor
        implements LogMealInputBoundary {

    private final LogMealUserDataAccessInterface dataAccessObject;
    private final LogMealOutputBoundary presenter;
    private final MealEntryFactory mealEntryFactory;

    public LogMealInteractor(
            LogMealUserDataAccessInterface dataAccessObject,
            LogMealOutputBoundary presenter,
            MealEntryFactory mealEntryFactory) {

        this.dataAccessObject = dataAccessObject;
        this.presenter = presenter;
        this.mealEntryFactory = mealEntryFactory;
    }

    @Override
    public void execute(LogMealInputData inputData) {
        if (inputData.getUsername() == null
                || inputData.getUsername().isBlank()) {
            presenter.prepareFailView(
                    "A logged-in user is required.");
            return;
        }

        if (inputData.getFoodItem() == null) {
            presenter.prepareFailView(
                    "A food item is required.");
            return;
        }

        if (inputData.getMealType() == null) {
            presenter.prepareFailView(
                    "A meal type is required.");
            return;
        }

        if (inputData.getServings() <= 0.0) {
            presenter.prepareFailView(
                    "Servings must be greater than zero.");
            return;
        }

        if (inputData.getDate() == null) {
            presenter.prepareFailView(
                    "A date and time are required.");
            return;
        }

        final MealEntry mealEntry = mealEntryFactory.create(
                inputData.getFoodItem(),
                inputData.getMealType(),
                inputData.getServings(),
                inputData.getDate()
        );

        dataAccessObject.saveMeal(
                inputData.getUsername(),
                mealEntry
        );

        presenter.prepareSuccessView(
                new LogMealOutputData(
                        inputData.getUsername(),
                        mealEntry
                )
        );
    }
}

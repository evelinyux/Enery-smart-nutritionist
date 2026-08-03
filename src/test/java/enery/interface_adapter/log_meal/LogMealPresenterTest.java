package enery.interface_adapter.log_meal;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import enery.entity.meal.FoodItem;
import enery.entity.meal.MealEntry;
import enery.entity.meal.MealType;
import enery.use_case.log_meal.LogMealOutputData;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LogMealPresenterTest {

    @Test
    void preparesSuccessState() {
        final LogMealViewModel viewModel =
                new LogMealViewModel();

        final LogMealPresenter presenter =
                new LogMealPresenter(viewModel);

        final FoodItem apple = new FoodItem(
                "Apple",
                100.0,
                95.0,
                0.5,
                25.0,
                0.3,
                4.4,
                19.0,
                2.0
        );

        final MealEntry entry = new MealEntry(
                apple,
                MealType.BREAKFAST,
                1.0,
                LocalDate.now()
        );

        presenter.prepareSuccessView(
                new LogMealOutputData("Paul", entry)
        );

        final LogMealState state = viewModel.getState();

        assertEquals("", state.getErrorMessage());
        assertEquals(
                "Apple was logged successfully.",
                state.getSuccessMessage()
        );
    }

    @Test
    void preparesFailureState() {
        final LogMealViewModel viewModel =
                new LogMealViewModel();

        final LogMealPresenter presenter =
                new LogMealPresenter(viewModel);

        presenter.prepareFailView(
                "Servings must be greater than zero."
        );

        final LogMealState state = viewModel.getState();

        assertEquals(
                "Servings must be greater than zero.",
                state.getErrorMessage()
        );
        assertEquals("", state.getSuccessMessage());
    }
}

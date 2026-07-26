package enery.interface_adapter.log_meal;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import enery.entity.FoodItem;
import enery.entity.MealEntry;
import enery.entity.MealType;
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
                LocalDateTime.now()
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

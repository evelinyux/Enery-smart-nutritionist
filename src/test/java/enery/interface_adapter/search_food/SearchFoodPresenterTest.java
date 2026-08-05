package enery.interface_adapter.search_food;

import java.util.List;

import org.junit.jupiter.api.Test;

import enery.entity.meal.FoodItem;
import enery.use_case.search_food.SearchFoodOutputData;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SearchFoodPresenterTest {

    @Test
    void preparesSuccessState() {
        final SearchFoodViewModel viewModel =
                new SearchFoodViewModel();

        final SearchFoodPresenter presenter =
                new SearchFoodPresenter(viewModel);

        final FoodItem apple = new FoodItem(
                "apple",
                100.0,
                52.0,
                0.3,
                13.8,
                0.2,
                2.4,
                10.4,
                1.0
        );

        presenter.prepareSuccessView(
                new SearchFoodOutputData(
                        List.of(apple)));

        final SearchFoodState state =
                viewModel.getState();

        assertEquals(1, state.getResults().size());
        assertEquals(
                "apple",
                state.getResults().get(0).getName());
        assertEquals("", state.getErrorMessage());
    }

    @Test
    void preparesFailureState() {
        final SearchFoodViewModel viewModel =
                new SearchFoodViewModel();

        final SearchFoodPresenter presenter =
                new SearchFoodPresenter(viewModel);

        presenter.prepareFailView(
                "No matching foods were found.");

        final SearchFoodState state =
                viewModel.getState();

        assertTrue(state.getResults().isEmpty());
        assertEquals(
                "No matching foods were found.",
                state.getErrorMessage());
    }
}

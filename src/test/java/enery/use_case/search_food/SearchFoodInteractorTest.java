package enery.use_case.search_food;

import java.util.List;

import org.junit.jupiter.api.Test;

import enery.entity.meal.FoodItem;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

class SearchFoodInteractorTest {

    @Test
    void successTest() {
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

        final NutritionDataAccessInterface dataAccessObject =
                query -> List.of(apple);

        final boolean[] successCalled = {false};

        final SearchFoodOutputBoundary presenter =
                new SearchFoodOutputBoundary() {
                    @Override
                    public void prepareSuccessView(
                            SearchFoodOutputData outputData) {

                        successCalled[0] = true;
                        assertEquals(
                                1,
                                outputData.getResults().size());
                        assertEquals(
                                "apple",
                                outputData.getResults()
                                        .get(0)
                                        .getName());
                    }

                    @Override
                    public void prepareFailView(
                            String errorMessage) {

                        fail("Unexpected search failure.");
                    }
                };

        final SearchFoodInputBoundary interactor =
                new SearchFoodInteractor(
                        dataAccessObject,
                        presenter
                );

        interactor.execute(
                new SearchFoodInputData("1 apple"));

        assertTrue(successCalled[0]);
    }

    @Test
    void rejectsBlankQuery() {
        final NutritionDataAccessInterface dataAccessObject =
                query -> List.of();

        final SearchFoodOutputBoundary presenter =
                new SearchFoodOutputBoundary() {
                    @Override
                    public void prepareSuccessView(
                            SearchFoodOutputData outputData) {

                        fail("Unexpected search success.");
                    }

                    @Override
                    public void prepareFailView(
                            String errorMessage) {

                        assertEquals(
                                "Enter a food name or description.",
                                errorMessage);
                    }
                };

        final SearchFoodInputBoundary interactor =
                new SearchFoodInteractor(
                        dataAccessObject,
                        presenter
                );

        interactor.execute(new SearchFoodInputData("   "));
    }
}

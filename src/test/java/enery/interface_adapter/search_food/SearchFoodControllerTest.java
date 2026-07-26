package enery.interface_adapter.search_food;

import org.junit.jupiter.api.Test;

import enery.use_case.search_food.SearchFoodInputBoundary;
import enery.use_case.search_food.SearchFoodInputData;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SearchFoodControllerTest {

    @Test
    void passesQueryToInteractor() {
        final SearchFoodInputData[] received = {null};

        final SearchFoodInputBoundary interactor =
                inputData -> received[0] = inputData;

        final SearchFoodController controller =
                new SearchFoodController(interactor);

        controller.execute("1 large apple");

        assertEquals(
                "1 large apple",
                received[0].getQuery());
    }
}

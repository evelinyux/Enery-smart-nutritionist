package enery.interface_adapter.log_meal;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import enery.entity.meal.FoodItem;
import enery.entity.meal.MealType;
import enery.use_case.log_meal.LogMealInputBoundary;
import enery.use_case.log_meal.LogMealInputData;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

class LogMealControllerTest {

    @Test
    void passesInputToInteractor() {
        final LogMealInputData[] receivedInput = {null};

        final LogMealInputBoundary interactor =
                inputData -> receivedInput[0] = inputData;

        final LogMealController controller =
                new LogMealController(interactor);

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

        final LocalDate time =
                LocalDate.of(2026, 7, 26);

        controller.execute(
                "Paul",
                apple,
                MealType.BREAKFAST,
                2.0,
                time
        );

        assertEquals("Paul", receivedInput[0].getUsername());
        assertSame(apple, receivedInput[0].getFoodItem());
        assertEquals(
                MealType.BREAKFAST,
                receivedInput[0].getMealType()
        );
        assertEquals(2.0, receivedInput[0].getServings());
        assertEquals(time, receivedInput[0].getDate());
    }
}

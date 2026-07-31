package enery.use_case.log_meal;

import java.time.LocalDate;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;

import enery.data_access.InMemoryMealDataAccessObject;
import enery.entity.meal.CommonMealEntryFactory;
import enery.entity.meal.FoodItem;
import enery.entity.meal.MealEntry;
import enery.entity.meal.MealEntryFactory;
import enery.entity.meal.MealType;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

class LogMealInteractorTest {

    @Test
    void successTest() {
        final InMemoryMealDataAccessObject repository =
                new InMemoryMealDataAccessObject();

        final FoodItem apple = new FoodItem(
                "Apple",
                100.0, // serving size in grams
                95.0,  // calories
                0.5,   // protein
                25.0,  // carbohydrates
                0.3,   // fat
                4.4,   // fiber
                19.0,  // sugar
                2.0    // sodium
        );

        final LocalDate time =
                LocalDate.of(
                        2026,
                        7,
                        26
                );

        final LogMealInputData inputData =
                new LogMealInputData(
                        "Paul",
                        apple,
                        MealType.BREAKFAST,
                        2.0,
                        time
                );

        final boolean[] successCalled = {false};

        final LogMealOutputBoundary presenter =
                new LogMealOutputBoundary() {
                    @Override
                    public void prepareSuccessView(
                            LogMealOutputData outputData) {

                        successCalled[0] = true;

                        assertEquals(
                                "Paul",
                                outputData.getUsername()
                        );

                        assertEquals(
                                190.0,
                                outputData
                                        .getMealEntry()
                                        .getTotalCalories()
                        );
                    }

                    @Override
                    public void prepareFailView(
                            String errorMessage) {

                        fail(
                                "Use case failure is unexpected.");
                    }
                };

        final MealEntryFactory factory =
                new CommonMealEntryFactory();

        final LogMealInputBoundary interactor =
                new LogMealInteractor(
                        repository,
                        presenter,
                        factory
                );

        interactor.execute(inputData);

        assertTrue(successCalled[0]);

        final List<MealEntry> meals =
                repository.getMeals(
                        "Paul",
                        LocalDate.of(
                                2026,
                                7,
                                26
                        )
                );

        assertEquals(1, meals.size());
        assertEquals(apple, meals.get(0).getFoodItem());
    }

    @Test
    void rejectsNonPositiveServings() {
        final InMemoryMealDataAccessObject repository =
                new InMemoryMealDataAccessObject();

        final FoodItem apple = new FoodItem(
                "Apple",
                100.0, // serving size
                95.0,  // calories
                0.5,   // protein
                25.0,  // carbohydrates
                0.3,   // fat
                4.4,   // fiber
                19.0,  // sugar
                2.0    // sodium
        );

        final LogMealInputData inputData =
                new LogMealInputData(
                        "Paul",
                        apple,
                        MealType.BREAKFAST,
                        0.0,
                        LocalDate.now()
                );

        final LogMealOutputBoundary presenter =
                new LogMealOutputBoundary() {
                    @Override
                    public void prepareSuccessView(
                            LogMealOutputData outputData) {

                        fail(
                                "Use case success is unexpected.");
                    }

                    @Override
                    public void prepareFailView(
                            String errorMessage) {

                        assertEquals(
                                "Servings must be greater than zero.",
                                errorMessage
                        );
                    }
                };

        final LogMealInputBoundary interactor =
                new LogMealInteractor(
                        repository,
                        presenter,
                        new CommonMealEntryFactory()
                );

        interactor.execute(inputData);

        assertTrue(
                repository
                        .getMeals(
                                "Paul",
                                LocalDate.now()
                        )
                        .isEmpty()
        );
    }
}

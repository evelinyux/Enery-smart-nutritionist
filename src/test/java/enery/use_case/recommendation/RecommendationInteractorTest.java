package enery.use_case.recommendation;

import java.time.LocalDate;
import java.util.List;

import enery.entity.exercise.ExerciseEntry;
import enery.entity.exercise.ExerciseSearchResult;
import enery.entity.meal.FoodItem;
import enery.entity.meal.MealEntry;
import enery.entity.meal.MealType;
import enery.entity.recipe.Recipe;
import enery.entity.tracking.DailyLog;
import enery.entity.user.NutritionGoal;
import enery.entity.user.User;
import enery.entity.user.UserProfile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class RecommendationInteractorTest {

    private RecommendationUserDataAccessInterface
            userDataAccess;

    private RecommendationRecipeDataAccessInterface
            recipeDataAccess;

    private RecommendationExerciseDataAccessInterface
            exerciseDataAccess;

    private RecommendationOutputBoundary presenter;

    private RecommendationInteractor interactor;

    @BeforeEach
    void setUp() {
        userDataAccess =
                mock(
                        RecommendationUserDataAccessInterface.class
                );

        recipeDataAccess =
                mock(
                        RecommendationRecipeDataAccessInterface.class
                );

        exerciseDataAccess =
                mock(
                        RecommendationExerciseDataAccessInterface.class
                );

        presenter =
                mock(
                        RecommendationOutputBoundary.class
                );

        interactor =
                new RecommendationInteractor(
                        userDataAccess,
                        recipeDataAccess,
                        exerciseDataAccess,
                        presenter
                );
    }

    @Test
    void executeSuccess() {
        final String username = "test_user";

        final RecommendationInputData inputData =
                new RecommendationInputData(username);

        final User user = mock(User.class);
        final UserProfile profile =
                mock(UserProfile.class);

        final NutritionGoal goal =
                mock(NutritionGoal.class);

        final DailyLog dailyLog =
                new DailyLog(LocalDate.now());

        when(userDataAccess.get(username))
                .thenReturn(user);

        when(user.getUserProfile())
                .thenReturn(profile);

        when(user.getNutritionGoal())
                .thenReturn(goal);

        when(user.getDailyLog(LocalDate.now()))
                .thenReturn(dailyLog);

        when(goal.getDailyCalories())
                .thenReturn(2000.0);

        when(profile.getDietaryRestrictions())
                .thenReturn(
                        List.of("Vegetarian")
                );

        final Recipe recipe = createRecipe(
                "r1",
                "Avocado Toast",
                350.0
        );

        final ExerciseSearchResult exercise =
                new ExerciseSearchResult(
                        "Running",
                        300.0,
                        60,
                        300.0
                );

        when(recipeDataAccess.fetchCandidateRecipes(
                anyInt(),
                anyList()
        )).thenReturn(List.of(recipe));

        when(exerciseDataAccess
                .fetchCandidateExercises(anyInt()))
                .thenReturn(List.of(exercise));

        interactor.execute(inputData);

        final ArgumentCaptor<RecommendationOutputData>
                captor =
                ArgumentCaptor.forClass(
                        RecommendationOutputData.class
                );

        verify(presenter)
                .prepareSuccessView(captor.capture());

        verify(presenter, never())
                .prepareFailView(anyString());

        final RecommendationOutputData outputData =
                captor.getValue();

        assertNotNull(outputData);

        assertEquals(
                "Avocado Toast",
                outputData.getRecommendedRecipes()
                        .get(0)
                        .getName()
        );

        assertEquals(
                "Running",
                outputData.getRecommendedExercises()
                        .get(0)
                        .getExerciseName()
        );
    }

    @Test
    void executeFailsWhenUserMissing() {
        final RecommendationInputData inputData =
                new RecommendationInputData(
                        "missing_user"
                );

        when(userDataAccess.get(
                "missing_user"
        )).thenReturn(null);

        interactor.execute(inputData);

        verify(presenter).prepareFailView(
                "User account was not found."
        );

        verify(presenter, never())
                .prepareSuccessView(any());
    }

    @Test
    void executeFailsWhenProfileIncomplete() {
        final User user = mock(User.class);

        when(userDataAccess.get("new_user"))
                .thenReturn(user);

        when(user.getUserProfile())
                .thenReturn(null);

        when(user.getNutritionGoal())
                .thenReturn(null);

        interactor.execute(
                new RecommendationInputData(
                        "new_user"
                )
        );

        verify(presenter).prepareFailView(
                "Please complete your user profile "
                        + "and nutrition goals first."
        );

        verify(presenter, never())
                .prepareSuccessView(any());
    }

    @Test
    void executeFiltersLoggedItems() {
        final String username = "test_user";

        final User user = mock(User.class);
        final UserProfile profile =
                mock(UserProfile.class);

        final NutritionGoal goal =
                mock(NutritionGoal.class);

        final DailyLog dailyLog =
                new DailyLog(LocalDate.now());

        final FoodItem loggedFood =
                new FoodItem(
                        "Avocado Toast",
                        350.0,
                        10.0,
                        30.0,
                        15.0,
                        5.0,
                        2.0,
                        300.0,
                        100.0
                );

        dailyLog.addMealEntry(
                new MealEntry(
                        loggedFood,
                        MealType.BREAKFAST,
                        1.0,
                        LocalDate.now()
                )
        );

        dailyLog.addExerciseEntry(
                new ExerciseEntry(
                        "Cycling",
                        45,
                        300.0,
                        LocalDate.now()
                )
        );

        when(userDataAccess.get(username))
                .thenReturn(user);

        when(user.getUserProfile())
                .thenReturn(profile);

        when(user.getNutritionGoal())
                .thenReturn(goal);

        when(user.getDailyLog(LocalDate.now()))
                .thenReturn(dailyLog);

        when(goal.getDailyCalories())
                .thenReturn(2000.0);

        when(profile.getDietaryRestrictions())
                .thenReturn(List.of());

        final Recipe duplicateRecipe =
                createRecipe(
                        "r1",
                        "Avocado Toast",
                        350.0
                );

        final Recipe newRecipe =
                createRecipe(
                        "r2",
                        "Oatmeal Bowl",
                        250.0
                );

        final ExerciseSearchResult duplicateExercise =
                new ExerciseSearchResult(
                        "Cycling",
                        300.0,
                        60,
                        300.0
                );

        final ExerciseSearchResult newExercise =
                new ExerciseSearchResult(
                        "Swimming",
                        400.0,
                        60,
                        400.0
                );

        when(recipeDataAccess.fetchCandidateRecipes(
                anyInt(),
                anyList()
        )).thenReturn(
                List.of(
                        duplicateRecipe,
                        newRecipe
                )
        );

        when(exerciseDataAccess
                .fetchCandidateExercises(anyInt()))
                .thenReturn(
                        List.of(
                                duplicateExercise,
                                newExercise
                        )
                );

        interactor.execute(
                new RecommendationInputData(username)
        );

        final ArgumentCaptor<RecommendationOutputData>
                captor =
                ArgumentCaptor.forClass(
                        RecommendationOutputData.class
                );

        verify(presenter)
                .prepareSuccessView(captor.capture());

        final RecommendationOutputData outputData =
                captor.getValue();

        assertEquals(
                1,
                outputData.getRecommendedRecipes().size()
        );

        assertEquals(
                "Oatmeal Bowl",
                outputData.getRecommendedRecipes()
                        .get(0)
                        .getName()
        );

        assertEquals(
                1,
                outputData.getRecommendedExercises().size()
        );

        assertEquals(
                "Swimming",
                outputData.getRecommendedExercises()
                        .get(0)
                        .getExerciseName()
        );
    }

    private static Recipe createRecipe(
            String id,
            String name,
            double calories) {

        return new Recipe(
                id,
                name,
                calories,
                10.0,
                2.0,
                30.0,
                15.0,
                List.of("Ingredient"),
                "http://recipe.com/" + id,
                "Test recipe",
                "Test instructions"
        );
    }
}

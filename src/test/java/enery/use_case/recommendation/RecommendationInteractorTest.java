package enery.use_case.recommendation;

import enery.entity.DailyLog;
import enery.entity.ExerciseEntry;
import enery.entity.ExerciseSearchResult;
import enery.entity.FoodItem;
import enery.entity.MealEntry;
import enery.entity.NutritionGoal;
import enery.entity.Recipe;
import enery.entity.User;
import enery.entity.UserProfile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.LocalDate;
import java.util.List;

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

    private RecommendationUserDataAccessInterface userDataAccess;
    private RecommendationOutputBoundary presenter;
    private RecommendationInteractor interactor;

    @BeforeEach
    void setUp() {
        userDataAccess = mock(RecommendationUserDataAccessInterface.class);
        presenter = mock(RecommendationOutputBoundary.class);
        interactor = new RecommendationInteractor(userDataAccess, presenter);
    }

    @Test
    @DisplayName("Scenario 1: Success Path - Valid user with profile receives filtered recommendations")
    void execute_SuccessPath() {
        // Arrange
        final String username = "test_user";
        final RecommendationInputData inputData = new RecommendationInputData(username);

        final User user = mock(User.class);
        final UserProfile profile = mock(UserProfile.class);
        final NutritionGoal goal = mock(NutritionGoal.class);
        final DailyLog todayLog = new DailyLog(LocalDate.now());

        when(userDataAccess.getUser(username)).thenReturn(user);
        when(user.getUserProfile()).thenReturn(profile);
        when(user.getNutritionGoal()).thenReturn(goal);
        when(user.getDailyLog(LocalDate.now())).thenReturn(todayLog);

        when(goal.getDailyCalories()).thenReturn(2000.0);
        when(profile.getDietaryRestrictions()).thenReturn(List.of("Vegetarian"));

        final Recipe candidateRecipe = new Recipe(
                "r1", "Avocado Toast", 350.0, 10.0, 30.0, 15.0, List.of("Avocado", "Bread"), "http://recipe.com/1"
        );
        final ExerciseSearchResult candidateExercise = new ExerciseSearchResult("Running", 300.0);

        when(userDataAccess.fetchCandidateRecipes(anyInt(), anyList()))
                .thenReturn(List.of(candidateRecipe));
        when(userDataAccess.fetchCandidateExercises(anyInt()))
                .thenReturn(List.of(candidateExercise));

        // Act
        interactor.execute(inputData);

        // Assert
        final ArgumentCaptor<RecommendationOutputData> captor = ArgumentCaptor.forClass(RecommendationOutputData.class);
        verify(presenter).prepareSuccessView(captor.capture());
        verify(presenter, never()).prepareFailView(anyString());

        final RecommendationOutputData outputData = captor.getValue();
        assertNotNull(outputData);
        assertEquals(1, outputData.getRecommendedRecipes().size());
        assertEquals("Avocado Toast", outputData.getRecommendedRecipes().get(0).getName());
        assertEquals(1, outputData.getRecommendedExercises().size());
        assertEquals("Running", outputData.getRecommendedExercises().get(0).getExerciseName());
    }

    @Test
    @DisplayName("Scenario 2: Missing User Path - Fails when user is not found in DAO")
    void execute_UserNotFound_Fails() {
        // Arrange
        final String username = "non_existent_user";
        final RecommendationInputData inputData = new RecommendationInputData(username);

        when(userDataAccess.getUser(username)).thenReturn(null);

        // Act
        interactor.execute(inputData);

        // Assert
        verify(presenter).prepareFailView("User account was not found.");
        verify(presenter, never()).prepareSuccessView(any());
    }

    @Test
    @DisplayName("Scenario 3: Incomplete Profile Path - Fails when user profile or goals are missing")
    void execute_IncompleteProfile_Fails() {
        // Arrange
        final String username = "new_user";
        final RecommendationInputData inputData = new RecommendationInputData(username);
        final User user = mock(User.class);

        when(userDataAccess.getUser(username)).thenReturn(user);
        when(user.getUserProfile()).thenReturn(null);
        when(user.getNutritionGoal()).thenReturn(null);

        // Act
        interactor.execute(inputData);

        // Assert
        verify(presenter).prepareFailView("Please complete your user profile and nutrition goals first.");
        verify(presenter, never()).prepareSuccessView(any());
    }

    @Test
    @DisplayName("Scenario 4: Duplicate Filtering Path - Filters out meals and exercises already logged today")
    void execute_DuplicateItems_AreFilteredOut() {
        // Arrange
        final String username = "test_user";
        final RecommendationInputData inputData = new RecommendationInputData(username);

        final User user = mock(User.class);
        final UserProfile profile = mock(UserProfile.class);
        final NutritionGoal goal = mock(NutritionGoal.class);
        final DailyLog todayLog = new DailyLog(LocalDate.now());

        // Log "Avocado Toast" and "Cycling" today
        final FoodItem loggedFood = new FoodItem("f1", "Avocado Toast", 350.0, 10.0, 30.0, 15.0);
        todayLog.addMealEntry(new MealEntry(loggedFood, 1.0));
        todayLog.addExerciseEntry(new ExerciseEntry("Cycling", 45.0, 300.0));

        when(userDataAccess.getUser(username)).thenReturn(user);
        when(user.getUserProfile()).thenReturn(profile);
        when(user.getNutritionGoal()).thenReturn(goal);
        when(user.getDailyLog(LocalDate.now())).thenReturn(todayLog);

        when(goal.getDailyCalories()).thenReturn(2000.0);
        when(profile.getDietaryRestrictions()).thenReturn(List.of());

        // APIs return 2 recipes & 2 exercises (one duplicate and one new for each)
        final Recipe duplicateRecipe = new Recipe(
                "r1", "Avocado Toast", 350.0, 10.0, 30.0, 15.0, List.of(), "http://recipe.com/1"
        );
        final Recipe newRecipe = new Recipe(
                "r2", "Oatmeal Bowl", 250.0, 8.0, 40.0, 5.0, List.of(), "http://recipe.com/2"
        );

        final ExerciseSearchResult duplicateExercise = new ExerciseSearchResult("Cycling", 300.0);
        final ExerciseSearchResult newExercise = new ExerciseSearchResult("Swimming", 400.0);

        when(userDataAccess.fetchCandidateRecipes(anyInt(), anyList()))
                .thenReturn(List.of(duplicateRecipe, newRecipe));
        when(userDataAccess.fetchCandidateExercises(anyInt()))
                .thenReturn(List.of(duplicateExercise, newExercise));

        // Act
        interactor.execute(inputData);

        // Assert
        final ArgumentCaptor<RecommendationOutputData> captor = ArgumentCaptor.forClass(RecommendationOutputData.class);
        verify(presenter).prepareSuccessView(captor.capture());

        final RecommendationOutputData outputData = captor.getValue();
        assertEquals(1, outputData.getRecommendedRecipes().size());
        assertEquals("Oatmeal Bowl", outputData.getRecommendedRecipes().get(0).getName());

        assertEquals(1, outputData.getRecommendedExercises().size());
        assertEquals("Swimming", outputData.getRecommendedExercises().get(0).getExerciseName());
    }
}
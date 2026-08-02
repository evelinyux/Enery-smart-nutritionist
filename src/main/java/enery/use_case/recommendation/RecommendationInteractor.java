package enery.use_case.recommendation;

import enery.entity.DailyLog;
import enery.entity.ExerciseSearchResult;
import enery.entity.NutritionGoal;
import enery.entity.Recipe;
import enery.entity.User;
import enery.entity.UserProfile;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class RecommendationInteractor implements RecommendationInputBoundary {

    private final RecommendationUserDataAccessInterface userDataAccess;
    private final RecommendationOutputBoundary presenter;

    public RecommendationInteractor(
            RecommendationUserDataAccessInterface userDataAccess,
            RecommendationOutputBoundary presenter) {

        this.userDataAccess = userDataAccess;
        this.presenter = presenter;
    }

    @Override
    public void execute(RecommendationInputData inputData) {
        final String username = inputData.getUsername();
        final User user = userDataAccess.getUser(username);

        if (user == null) {
            presenter.prepareFailView("User account was not found.");
            return;
        }

        final UserProfile profile = user.getUserProfile();
        final NutritionGoal nutritionGoal = user.getNutritionGoal();

        if (profile == null || nutritionGoal == null) {
            presenter.prepareFailView("Please complete your user profile and nutrition goals first.");
            return;
        }

        // 1. Fetch today's DailyLog from the user entity
        final DailyLog todayLog = user.getDailyLog(LocalDate.now());

        // 2. Collect names of meals and exercises already logged today (to avoid duplicate recommendations)
        final Set<String> loggedMealNames = todayLog.getMealEntries().stream()
                .map(meal -> meal.getFoodItem().getName().toLowerCase())
                .collect(Collectors.toSet());

        final Set<String> loggedExerciseNames = todayLog.getExerciseEntries().stream()
                .map(exercise -> exercise.getExerciseName().toLowerCase())
                .collect(Collectors.toSet());

        // 3. Calculate remaining calories for today
        final double targetDailyCalories = nutritionGoal.getDailyCalories();
        final double netCaloriesToday = todayLog.getNetCalories();
        final int remainingCalories = (int) Math.max(0, targetDailyCalories - netCaloriesToday);
        final double targetCarbs = nutritionGoal.getCarbohydratesInGrams();
        final double netCarbs = nutritionGoal.getCarbohydratesInGrams();
        final int remainingCarbs = (int) Math.max(0, targetCarbs - netCarbs);


        // 4. Fetch candidate recipes and exercises from external APIs/DAOs
        List<Recipe> candidateRecipes = userDataAccess.fetchCandidateRecipes(
                remainingCalories,
                profile.getDietaryRestrictions()
        );

        final List<ExerciseSearchResult> candidateExercises =
                userDataAccess.fetchCandidateExercises(remainingCalories);

        // 5. Filter out items that the user already logged today
        final List<Recipe> filteredRecipes = candidateRecipes.stream()
                .filter(recipe -> !loggedMealNames.contains(recipe.getName().toLowerCase()))
                .limit(3)
                .collect(Collectors.toList());

        final List<ExerciseSearchResult> filteredExercises = candidateExercises.stream()
                .filter(exercise -> !loggedExerciseNames.contains(exercise.getExerciseName().toLowerCase()))
                .limit(3)
                .collect(Collectors.toList());

        // 6. Present success or failure view
        if (filteredRecipes.isEmpty() && filteredExercises.isEmpty()) {
            presenter.prepareFailView("No suitable recommendations found for your remaining calorie targets today.");
        } else {
            final RecommendationOutputData outputData =
                    new RecommendationOutputData(filteredRecipes, filteredExercises);
            presenter.prepareSuccessView(outputData);
        }
    }
}
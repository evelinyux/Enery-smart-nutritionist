package enery.use_case.recommendation;

import enery.entity.tracking.DailyLog;
import enery.entity.exercise.ExerciseSearchResult;
import enery.entity.user.NutritionGoal;
import enery.entity.recipe.Recipe;
import enery.entity.user.User;
import enery.entity.user.UserProfile;
import enery.entity.user.WeightGoalPlan;
import enery.use_case.log_meal.LogMealUserDataAccessInterface;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class RecommendationInteractor implements RecommendationInputBoundary {

    private final RecommendationUserDataAccessInterface userDataAccess;
    private final RecommendationRecipeDataAccessInterface recipeDataAccess;
    private final RecommendationExerciseDataAccessInterface exerciseDataAccess;
    private final RecommendationOutputBoundary presenter;
    private final LogMealUserDataAccessInterface mealDataAccess; // For the time being


    public RecommendationInteractor(
            RecommendationUserDataAccessInterface userDataAccess,
            RecommendationRecipeDataAccessInterface recipeDataAccess,
            RecommendationExerciseDataAccessInterface exerciseDataAccess,
            RecommendationOutputBoundary presenter,
            LogMealUserDataAccessInterface mealDataAccess) {

        this.userDataAccess = userDataAccess;
        this.recipeDataAccess = recipeDataAccess;
        this.exerciseDataAccess = exerciseDataAccess;
        this.presenter = presenter;
        this.mealDataAccess = mealDataAccess;
    }

    @Override
    public void execute(RecommendationInputData inputData) {
        final String username = inputData.getUsername();
        final User user = userDataAccess.get(username);


        if (user == null) {
            presenter.prepareFailView("User account was not found.");
            return;
        }
        final LocalDate localDate = LocalDate.now();
        final UserProfile profile = user.getUserProfile();

        if (profile == null) {
            presenter.prepareFailView("Please complete your user profile first.");
            return;
        }
        final NutritionGoal nutritionGoal = user.getNutritionGoal();
        final WeightGoalPlan weightGoalPlan = user.getUserProfile().getActivePlan();
        final List<String> dietaryRestrictions = (profile != null)
                ? profile.getDietaryRestrictions()
                : List.of();

        final double targetDailyCalories = (weightGoalPlan != null )
                ? weightGoalPlan.getCurrentDailyCalorieGoal()
                : 2000.0; // if currentDailyCalorieGOal doesnt retrun a valid response, default to 2K cls per day



        // 1. Fetch today's DailyLog from the user entity
        final DailyLog todayLog = user.getDailyLog(LocalDate.now());
        // TODO : @Ethan logmealinteracotr is not currently saving meals to memory in DailyLog but for now ill acces it directly from the mealDAO

        // 2. Collect names of meals and exercises already logged today (to avoid duplicate recommendations)
        final Set<String> loggedMealNames = todayLog.getMealEntries().stream()
                .map(meal -> meal.getFoodItem().getName().toLowerCase())
                .collect(Collectors.toSet());

        final Set<String> loggedExerciseNames = todayLog.getExerciseEntries().stream()
                .map(exercise -> exercise.getExerciseName().toLowerCase())
                .collect(Collectors.toSet());

        final double targetCarbs = nutritionGoal.getDailyCarbsGrams();
        final double todayCarbs = todayLog.getTotalCarbohydratesInGrams();
        final int remainingCarbs = (int) Math.max(0, targetCarbs - todayCarbs);

        final double targetFats = nutritionGoal.getDailyFatGrams();
        final double todayFats = todayLog.getTotalFatInGrams();
        final double remainingFats = (int) Math.max(0, targetFats - todayFats);

        final double targetProtein = nutritionGoal.getDailyProteinGrams();
        final double todayProtein = todayLog.getTotalProteinInGrams();
        final double remainingProteins = (int) Math.max(0, targetProtein - todayProtein);

        final double netCaloriesToday = todayLog.getNetCalories();
        final int remainingCalories = (int) Math.max(0, targetDailyCalories - netCaloriesToday);
        System.out.println("[DEBUG Interactor] remainingCalories: " + remainingCalories);
        System.out.println("[DEBUG Interactor] remainingProteins: " + remainingProteins);
        System.out.println("[DEBUG Interactor] remainingCarbs: " + remainingCarbs);
        System.out.println("[DEBUG Interactor] remainingFats: " + remainingFats);
        System.out.println("[DEBUG Interactor] dietaryRestrictions: " + dietaryRestrictions);
        System.out.println("[DEBUG Interactor] loggedMealNames: " + loggedMealNames);
        System.out.println("[DEBUG Interactor] loggedExerciseNames: " + loggedExerciseNames);

        List<Recipe> candidateRecipes = recipeDataAccess.fetchCandidateRecipes(
                remainingCalories,
                remainingProteins,
                remainingCarbs,
                remainingFats,
                dietaryRestrictions
        );

        final List<ExerciseSearchResult> candidateExercises =
                exerciseDataAccess.fetchCandidateExercises(remainingCalories);

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

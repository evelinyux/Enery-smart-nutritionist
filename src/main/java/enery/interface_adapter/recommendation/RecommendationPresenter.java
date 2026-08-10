//package enery.interface_adapter.recommendation;
//
//import enery.entity.exercise.ExerciseSearchResult;
//import enery.entity.recipe.Recipe;
//import enery.use_case.recommendation.RecommendationOutputBoundary;
//import enery.use_case.recommendation.RecommendationOutputData;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class RecommendationPresenter implements RecommendationOutputBoundary {
//    private final RecommendationViewModel recommendationViewModel;
//
//    public RecommendationPresenter(RecommendationViewModel recommendationViewModel) {
//        this.recommendationViewModel = recommendationViewModel;
//    }
//
//    public void prepareSuccessView(RecommendationOutputData outputData) {
//        RecommendationState state = new RecommendationState(recommendationViewModel.getState());
//
//        List<Recipe> recipes = outputData.getRecommendedRecipes();
//        List<String> titles = new ArrayList<>();
//        List<String> urls = new ArrayList<>();
//
//        if (recipes != null) {
//            for (Recipe recipe : recipes) {
//                titles.add(recipe.getName());
//                urls.add(recipe.getRecipeUrl() != null ? recipe.getRecipeUrl() : "");
//            }
//        }
//
//        // Map exercise entities into formatted names with calories
//        List<String> workoutNames = new ArrayList<>();
//        List<ExerciseSearchResult> exercises = outputData.getRecommendedExercises();
//        if (exercises != null) {
//            for (ExerciseSearchResult exercise : exercises) {
//                workoutNames.add(exercise.getExerciseName() + " (" + (int) exercise.getTotalCalories() + " kcal)");
//            }
//        }
//
//        state.setRecommendedRecipes(recipes != null ? recipes : new ArrayList<>());
//        state.setRecipeTitles(titles);
//        state.setRecipeUrls(urls);
//        state.setRecommendedExercises(workoutNames);
//        state.setRecommendationError(null);
//        state.setLoading(false);
//
//        recommendationViewModel.setState(state);
//        recommendationViewModel.firePropertyChanged();
//    }
//
//    public void prepareFailView(String error) {
//        RecommendationState state = new RecommendationState(recommendationViewModel.getState());
//        state.setRecommendationError(error);
//        state.setLoading(false);
//
//        recommendationViewModel.setState(state);
//        recommendationViewModel.firePropertyChanged();
//    }
//}
package enery.interface_adapter.recommendation;

import enery.entity.exercise.ExerciseSearchResult;
import enery.entity.recipe.Recipe;
import enery.use_case.recommendation.RecommendationOutputBoundary;
import enery.use_case.recommendation.RecommendationOutputData;

import java.util.ArrayList;
import java.util.List;

public class RecommendationPresenter implements RecommendationOutputBoundary {
    private final RecommendationViewModel recommendationViewModel;

    public RecommendationPresenter(RecommendationViewModel recommendationViewModel) {
        this.recommendationViewModel = recommendationViewModel;
    }

    public void prepareSuccessView(RecommendationOutputData outputData) {
        RecommendationState state = new RecommendationState(recommendationViewModel.getState());

        List<Recipe> recipes = outputData.getRecommendedRecipes();
        List<String> titles = new ArrayList<>();
        List<String> urls = new ArrayList<>();

        if (recipes != null) {
            for (Recipe recipe : recipes) {
                titles.add(recipe.getName());
                urls.add(recipe.getRecipeUrl() != null ? recipe.getRecipeUrl() : "");
            }
        }

        // Map exercise entities into formatted names with calories
        List<String> workoutNames = new ArrayList<>();
        List<ExerciseSearchResult> exercises = outputData.getRecommendedExercises();
        if (exercises != null) {
            for (ExerciseSearchResult exercise : exercises) {
                workoutNames.add(exercise.getExerciseName() + " (" + (int) exercise.getTotalCalories() + " kcal)");
            }
        }

        state.setRecommendedRecipes(recipes != null ? recipes : new ArrayList<>());
        state.setRecipeTitles(titles);
        state.setRecipeUrls(urls);
        state.setRecommendedExercises(workoutNames);
        state.setRecommendationError(null);
        state.setLoading(false);

        recommendationViewModel.setState(state);
        recommendationViewModel.firePropertyChanged();
    }

    public void prepareFailView(String error) {
        RecommendationState state = new RecommendationState(recommendationViewModel.getState());
        state.setRecommendationError(error);
        state.setLoading(false);

        recommendationViewModel.setState(state);
        recommendationViewModel.firePropertyChanged();
    }
}
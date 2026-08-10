package enery.app;

import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import enery.interface_adapter.daily_check.DailyCheckPresenter;
import enery.interface_adapter.daily_check.DailyCheckViewModel;
import enery.use_case.daily_check.*;
import enery.use_case.recommendation.*;
import enery.view.recommendation.RecipeView;
import enery.view.recommendation.RecommendationView;
import okhttp3.OkHttpClient;

import enery.interface_adapter.daily_check.DailyCheckController;
import enery.interface_adapter.daily_check.DailyCheckPresenter;
import enery.interface_adapter.daily_check.DailyCheckViewModel;
import enery.use_case.daily_check.DailyCheckInputBoundary;
import enery.use_case.daily_check.DailyCheckInteractor;
import enery.use_case.daily_check.DailyCheckOutputBoundary;
import enery.use_case.daily_check.DailyCheckExerciseDataAccessInterface;
import enery.use_case.daily_check.DailyCheckMealDataAccessInterface;
import enery.view.daily_check.DailyCheckView;
import enery.data_access.tracking.DailyTrackingDataAccessObject;
import enery.data_access.in_memory.InMemoryExerciseDataAccessObject;
import enery.data_access.in_memory.InMemoryMealDataAccessObject;
import enery.data_access.in_memory.InMemoryPlanDataAccessObject;
import enery.data_access.in_memory.InMemoryUserDataAccessObject;
import enery.data_access.api.ApiNinjasCaloriesBurnedDataAccessObject;
import enery.data_access.api.UsdaNutritionDataAccessObject;
import enery.entity.exercise.CommonExerciseEntryFactory;
import enery.entity.exercise.ExerciseEntryFactory;
import enery.entity.meal.CommonMealEntryFactory;
import enery.entity.meal.MealEntryFactory;
import enery.entity.user.CommonUserFactory;
import enery.entity.user.UserFactory;
import enery.interface_adapter.shared.ViewManagerModel;
import enery.interface_adapter.change_password.ChangePasswordController;
import enery.interface_adapter.change_password.ChangePasswordPresenter;
import enery.interface_adapter.change_password.LoggedInViewModel;
import enery.interface_adapter.forgot_password.ForgotPasswordController;
import enery.interface_adapter.forgot_password.ForgotPasswordPresenter;
import enery.interface_adapter.forgot_password.ForgotPasswordViewModel;
import enery.interface_adapter.log_exercise.LogExerciseController;
import enery.interface_adapter.log_exercise.LogExercisePresenter;
import enery.interface_adapter.log_exercise.LogExerciseViewModel;
import enery.interface_adapter.log_meal.LogMealController;
import enery.interface_adapter.log_meal.LogMealPresenter;
import enery.interface_adapter.log_meal.LogMealViewModel;
import enery.interface_adapter.login.LoginController;
import enery.interface_adapter.login.LoginPresenter;
import enery.interface_adapter.login.LoginViewModel;
import enery.interface_adapter.logout.LogoutController;
import enery.interface_adapter.logout.LogoutPresenter;
import enery.interface_adapter.profile.ProfileController;
import enery.interface_adapter.profile.ProfilePresenter;
import enery.interface_adapter.profile.ProfileViewModel;
import enery.interface_adapter.progress.ProgressController;
import enery.interface_adapter.progress.ProgressPresenter;
import enery.interface_adapter.progress.ProgressViewModel;
import enery.interface_adapter.search_exercise.SearchExerciseController;
import enery.interface_adapter.search_exercise.SearchExercisePresenter;
import enery.interface_adapter.search_exercise.SearchExerciseViewModel;
import enery.interface_adapter.search_food.SearchFoodController;
import enery.interface_adapter.search_food.SearchFoodPresenter;
import enery.interface_adapter.search_food.SearchFoodViewModel;
import enery.interface_adapter.signup.SignupController;
import enery.interface_adapter.signup.SignupPresenter;
import enery.interface_adapter.signup.SignupViewModel;
import enery.interface_adapter.tracking.TrackingController;
import enery.interface_adapter.tracking.TrackingPresenter;
import enery.interface_adapter.tracking.TrackingViewModel;
import enery.use_case.change_password.ChangePasswordInputBoundary;
import enery.use_case.change_password.ChangePasswordInteractor;
import enery.use_case.change_password.ChangePasswordOutputBoundary;
import enery.use_case.forgot_password.ForgotPasswordInputBoundary;
import enery.use_case.forgot_password.ForgotPasswordInteractor;
import enery.use_case.forgot_password.ForgotPasswordOutputBoundary;
import enery.use_case.log_exercise.LogExerciseInputBoundary;
import enery.use_case.log_exercise.LogExerciseInteractor;
import enery.use_case.log_exercise.LogExerciseOutputBoundary;
import enery.use_case.log_meal.LogMealInputBoundary;
import enery.use_case.log_meal.LogMealInteractor;
import enery.use_case.log_meal.LogMealOutputBoundary;
import enery.use_case.login.LoginInputBoundary;
import enery.use_case.login.LoginInteractor;
import enery.use_case.login.LoginOutputBoundary;
import enery.use_case.logout.LogoutInputBoundary;
import enery.use_case.logout.LogoutInteractor;
import enery.use_case.logout.LogoutOutputBoundary;
import enery.use_case.profile.ProfileInputBoundary;
import enery.use_case.profile.ProfileInteractor;
import enery.use_case.profile.ProfileOutputBoundary;
import enery.use_case.progress.ProgressDataInterface;
import enery.use_case.progress.ProgressInputBoundary;
import enery.use_case.progress.ProgressInteractor;
import enery.use_case.progress.ProgressOutputBoundary;
import enery.use_case.search_exercise.CaloriesBurnedDataAccessInterface;
import enery.use_case.search_exercise.SearchExerciseInputBoundary;
import enery.use_case.search_exercise.SearchExerciseInteractor;
import enery.use_case.search_exercise.SearchExerciseOutputBoundary;
import enery.use_case.search_food.NutritionDataAccessInterface;
import enery.use_case.search_food.SearchFoodInputBoundary;
import enery.use_case.search_food.SearchFoodInteractor;
import enery.use_case.search_food.SearchFoodOutputBoundary;
import enery.use_case.signup.SignupInputBoundary;
import enery.use_case.signup.SignupInteractor;
import enery.use_case.signup.SignupOutputBoundary;
import enery.use_case.tracking.TrackingDataAccessInterface;
import enery.use_case.tracking.TrackingInputBoundary;
import enery.use_case.tracking.TrackingInteractor;
import enery.use_case.tracking.TrackingOutputBoundary;
import enery.view.auth.ForgotPasswordView;
import enery.view.exercise.LogExerciseView;
import enery.view.meal.LogMealView;
import enery.view.auth.LoginView;
import enery.view.profile.ProfileView;
import enery.view.tracking.ProgressView;
import enery.view.auth.ChangePasswordView;
import enery.view.auth.SignupView;
import enery.view.tracking.TrackingView;
import enery.view.shared.ViewManager;
import enery.data_access.api.RecipeApiDataAccessObject;
import enery.interface_adapter.recipe.RecipePresenter;
import enery.interface_adapter.recipe.RecipeViewModel;
import enery.interface_adapter.recommendation.*;
import enery.view.home.HomeView;


/**
 * Builds and wires the ENERY Smart Nutritionist application.
 *
 * <p>This class is the composition root of the application. It creates the
 * concrete views, view models, controllers, presenters, interactors,
 * repositories, API adapters, and factories used by the Clean Architecture
 * layers.</p>
 *
 * <p>Each {@code add...View()} method must be called before the related
 * {@code add...UseCase()} method because the use-case method attaches a
 * controller to an existing view.</p>
 */
public final class AppBuilder {

    // Shared navigation infrastructure.
    private final JPanel cardPanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();
    private final ViewManagerModel viewManagerModel =
            new ViewManagerModel();

    private final ViewManager viewManager =
            new ViewManager(
                    cardPanel,
                    cardLayout,
                    viewManagerModel
            );

    // Shared factories.
    private final UserFactory userFactory =
            new CommonUserFactory();

    private final MealEntryFactory mealEntryFactory =
            new CommonMealEntryFactory();

    private final ExerciseEntryFactory exerciseEntryFactory =
            new CommonExerciseEntryFactory();

    // Shared application repositories.
    private final InMemoryUserDataAccessObject userDataAccessObject =
            new InMemoryUserDataAccessObject();

    private final InMemoryMealDataAccessObject mealDataAccessObject =
            new InMemoryMealDataAccessObject(userDataAccessObject);

    private DailyCheckView dailyCheckView;
    private DailyCheckViewModel dailyCheckViewModel;

    private final InMemoryExerciseDataAccessObject
            exerciseDataAccessObject =
            new InMemoryExerciseDataAccessObject();

    // Shared HTTP client.
    private final OkHttpClient httpClient =
            new OkHttpClient();


    // Authentication and home components.
    private SignupView signupView;
    private SignupViewModel signupViewModel;

    private LoginView loginView;
    private LoginViewModel loginViewModel;

    private ForgotPasswordView forgotPasswordView;
    private ForgotPasswordViewModel forgotPasswordViewModel;

    private HomeView homeView;
    private LoggedInViewModel loggedInViewModel;
    private ChangePasswordView changePasswordView;


    // Profile components.
    private ProfileView profileView;
    private ProfileViewModel profileViewModel;

    // Food-search and meal-logging components.
    private LogMealView logMealView;
    private LogMealViewModel logMealViewModel;
    private SearchFoodViewModel searchFoodViewModel;

    // Exercise-search and exercise-logging components.
    private LogExerciseView logExerciseView;
    private LogExerciseViewModel logExerciseViewModel;
    private SearchExerciseViewModel searchExerciseViewModel;

    // Progress components.
    private ProgressView progressView;
    private ProgressViewModel progressViewModel;

    // Tracking components.
    private TrackingView trackingView;
    private TrackingViewModel trackingViewModel;

    // Recommendation and Recipe components.
    private RecommendationView recommendationView;
    private RecommendationViewModel recommendationViewModel;
    private RecipeViewModel recipeViewModel;
    private RecipeView recipeView;

    /**
     * Creates the builder and configures the shared card panel.
     */
    public AppBuilder() {
        cardPanel.setLayout(cardLayout);
    }

    /**
     * Creates and registers the signup view.
     *
     * @return this builder
     */
    public AppBuilder addSignupView() {
        signupViewModel =
                new SignupViewModel();

        signupView =
                new SignupView(signupViewModel);

        cardPanel.add(
                signupView,
                signupView.getViewName()
        );

        return this;
    }

    /**
     * Creates and registers the login view.
     *
     * @return this builder
     */
    public AppBuilder addLoginView() {
        loginViewModel =
                new LoginViewModel();

        loginView =
                new LoginView(
                        loginViewModel,
                        viewManagerModel
                );

        cardPanel.add(
                loginView,
                loginView.getViewName()
        );

        return this;
    }

    /**
     * Creates and registers the forgot-password view.
     *
     * @return this builder
     */
    public AppBuilder addForgotPasswordView() {
        forgotPasswordViewModel =
                new ForgotPasswordViewModel();

        forgotPasswordView =
                new ForgotPasswordView(
                        forgotPasswordViewModel,
                        viewManagerModel
                );

        cardPanel.add(
                forgotPasswordView,
                forgotPasswordView.getViewName()
        );

        return this;
    }

    /**
     * Creates and registers the home view.
     *
     * @return this builder
     */
    public AppBuilder addHomeView() {
        loggedInViewModel =
                new LoggedInViewModel();

        homeView =
                new HomeView(
                        loggedInViewModel,
                        viewManagerModel
                );

        cardPanel.add(
                homeView,
                homeView.getViewName()
        );

        return this;
    }

    /**
     * Creates and registers the Change Password view.
     *
     * @return this builder
     */
    public AppBuilder addChangePasswordView() {
        changePasswordView =
                new ChangePasswordView(
                        loggedInViewModel,
                        viewManagerModel
                );

        cardPanel.add(
                changePasswordView,
                changePasswordView.getViewName()
        );

        return this;
    }

    /**
     * Creates and registers the profile view.
     *
     * @return this builder
     */
    public AppBuilder addProfileView() {
        profileViewModel =
                new ProfileViewModel();

        profileView =
                new ProfileView(profileViewModel);

        cardPanel.add(
                profileView,
                profileView.getViewName()
        );

        return this;
    }

    /**
     * Creates and registers the combined food-search and meal-logging view.
     *
     * @return this builder
     */
    public AppBuilder addLogMealView() {
        logMealViewModel =
                new LogMealViewModel();

        searchFoodViewModel =
                new SearchFoodViewModel();

        logMealView =
                new LogMealView(
                        logMealViewModel,
                        searchFoodViewModel,
                        viewManagerModel,
                        loggedInViewModel
                );

        cardPanel.add(
                logMealView,
                logMealView.getViewName()
        );

        return this;
    }

    /**
     * Creates and registers the combined exercise-search and
     * exercise-logging view.
     *
     * @return this builder
     */
    public AppBuilder addLogExerciseView() {
        searchExerciseViewModel =
                new SearchExerciseViewModel();

        logExerciseViewModel =
                new LogExerciseViewModel();

        logExerciseView =
                new LogExerciseView(
                        searchExerciseViewModel,
                        logExerciseViewModel,
                        viewManagerModel,
                        loggedInViewModel
                );

        cardPanel.add(
                logExerciseView,
                logExerciseView.getViewName()
        );

        return this;
    }

    /**
     * Creates and registers the progress view.
     *
     * @return this builder
     */
    public AppBuilder addProgressView() {
        progressViewModel =
                new ProgressViewModel();

        progressView =
                new ProgressView(
                        progressViewModel,
                        viewManagerModel,
                        loggedInViewModel
                );

        cardPanel.add(
                progressView,
                progressView.getViewName()
        );

        return this;
    }

    /**
     * Creates and registers the tracking view.
     *
     * @return this builder
     */
    public AppBuilder addTrackingView() {
        trackingViewModel =
                new TrackingViewModel();

        trackingView =
                new TrackingView(
                        trackingViewModel,
                        viewManagerModel,
                        loggedInViewModel
                );

        cardPanel.add(
                trackingView,
                trackingView.getViewName()
        );

        return this;
    }

    /**
     * Creates and registers the recommendation view.
     *
     * @return this builder
     */
    public AppBuilder addRecommendationView() {
        recommendationViewModel = new RecommendationViewModel();
        recipeViewModel = new RecipeViewModel();

        final RecipePresenter recipePresenter = new RecipePresenter(recipeViewModel);

        recommendationView = new RecommendationView(
                recommendationViewModel,
                loggedInViewModel,
                recipePresenter,
                viewManagerModel
        );

        cardPanel.add(
                recommendationView,
                RecommendationViewModel.VIEW_NAME
        );

        return this;
    }



    /**
     * Creates and registers the detailed recipe view.
     *
     * @return this builder
     */
    public AppBuilder addRecipeView() {
        RecipeView recipeView = new RecipeView(recipeViewModel, viewManagerModel);

        cardPanel.add(
                recipeView,
                RecipeViewModel.VIEW_NAME
        );

        return this;
    }

    /**
     * Wires the signup use case.
     *
     * @return this builder
     */
    public AppBuilder addSignupUseCase() {
        final SignupOutputBoundary presenter =
                new SignupPresenter(
                        viewManagerModel,
                        signupViewModel,
                        loginViewModel
                );

        final SignupInputBoundary interactor =
                new SignupInteractor(
                        userDataAccessObject,
                        presenter,
                        userFactory
                );

        signupView.setSignupController(
                new SignupController(interactor)
        );

        return this;
    }

    /**
     * Wires the login use case.
     *
     * @return this builder
     */
    public AppBuilder addLoginUseCase() {
        final LoginOutputBoundary presenter =
                new LoginPresenter(
                        viewManagerModel,
                        loggedInViewModel,
                        loginViewModel,
                        signupViewModel
                );

        final LoginInputBoundary interactor =
                new LoginInteractor(
                        userDataAccessObject,
                        presenter
                );

        loginView.setLoginController(
                new LoginController(interactor)
        );

        return this;
    }

    /**
     * Wires the forgot-password use case.
     *
     * @return this builder
     */
    public AppBuilder addForgotPasswordUseCase() {
        final ForgotPasswordOutputBoundary presenter =
                new ForgotPasswordPresenter(
                        viewManagerModel,
                        forgotPasswordViewModel
                );

        final ForgotPasswordInputBoundary interactor =
                new ForgotPasswordInteractor(
                        userDataAccessObject,
                        presenter
                );

        forgotPasswordView.setForgotPasswordController(
                new ForgotPasswordController(interactor)
        );

        return this;
    }

    /**
     * Wires the change-password use case.
     *
     * @return this builder
     */
    public AppBuilder addChangePasswordUseCase() {
        final ChangePasswordOutputBoundary presenter =
                new ChangePasswordPresenter(
                        loggedInViewModel
                );

        final ChangePasswordInputBoundary interactor =
                new ChangePasswordInteractor(
                        userDataAccessObject,
                        presenter
                );

        changePasswordView.setChangePasswordController(
                new ChangePasswordController(interactor)
        );

        return this;
    }

    /**
     * Wires the logout use case.
     *
     * @return this builder
     */
    public AppBuilder addLogoutUseCase() {
        final LogoutOutputBoundary presenter =
                new LogoutPresenter(
                        viewManagerModel,
                        loggedInViewModel,
                        loginViewModel
                );

        final LogoutInputBoundary interactor =
                new LogoutInteractor(
                        userDataAccessObject,
                        presenter
                );

        homeView.setLogoutController(
                new LogoutController(interactor)
        );

        return this;
    }

    /**
     * Wires the profile use case.
     *
     * @return this builder
     */
    public AppBuilder addProfileUseCase() {

        final ProfileOutputBoundary presenter =
                new ProfilePresenter(
                        viewManagerModel,
                        profileViewModel,
                        loggedInViewModel
                );

        final ProfileInputBoundary interactor =
                new ProfileInteractor(
                        userDataAccessObject,
                        presenter
                );

        profileView.setProfileController(
                new ProfileController(
                        interactor
                )
        );

        return this;
    }

    /**
     * Wires the meal-logging use case.
     *
     * @return this builder
     */
    public AppBuilder addLogMealUseCase() {
        final LogMealOutputBoundary presenter =
                new LogMealPresenter(
                        logMealViewModel
                );

        final LogMealInputBoundary interactor =
                new LogMealInteractor(
                        mealDataAccessObject,
                        presenter,
                        mealEntryFactory
                );

        logMealView.setLogMealController(
                new LogMealController(interactor)
        );

        return this;
    }

    /**
     * Creates and registers the Daily Check view.
     *
     * @return this builder
     */
    public AppBuilder addDailyCheckView() {
        dailyCheckViewModel = new DailyCheckViewModel();

        // Assuming your DailyCheckView takes the ViewModel and ViewManagerModel in its constructor
        dailyCheckView = new DailyCheckView(dailyCheckViewModel, viewManagerModel, loggedInViewModel);

        cardPanel.add(
                dailyCheckView,
                DailyCheckViewModel.VIEW_NAME
        );

        return this;
    }

    /**
     * Wires the USDA-backed food-search use case.
     *
     * @return this builder
     */
    public AppBuilder addSearchFoodUseCase() {
//        final NutritionDataAccessInterface dataAccessObject =
//                new UsdaNutritionDataAccessObject(
//                        httpClient,
//                        getRequiredEnvironmentVariable(
//                                "USDA_API_KEY"
//                        )
//                );
        final NutritionDataAccessInterface dataAccessObject = new UsdaNutritionDataAccessObject(getUnsafeOkHttpClient(),
                getRequiredEnvironmentVariable("USDA_API_KEY"));

        final SearchFoodOutputBoundary presenter =
                new SearchFoodPresenter(
                        searchFoodViewModel
                );

        final SearchFoodInputBoundary interactor =
                new SearchFoodInteractor(
                        dataAccessObject,
                        presenter
                );

        logMealView.setSearchFoodController(
                new SearchFoodController(interactor)
        );

        return this;
    }

    /**
     * Wires the API-backed exercise-search use case.
     *
     * @return this builder
     */
    public AppBuilder addSearchExerciseUseCase() {

        final CaloriesBurnedDataAccessInterface
                dataAccessObject =
                new ApiNinjasCaloriesBurnedDataAccessObject(
                        httpClient,
                        getRequiredEnvironmentVariable(
                                "API_NINJAS_KEY"
                        )
                );

        final SearchExerciseOutputBoundary presenter =
                new SearchExercisePresenter(
                        searchExerciseViewModel
                );

        final SearchExerciseInputBoundary interactor =
                new SearchExerciseInteractor(
                        dataAccessObject,
                        userDataAccessObject,
                        presenter
                );

        logExerciseView.setSearchController(
                new SearchExerciseController(
                        interactor
                )
        );

        return this;
    }

    /**
     * Wires the exercise-logging use case.
     *
     * @return this builder
     */
    public AppBuilder addLogExerciseUseCase() {
        final LogExerciseOutputBoundary presenter =
                new LogExercisePresenter(
                        logExerciseViewModel
                );

        final LogExerciseInputBoundary interactor =
                new LogExerciseInteractor(
                        exerciseDataAccessObject,
                        presenter,
                        exerciseEntryFactory
                );

        logExerciseView.setLogController(
                new LogExerciseController(interactor)
        );

        return this;
    }

//    /**
//     * Wires the Daily Check use case.
//     *
//     * @return this builder
//     */
//    public AppBuilder addDailyCheckUseCase() {
//        final DailyCheckOutputBoundary presenter =
//                new DailyCheckPresenter(viewManagerModel, dailyCheckViewModel);
//
//        final DailyCheckInputBoundary interactor =
//                new DailyCheckInteractor(
//                        (DailyCheckMealDataAccessInterface) mealDataAccessObject,
//                        (DailyCheckExerciseDataAccessInterface) exerciseDataAccessObject,
//                        presenter
//                );
//
//        dailyCheckView.setDailyCheckController(
//                new DailyCheckController(interactor)
//        );
//
//        return this;
//    }

    /**
     * Wires the progress use case.
     *
     * @return this builder
     */
    public AppBuilder addProgressUseCase() {
        final ProgressDataInterface dataAccessObject =
                new InMemoryPlanDataAccessObject(
                        userDataAccessObject,
                        mealDataAccessObject
                );

        final ProgressOutputBoundary presenter =
                new ProgressPresenter(
                        viewManagerModel,
                        progressViewModel
                );

        final ProgressInputBoundary interactor =
                new ProgressInteractor(
                        dataAccessObject,
                        presenter
                );

        progressView.setProgressController(
                new ProgressController(interactor)
        );

        return this;
    }

    /**
     * Wires the tracking use case.
     *
     * @return this builder
     */
    public AppBuilder addTrackingUseCase() {
        final TrackingDataAccessInterface dataAccessObject =
                new DailyTrackingDataAccessObject(
                        mealDataAccessObject,
                        exerciseDataAccessObject
                );

        final TrackingOutputBoundary presenter =
                new TrackingPresenter(
                        trackingViewModel
                );

        final TrackingInputBoundary interactor =
                new TrackingInteractor(
                        dataAccessObject,
                        presenter
                );

        trackingView.setTrackingController(
                new TrackingController(interactor)
        );

        return this;
    }

    /**
     * Wires the recommendation use case.
     *
     * @return this builder
     */
    public AppBuilder addRecommendationUseCase() {
        final RecommendationOutputBoundary presenter =
                new RecommendationPresenter(recommendationViewModel);

//        final RecommendationRecipeDataAccessInterface recipeApiDAO =
//                new RecipeApiDataAccessObject(
//                        httpClient,
//                        getRequiredEnvironmentVariable("RECIPE_API_KEY")
//                );
        final RecommendationRecipeDataAccessInterface recipeApiDAO =
                new RecipeApiDataAccessObject(
                        getRequiredEnvironmentVariable("RECIPE_API_KEY")
                );

        final RecommendationExerciseDataAccessInterface exerciseDAO =
                new InMemoryExerciseDataAccessObject();

        final RecommendationInputBoundary interactor =
                new RecommendationInteractor(
                        userDataAccessObject,
                        recipeApiDAO,
                        exerciseDAO,
                        presenter,
                        mealDataAccessObject
                );

        recommendationView.setRecommendationController(
                new RecommendationController(interactor)
        );
        // updates the recViewModel if a new meal was logged
        if (logMealViewModel != null) {
            logMealViewModel.addPropertyChangeListener(evt -> {
                if ("state".equals(evt.getPropertyName())) {
                    final String successMsg = logMealViewModel.getState().getSuccessMessage();
                    if (successMsg != null && !successMsg.isBlank()) {
                        System.out.println("[DEBUG AppBuilder] Meal successfully logged (" + successMsg + ") -> Setting stale=true");
                        recommendationViewModel.setStale(true);
                    }
                }
            });
        }
        return this;
    }


    /**
     * Wires the Daily Check use case.
     *
     * @return this builder
     */
    public AppBuilder addDailyCheckUseCase() {
        // 1. Cast existing DAOs from AppBuilder fields
        final DailyCheckMealDataAccessInterface mealDAO =
                (DailyCheckMealDataAccessInterface) mealDataAccessObject;
        final DailyCheckExerciseDataAccessInterface exerciseDAO =
                (DailyCheckExerciseDataAccessInterface) exerciseDataAccessObject;

        // 2. Create Presenter with ViewManagerModel and DailyCheckViewModel
        final DailyCheckOutputBoundary presenter =
                new DailyCheckPresenter(viewManagerModel, dailyCheckViewModel);

        // 3. Instantiate Interactor with both DAOs + Presenter
        final DailyCheckInputBoundary interactor =
                new DailyCheckInteractor(mealDAO, exerciseDAO, presenter);

        // 4. Set Controller on dailyCheckView (NOT homeView)
        dailyCheckView.setDailyCheckController(
                new DailyCheckController(interactor)
        );

        return this;
    }

    /**
     * Wires and registers the Health Assessment module.
     *
     * @return this builder
     */
    public AppBuilder addHealthAssessmentModule() {
        enery.use_case.tracking.TrackingDataAccessInterface trackingDAO =
                new DailyTrackingDataAccessObject(
                        mealDataAccessObject,
                        exerciseDataAccessObject
                );

        enery.use_case.health_assessment.HealthAssessmentDataAccessInterface healthDataAccess =
                new enery.data_access.HealthAssessment_database.HealthAssessmentDataAccessObject(trackingDAO);

        enery.interface_adapter.health_assessment.HealthAssessmentViewModel healthAssessmentViewModel =
                new enery.interface_adapter.health_assessment.HealthAssessmentViewModel();

        enery.use_case.health_assessment.HealthAssessmentOutputBoundary presenter =
                new enery.interface_adapter.health_assessment.HealthAssessmentPresenter(healthAssessmentViewModel);

        enery.use_case.health_assessment.HealthAssessmentInputBoundary interactor =
                new enery.use_case.health_assessment.HealthAssessmentInteractor(presenter, healthDataAccess);

        enery.interface_adapter.health_assessment.HealthAssessmentController controller =
                new enery.interface_adapter.health_assessment.HealthAssessmentController(interactor);

        enery.view.health_assessment.HealthAssessmentView healthAssessmentView =
                new enery.view.health_assessment.HealthAssessmentView(
                        healthAssessmentViewModel,
                        controller,
                        viewManagerModel,
                        loggedInViewModel);

        cardPanel.add(healthAssessmentView,enery.interface_adapter.health_assessment.HealthAssessmentViewModel.VIEW_NAME
        );

        return this;
    }

    /**
     * Creates the main application window.
     *
     * @return constructed application window
     */
    public JFrame build() {
        final JFrame application =
                new JFrame(
                        "ENERY Smart Nutritionist"
                );

        application.setDefaultCloseOperation(
                WindowConstants.EXIT_ON_CLOSE
        );

        application.add(cardPanel);

        viewManagerModel.setState(
                signupView.getViewName()
        );

        viewManagerModel.firePropertyChanged();

        return application;
    }

    /**
     * Reads a required environment variable.
     *
     * @param variableName environment-variable name
     * @return configured environment-variable value
     * @throws IllegalStateException if the variable is missing
     */
    private static String getRequiredEnvironmentVariable(
            String variableName) {

        final String value =
                System.getenv(variableName);

        if (value == null || value.isBlank()) {
            throw new IllegalStateException(
                    variableName
                            + " environment variable is not configured."
            );
        }

        return value;
    }

//    /**
//     * Creates an OkHttpClient that trusts all SSL certificates to bypass local SSL verification issues.
//     */
//    private static OkHttpClient getUnsafeOkHttpClient() {
//        try {
//            final javax.net.ssl.TrustManager[] trustAllCerts = new javax.net.ssl.TrustManager[]{
//                    new javax.net.ssl.X509TrustManager() {
//                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
//                            return new java.security.cert.X509Certificate[0];
//                        }
//                        public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {}
//                        public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {}
//                    }
//            };
//
//            final javax.net.ssl.SSLContext sslContext = javax.net.ssl.SSLContext.getInstance("SSL");
//            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
//
//            return new OkHttpClient.Builder()
//                    .sslSocketFactory(sslContext.getSocketFactory(), (javax.net.ssl.X509TrustManager) trustAllCerts[0])
//                    .hostnameVerifier((hostname, session) -> true)
//                    .build();
//        } catch (Exception e) {
//            return new OkHttpClient();
//        }
//    }
private static OkHttpClient getUnsafeOkHttpClient() {
    try {
        final javax.net.ssl.TrustManager[] trustAllCerts = new javax.net.ssl.TrustManager[]{
                new javax.net.ssl.X509TrustManager() {
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return new java.security.cert.X509Certificate[0];
                    }
                    public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {}
                    public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {}
                }
        };

        final javax.net.ssl.SSLContext sslContext = javax.net.ssl.SSLContext.getInstance("SSL");
        sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

        return new OkHttpClient.Builder()
                .sslSocketFactory(sslContext.getSocketFactory(), (javax.net.ssl.X509TrustManager) trustAllCerts[0])
                .hostnameVerifier((hostname, session) -> true)
                .build();
    } catch (Exception e) {
        throw new RuntimeException(e);
    }
}
}

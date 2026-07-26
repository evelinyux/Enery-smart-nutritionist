package enery.app;

import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import okhttp3.OkHttpClient;

import enery.data_access.api.ApiNinjasNutritionDataAccessObject;
import enery.interface_adapter.search_food.SearchFoodController;
import enery.interface_adapter.search_food.SearchFoodPresenter;
import enery.interface_adapter.search_food.SearchFoodViewModel;
import enery.use_case.search_food.NutritionDataAccessInterface;
import enery.use_case.search_food.SearchFoodInputBoundary;
import enery.use_case.search_food.SearchFoodInteractor;
import enery.use_case.search_food.SearchFoodOutputBoundary;
import enery.data_access.InMemoryExerciseDataAccessObject;
import enery.data_access.InMemoryMealDataAccessObject;
import enery.data_access.InMemoryUserDataAccessObject;
import enery.data_access.api.ApiNinjasCaloriesBurnedDataAccessObject;
import enery.entity.CommonExerciseEntryFactory;
import enery.entity.CommonMealEntryFactory;
import enery.entity.CommonUserFactory;
import enery.entity.ExerciseEntryFactory;
import enery.entity.MealEntryFactory;
import enery.entity.UserFactory;
import enery.interface_adapter.ViewManagerModel;
import enery.interface_adapter.change_password.ChangePasswordController;
import enery.interface_adapter.change_password.ChangePasswordPresenter;
import enery.interface_adapter.change_password.LoggedInViewModel;
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
import enery.interface_adapter.search_exercise.SearchExerciseController;
import enery.interface_adapter.search_exercise.SearchExercisePresenter;
import enery.interface_adapter.search_exercise.SearchExerciseViewModel;
import enery.interface_adapter.signup.SignupController;
import enery.interface_adapter.signup.SignupPresenter;
import enery.interface_adapter.signup.SignupViewModel;
import enery.use_case.change_password.ChangePasswordInputBoundary;
import enery.use_case.change_password.ChangePasswordInteractor;
import enery.use_case.change_password.ChangePasswordOutputBoundary;
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
import enery.use_case.search_exercise.CaloriesBurnedDataAccessInterface;
import enery.use_case.search_exercise.SearchExerciseInputBoundary;
import enery.use_case.search_exercise.SearchExerciseInteractor;
import enery.use_case.search_exercise.SearchExerciseOutputBoundary;
import enery.use_case.signup.SignupInputBoundary;
import enery.use_case.signup.SignupInteractor;
import enery.use_case.signup.SignupOutputBoundary;
import enery.view.HomeView;
import enery.view.LogExerciseView;
import enery.view.LogMealView;
import enery.view.LoginView;
import enery.view.SignupView;
import enery.view.ViewManager;

/**
 * Builds and wires the ENERY Smart Nutritionist application.
 *
 * <p>This class contains the application composition logic for the Clean
 * Architecture layers. It creates views, view models, controllers, presenters,
 * interactors, factories, and data-access objects.</p>
 *
 * <p>Each view must be added before its corresponding use case because the
 * use-case method attaches a controller to an existing view.</p>
 */
public final class AppBuilder {

    // Shared navigation infrastructure.
    private final JPanel cardPanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();
    private final ViewManagerModel viewManagerModel = new ViewManagerModel();
    private final ViewManager viewManager =
            new ViewManager(cardPanel, cardLayout, viewManagerModel);

    // Shared factories.
    private final UserFactory userFactory = new CommonUserFactory();
    private final MealEntryFactory mealEntryFactory =
            new CommonMealEntryFactory();
    private final ExerciseEntryFactory exerciseEntryFactory =
            new CommonExerciseEntryFactory();

    // Shared data-access objects.
    private final InMemoryUserDataAccessObject userDataAccessObject =
            new InMemoryUserDataAccessObject();
    private final InMemoryMealDataAccessObject mealDataAccessObject =
            new InMemoryMealDataAccessObject();
    private final InMemoryExerciseDataAccessObject exerciseDataAccessObject =
            new InMemoryExerciseDataAccessObject();

    // Shared HTTP client for external API requests.
    private final OkHttpClient httpClient = new OkHttpClient();

    // Authentication and home components.
    private SignupView signupView;
    private SignupViewModel signupViewModel;
    private LoginView loginView;
    private LoginViewModel loginViewModel;
    private HomeView homeView;
    private LoggedInViewModel loggedInViewModel;

    //Food-Search and Meal-logging components.
    private LogMealView logMealView;
    private LogMealViewModel logMealViewModel;
    private SearchFoodViewModel searchFoodViewModel;

    // Exercise-search and exercise-logging components.
    private LogExerciseView logExerciseView;
    private LogExerciseViewModel logExerciseViewModel;
    private SearchExerciseViewModel searchExerciseViewModel;

    /**
     * Creates the application builder and configures the card panel.
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
        signupViewModel = new SignupViewModel();
        signupView = new SignupView(signupViewModel);
        cardPanel.add(signupView, signupView.getViewName());
        return this;
    }

    /**
     * Creates and registers the login view.
     *
     * @return this builder
     */
    public AppBuilder addLoginView() {
        loginViewModel = new LoginViewModel();
        loginView = new LoginView(loginViewModel);
        cardPanel.add(loginView, loginView.getViewName());
        return this;
    }

    /**
     * Creates and registers the home view.
     *
     * @return this builder
     */
    public AppBuilder addHomeView() {
        loggedInViewModel = new LoggedInViewModel();
        homeView = new HomeView(loggedInViewModel, viewManagerModel);
        cardPanel.add(homeView, homeView.getViewName());
        return this;
    }

    /**
     * Creates and registers the meal-logging view.
     *
     * @return this builder
     */
    public AppBuilder addLogMealView() {
        logMealViewModel = new LogMealViewModel();
        searchFoodViewModel = new SearchFoodViewModel();

        logMealView = new LogMealView(
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
     * Creates and registers the combined exercise-search and exercise-logging
     * view.
     *
     * @return this builder
     */
    public AppBuilder addLogExerciseView() {
        searchExerciseViewModel = new SearchExerciseViewModel();
        logExerciseViewModel = new LogExerciseViewModel();

        logExerciseView = new LogExerciseView(
                searchExerciseViewModel,
                logExerciseViewModel,
                viewManagerModel,
                loggedInViewModel
        );

        cardPanel.add(logExerciseView, logExerciseView.getViewName());
        return this;
    }

    /**
     * Wires the signup use case and attaches its controller to the signup view.
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
                new SignupController(interactor));
        return this;
    }

    /**
     * Wires the login use case and attaches its controller to the login view.
     *
     * @return this builder
     */
    public AppBuilder addLoginUseCase() {
        final LoginOutputBoundary presenter =
                new LoginPresenter(
                        viewManagerModel,
                        loggedInViewModel,
                        loginViewModel
                );

        final LoginInputBoundary interactor =
                new LoginInteractor(
                        userDataAccessObject,
                        presenter
                );

        loginView.setLoginController(
                new LoginController(interactor));
        return this;
    }

    /**
     * Wires the change-password use case and attaches its controller to the
     * home view.
     *
     * @return this builder
     */
    public AppBuilder addChangePasswordUseCase() {
        final ChangePasswordOutputBoundary presenter =
                new ChangePasswordPresenter(loggedInViewModel);

        final ChangePasswordInputBoundary interactor =
                new ChangePasswordInteractor(
                        userDataAccessObject,
                        presenter,
                        userFactory
                );

        homeView.setChangePasswordController(
                new ChangePasswordController(interactor));
        return this;
    }

    /**
     * Wires the logout use case and attaches its controller to the home view.
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
                new LogoutController(interactor));
        return this;
    }

    /**
     * Wires the meal-logging use case.
     *
     * @return this builder
     */
    public AppBuilder addLogMealUseCase() {
        final LogMealOutputBoundary presenter =
                new LogMealPresenter(logMealViewModel);

        final LogMealInputBoundary interactor =
                new LogMealInteractor(
                        mealDataAccessObject,
                        presenter,
                        mealEntryFactory
                );

        logMealView.setLogMealController(new LogMealController(interactor));
        return this;
    }

    /**
     * Wires the API-backed exercise-search use case.
     *
     * @return this builder
     * @throws IllegalStateException if {@code API_NINJAS_KEY} is missing
     */
    public AppBuilder addSearchExerciseUseCase() {
        final String apiKey =
                getRequiredEnvironmentVariable("API_NINJAS_KEY");

        final CaloriesBurnedDataAccessInterface dataAccessObject =
                new ApiNinjasCaloriesBurnedDataAccessObject(
                        httpClient,
                        apiKey
                );

        final SearchExerciseOutputBoundary presenter =
                new SearchExercisePresenter(
                        searchExerciseViewModel
                );

        final SearchExerciseInputBoundary interactor =
                new SearchExerciseInteractor(
                        dataAccessObject,
                        presenter
                );

        logExerciseView.setSearchController(
                new SearchExerciseController(interactor));
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
                new LogExerciseController(interactor));
        return this;
    }

    /**
     * Creates the main application window and selects the signup screen as the
     * initial view.
     *
     * @return the constructed application window
     */
    public JFrame build() {
        final JFrame application =
                new JFrame("ENERY Smart Nutritionist");

        application.setDefaultCloseOperation(
                WindowConstants.EXIT_ON_CLOSE);
        application.add(cardPanel);

        viewManagerModel.setState(signupView.getViewName());
        viewManagerModel.firePropertyChanged();

        return application;
    }

    /**
     * Reads a required environment variable.
     *
     * @param variableName name of the environment variable
     * @return the configured value
     * @throws IllegalStateException if the value is missing or blank
     */
    private static String getRequiredEnvironmentVariable(
            String variableName) {

        final String value = System.getenv(variableName);

        if (value == null || value.isBlank()) {
            throw new IllegalStateException(
                    variableName
                            + " environment variable is not configured.");
        }

        return value;
    }

    /**
     * Wires the reusable API-backed food-search use case.
     *
     * @return this builder
     */
    public AppBuilder addSearchFoodUseCase() {
        final String apiKey =
                getRequiredEnvironmentVariable(
                        "API_NINJAS_KEY");

        final NutritionDataAccessInterface dataAccessObject =
                new ApiNinjasNutritionDataAccessObject(
                        httpClient,
                        apiKey
                );

        final SearchFoodOutputBoundary presenter =
                new SearchFoodPresenter(
                        searchFoodViewModel
                );

        final SearchFoodInputBoundary interactor =
                new SearchFoodInteractor(
                        dataAccessObject,
                        presenter
                );

        final SearchFoodController controller =
                new SearchFoodController(interactor);

        logMealView.setSearchFoodController(controller);

        return this;
    }
}

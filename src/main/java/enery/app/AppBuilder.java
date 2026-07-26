package enery.app;

import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import enery.data_access.InMemoryMealDataAccessObject;
import enery.data_access.InMemoryUserDataAccessObject;
import enery.entity.CommonMealEntryFactory;
import enery.entity.CommonUserFactory;
import enery.entity.MealEntryFactory;
import enery.entity.UserFactory;
import enery.interface_adapter.ViewManagerModel;
import enery.interface_adapter.change_password.ChangePasswordController;
import enery.interface_adapter.change_password.ChangePasswordPresenter;
import enery.interface_adapter.change_password.LoggedInViewModel;
import enery.interface_adapter.log_meal.LogMealController;
import enery.interface_adapter.log_meal.LogMealPresenter;
import enery.interface_adapter.log_meal.LogMealViewModel;
import enery.interface_adapter.login.LoginController;
import enery.interface_adapter.login.LoginPresenter;
import enery.interface_adapter.login.LoginViewModel;
import enery.interface_adapter.logout.LogoutController;
import enery.interface_adapter.logout.LogoutPresenter;
import enery.interface_adapter.signup.SignupController;
import enery.interface_adapter.signup.SignupPresenter;
import enery.interface_adapter.signup.SignupViewModel;
import enery.use_case.change_password.ChangePasswordInputBoundary;
import enery.use_case.change_password.ChangePasswordInteractor;
import enery.use_case.change_password.ChangePasswordOutputBoundary;
import enery.use_case.log_meal.LogMealInputBoundary;
import enery.use_case.log_meal.LogMealInteractor;
import enery.use_case.log_meal.LogMealOutputBoundary;
import enery.use_case.login.LoginInputBoundary;
import enery.use_case.login.LoginInteractor;
import enery.use_case.login.LoginOutputBoundary;
import enery.use_case.logout.LogoutInputBoundary;
import enery.use_case.logout.LogoutInteractor;
import enery.use_case.logout.LogoutOutputBoundary;
import enery.use_case.signup.SignupInputBoundary;
import enery.use_case.signup.SignupInteractor;
import enery.use_case.signup.SignupOutputBoundary;
import enery.view.HomeView;
import enery.view.LogMealView;
import enery.view.LoginView;
import enery.view.SignupView;
import enery.view.ViewManager;

/**
 * Builds and wires the ENERY Smart Nutritionist application.
 *
 * <p>This class applies the Builder design pattern. It creates the application's
 * views, view models, controllers, presenters, interactors, factories, and
 * data-access objects. Keeping this construction logic here prevents
 * {@code Main} from needing to know the details of the Clean Architecture
 * wiring.</p>
 *
 * <p>Call the {@code add...View()} methods before the corresponding
 * {@code add...UseCase()} methods. A use case attaches a controller to a view,
 * so the view must already exist.</p>
 */
public final class AppBuilder {

    // Shared Swing navigation infrastructure.
    private final JPanel cardPanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();
    private final ViewManagerModel viewManagerModel = new ViewManagerModel();
    private final ViewManager viewManager =
            new ViewManager(cardPanel, cardLayout, viewManagerModel);

    // Shared factories.
    private final UserFactory userFactory = new CommonUserFactory();
    private final MealEntryFactory mealEntryFactory =
            new CommonMealEntryFactory();

    // Shared in-memory data-access objects.
    private final InMemoryUserDataAccessObject userDataAccessObject =
            new InMemoryUserDataAccessObject();
    private final InMemoryMealDataAccessObject mealDataAccessObject =
            new InMemoryMealDataAccessObject();

    // Authentication and home-screen components.
    private SignupView signupView;
    private SignupViewModel signupViewModel;
    private LoginView loginView;
    private LoginViewModel loginViewModel;
    private HomeView homeView;
    private LoggedInViewModel loggedInViewModel;

    // Meal-logging components.
    private LogMealView logMealView;
    private LogMealViewModel logMealViewModel;

    /**
     * Creates an empty application builder and configures the panel that holds
     * all application screens.
     *
     * <p>The {@link CardLayout} allows the {@link ViewManager} to switch between
     * views such as signup, login, home, and meal logging.</p>
     */
    public AppBuilder() {
        cardPanel.setLayout(cardLayout);
    }

    /**
     * Creates the signup view and its view model, then registers the view with
     * the application's {@link CardLayout}.
     *
     * @return this builder, allowing method chaining
     */
    public AppBuilder addSignupView() {
        signupViewModel = new SignupViewModel();
        signupView = new SignupView(signupViewModel);
        cardPanel.add(signupView, signupView.getViewName());
        return this;
    }

    /**
     * Creates the login view and its view model, then registers the view with
     * the application's {@link CardLayout}.
     *
     * @return this builder, allowing method chaining
     */
    public AppBuilder addLoginView() {
        loginViewModel = new LoginViewModel();
        loginView = new LoginView(loginViewModel);
        cardPanel.add(loginView, loginView.getViewName());
        return this;
    }

    /**
     * Creates the home view shown after a successful login.
     *
     * <p>The home view receives the shared {@link ViewManagerModel}, which it
     * can later use to navigate to profile, logging, tracking, and other
     * screens.</p>
     *
     * @return this builder, allowing method chaining
     */
    public AppBuilder addHomeView() {
        loggedInViewModel = new LoggedInViewModel();

        homeView = new HomeView(
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
     * Creates the meal-logging view and its view model, then registers the view
     * with the application's {@link CardLayout}.
     *
     * @return this builder, allowing method chaining
     */
    public AppBuilder addLogMealView() {
        logMealViewModel = new LogMealViewModel();
        logMealView = new LogMealView(
                logMealViewModel,
                viewManagerModel,
                loggedInViewModel
        );
        cardPanel.add(logMealView, logMealView.getViewName());
        return this;
    }

    /**
     * Wires the signup use case.
     *
     * <p>The signup presenter updates the signup and login view models. The
     * signup interactor uses the shared user DAO and user factory. Finally, the
     * controller is attached to the already-created signup view.</p>
     *
     * @return this builder, allowing method chaining
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

        final SignupController controller =
                new SignupController(interactor);

        signupView.setSignupController(controller);
        return this;
    }

    /**
     * Wires the login use case.
     *
     * <p>The login presenter changes the active view after a successful login
     * and updates both the login state and logged-in user state.</p>
     *
     * @return this builder, allowing method chaining
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

        final LoginController controller =
                new LoginController(interactor);

        loginView.setLoginController(controller);
        return this;
    }

    /**
     * Wires the change-password use case and attaches its controller to the
     * home view.
     *
     * @return this builder, allowing method chaining
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

        final ChangePasswordController controller =
                new ChangePasswordController(interactor);

        homeView.setChangePasswordController(controller);
        return this;
    }

    /**
     * Wires the logout use case and attaches its controller to the home view.
     *
     * <p>A successful logout clears the current username and returns the user
     * to the login screen.</p>
     *
     * @return this builder, allowing method chaining
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

        final LogoutController controller =
                new LogoutController(interactor);

        homeView.setLogoutController(controller);
        return this;
    }

    /**
     * Wires the log-meal use case.
     *
     * <p>The presenter converts use-case results into view-model state. The
     * interactor creates meal entries through the injected factory and stores
     * them through the meal DAO. The controller is then attached to the
     * meal-logging view.</p>
     *
     * @return this builder, allowing method chaining
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

        final LogMealController controller =
                new LogMealController(interactor);

        logMealView.setController(controller);
        return this;
    }

    /**
     * Creates the main application window and selects the first visible view.
     *
     * <p>The meal-logging view is currently selected first for development
     * testing. Before merging the finished navigation workflow, change the
     * initial state to {@code signupView.getViewName()} so the application
     * starts on the signup screen.</p>
     *
     * @return the fully constructed application window
     */
    public JFrame build() {
        final JFrame application =
                new JFrame("ENERY Smart Nutritionist");

        application.setDefaultCloseOperation(
                WindowConstants.EXIT_ON_CLOSE);
        application.add(cardPanel);

        // Temporary development start screen.
        viewManagerModel.setState(signupView.getViewName());
        viewManagerModel.firePropertyChanged();

        return application;
    }
}

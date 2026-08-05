package enery.app;

import javax.swing.JFrame;

/**
 * The Main class of our application.
 */
public class Main {
    /**
     * Builds and runs the CA architecture of the application.
     * @param args unused arguments
     */
    public static void main(String[] args) {
        final AppBuilder appBuilder = new AppBuilder();
        final JFrame application = appBuilder
                                            .addSignupView()
                                            .addLoginView()
                                            .addForgotPasswordView()
                                            .addHomeView()
                                            .addChangePasswordView()
                                            .addProfileView()
                                            .addLogMealView()
                                            .addLogExerciseView()
                                            .addProgressView()
                                            .addTrackingView()
                                            .addSignupUseCase()
                                            .addLoginUseCase()
                                            .addForgotPasswordUseCase()
                                            .addChangePasswordUseCase()
                                            .addLogoutUseCase()
                                            .addProfileUseCase()
                                            .addLogMealUseCase()
                                            .addSearchFoodUseCase()
                                            .addSearchExerciseUseCase()
                                            .addLogExerciseUseCase()
                                            .addProgressUseCase()
                                            .addTrackingUseCase()
                                            .build();

        application.pack();
        application.setVisible(true);
    }
}

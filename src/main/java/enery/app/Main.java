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
                                            .addHomeView()
                                            .addLogMealView()
                                            .addLogExerciseView()
                                            .addTrackingView()
                                            .addSignupUseCase()
                                            .addLoginUseCase()
                                            .addChangePasswordUseCase()
                                            .addLogoutUseCase()
                                            .addLogMealUseCase()
                                            .addSearchFoodUseCase()
                                            .addSearchExerciseUseCase()
                                            .addLogExerciseUseCase()
                                            .addTrackingUseCase()
                                            .build();

        application.pack();
        application.setVisible(true);
    }
}

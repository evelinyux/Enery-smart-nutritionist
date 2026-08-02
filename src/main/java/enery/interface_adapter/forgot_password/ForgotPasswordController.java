package enery.interface_adapter.forgot_password;

import enery.use_case.forgot_password.ForgotPasswordInputBoundary;
import enery.use_case.forgot_password.ForgotPasswordInputData;

/**
 * Controller for the Forgot Password Use Case.
 */
public class ForgotPasswordController {

    private final ForgotPasswordInputBoundary
            forgotPasswordUseCaseInteractor;

    public ForgotPasswordController(
            ForgotPasswordInputBoundary
                    forgotPasswordUseCaseInteractor) {

        this.forgotPasswordUseCaseInteractor =
                forgotPasswordUseCaseInteractor;
    }

    /**
     * Executes the Forgot Password Use Case.
     *
     * @param username username
     * @param newPassword new password
     * @param confirmPassword repeated new password
     */
    public void execute(
            String username,
            String newPassword,
            String confirmPassword) {

        final ForgotPasswordInputData inputData =
                new ForgotPasswordInputData(
                        username,
                        newPassword,
                        confirmPassword
                );

        forgotPasswordUseCaseInteractor.execute(
                inputData);
    }

    /**
     * Switches back to the Login View.
     */
    public void switchToLoginView() {
        forgotPasswordUseCaseInteractor
                .switchToLoginView();
    }
}
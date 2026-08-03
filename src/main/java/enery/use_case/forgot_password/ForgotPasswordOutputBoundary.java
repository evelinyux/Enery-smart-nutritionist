package enery.use_case.forgot_password;

/**
 * Output boundary for the Forgot Password Use Case.
 */
public interface ForgotPasswordOutputBoundary {

    /**
     * Prepares the success view.
     *
     * @param outputData output data
     */
    void prepareSuccessView(
            ForgotPasswordOutputData outputData);

    /**
     * Prepares the failure view.
     *
     * @param error error message
     */
    void prepareFailView(String error);

    /**
     * Switches to the Login View.
     */
    void switchToLoginView();
}
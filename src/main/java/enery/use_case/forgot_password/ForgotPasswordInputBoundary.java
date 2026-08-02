package enery.use_case.forgot_password;

/**
 * Input boundary for the Forgot Password Use Case.
 */
public interface ForgotPasswordInputBoundary {

    /**
     * Executes the Forgot Password Use Case.
     *
     * @param inputData the input data
     */
    void execute(ForgotPasswordInputData inputData);

    /**
     * Switches back to the Login View.
     */
    void switchToLoginView();
}
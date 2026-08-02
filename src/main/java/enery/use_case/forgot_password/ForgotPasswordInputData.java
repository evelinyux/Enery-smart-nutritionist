package enery.use_case.forgot_password;

/**
 * The input data for the Forgot Password Use Case.
 */
public class ForgotPasswordInputData {

    private final String username;
    private final String newPassword;
    private final String confirmPassword;

    public ForgotPasswordInputData(
            String username,
            String newPassword,
            String confirmPassword) {

        this.username = username;
        this.newPassword = newPassword;
        this.confirmPassword = confirmPassword;
    }

    public String getUsername() {
        return username;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }
}
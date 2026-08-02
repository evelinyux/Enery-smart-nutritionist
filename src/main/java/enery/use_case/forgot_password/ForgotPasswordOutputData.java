package enery.use_case.forgot_password;

/**
 * Output data for the Forgot Password Use Case.
 */
public class ForgotPasswordOutputData {

    private final String username;

    public ForgotPasswordOutputData(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
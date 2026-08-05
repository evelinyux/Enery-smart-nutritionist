package enery.use_case.change_password;

/**
 * The input data for the Change Password Use Case.
 */
public class ChangePasswordInputData {

    private final String username;
    private final String password;

    /**
     * Creates the input data for changing a password.
     *
     * @param username the username
     * @param password the new password
     */
    public ChangePasswordInputData(
            String username,
            String password) {

        this.username = username;
        this.password = password;
    }

    String getUsername() {
        return username;
    }

    String getPassword() {
        return password;
    }
}
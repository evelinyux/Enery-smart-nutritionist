package use_case.logout;

/**
 * The Input Data for the Logout Use Case.
 */
public class LogoutInputData {
    private final String username;

    public LogoutInputData(String username) {
        this.username = username;
    }

    /**
     * Returns the username of the current user.
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }
}

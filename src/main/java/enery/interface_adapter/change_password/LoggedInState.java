package enery.interface_adapter.change_password;

/**
 * State representing the currently logged-in user.
 */
public class LoggedInState {

    private String username = "";
    private String password = "";
    private String passwordError;
    private boolean profileComplete;

    /**
     * Creates an empty logged-in state.
     */
    public LoggedInState() {
        // Default constructor.
    }

    /**
     * Creates a copy of another logged-in state.
     *
     * @param copy the state to copy
     */
    public LoggedInState(LoggedInState copy) {
        this.username = copy.username;
        this.password = copy.password;
        this.passwordError = copy.passwordError;
        this.profileComplete = copy.profileComplete;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordError() {
        return passwordError;
    }

    public void setPasswordError(String passwordError) {
        this.passwordError = passwordError;
    }

    /**
     * Returns whether the logged-in user has completed
     * their profile.
     *
     * @return true if the profile exists
     */
    public boolean isProfileComplete() {
        return profileComplete;
    }

    /**
     * Sets whether the logged-in user has completed
     * their profile.
     *
     * @param profileComplete profile completion status
     */
    public void setProfileComplete(boolean profileComplete) {
        this.profileComplete = profileComplete;
    }
}

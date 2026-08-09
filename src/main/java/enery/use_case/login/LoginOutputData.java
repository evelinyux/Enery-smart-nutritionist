package enery.use_case.login;

/**
 * Output data for the Login Use Case.
 */
public class LoginOutputData {

    private final String username;
    private final boolean useCaseFailed;
    private final boolean profileComplete;

    /**
     * Original constructor retained for existing tests and callers.
     *
     * @param username username
     * @param useCaseFailed whether the login use case failed
     */
    public LoginOutputData(
            String username,
            boolean useCaseFailed) {

        this(
                username,
                useCaseFailed,
                false
        );
    }

    /**
     * Creates login output data.
     *
     * @param username username
     * @param useCaseFailed whether login failed
     * @param profileComplete whether the user has a profile
     */
    public LoginOutputData(
            String username,
            boolean useCaseFailed,
            boolean profileComplete) {

        this.username = username;
        this.useCaseFailed = useCaseFailed;
        this.profileComplete = profileComplete;
    }

    public String getUsername() {
        return username;
    }

    public boolean isUseCaseFailed() {
        return useCaseFailed;
    }

    public boolean isProfileComplete() {
        return profileComplete;
    }
}

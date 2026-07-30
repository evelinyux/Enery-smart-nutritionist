package enery.use_case.profile;

import enery.entity.user.UserProfile;

/**
 * Output Data for the Profile Use Case.
 */
public class ProfileOutputData {

    private final String username;
    private final UserProfile userProfile;
    private final boolean useCaseFailed;

    public ProfileOutputData(
            String username,
            UserProfile userProfile,
            boolean useCaseFailed) {

        this.username = username;
        this.userProfile = userProfile;
        this.useCaseFailed = useCaseFailed;
    }

    public String getUsername() {
        return username;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public boolean isUseCaseFailed() {
        return useCaseFailed;
    }
}
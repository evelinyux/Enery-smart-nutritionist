package enery.use_case.search_exercise;

import enery.entity.user.UserProfile;

/**
 * Data-access operations required by exercise search
 * to retrieve the user's profile.
 */
public interface SearchExerciseUserDataAccessInterface {

    /**
     * Gets a user's saved profile.
     *
     * @param username username
     * @return saved profile, or null if no profile exists
     */
    UserProfile getUserProfile(String username);
}

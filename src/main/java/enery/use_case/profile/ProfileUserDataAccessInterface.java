package enery.use_case.profile;

import enery.entity.user.User;

/**
 * DAO for the Profile Use Case.
 */
public interface ProfileUserDataAccessInterface {

    /**
     * Returns the username of the currently logged-in user.
     *
     * @return the current username
     */
    String getCurrentUsername();

    /**
     * Returns the user with the given username.
     *
     * @param username the username to look for
     * @return the user
     */
    User get(String username);

    /**
     * Saves the updated user.
     *
     * @param user the user to save
     */
    void save(User user);
}

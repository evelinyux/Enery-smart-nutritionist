package enery.use_case.login;

import enery.entity.user.User;

/**
 * DAO for the Login Use Case.
 */
public interface LoginUserDataAccessInterface {

    /**
     * Checks if the given username exists.
     * @param username the username to look for
     * @return true if a user with the given username exists; false otherwise
     */
    boolean existsByName(String username);

    /**
     * Saves the user.
     * @param user the user to save
     */
    void save(User user);

    /**
     * Returns the user with the given username.
     * @param username the username to look up
     * @return the user with the given username
     */
    User get(String username);

    /**
     * Sets the username of the currently logged-in user.
     * Use null if no user is logged in.
     *
     * @param username the current username
     */
    void setCurrentUsername(String username);

    /**
     * Returns the username of the currently logged-in user.
     *
     * @return the current username, or null if nobody is logged in
     */
    String getCurrentUsername();
}


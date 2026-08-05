package enery.use_case.forgot_password;

import enery.entity.user.User;

/**
 * Data access interface for the Forgot Password Use Case.
 */
public interface ForgotPasswordUserDataAccessInterface {

    /**
     * Checks whether a username exists.
     *
     * @param username username to check
     * @return whether the user exists
     */
    boolean existsByName(String username);

    /**
     * Gets the user with the given username.
     *
     * @param username username
     * @return the user
     */
    User get(String username);

    /**
     * Saves the user's updated password.
     *
     * @param user user whose password was changed
     */
    void changePassword(User user);
}
package enery.use_case.change_password;

import enery.entity.User;

/**
 * The interface of the DAO for the Change Password Use Case.
 */
public interface ChangePasswordUserDataAccessInterface {

    /**
     * Gets an existing user.
     *
     * @param username the username
     * @return the user
     */
    User get(String username);

    /**
     * Updates the system to record this user's password.
     *
     * @param user the user whose password is to be updated
     */
    void changePassword(User user);
}
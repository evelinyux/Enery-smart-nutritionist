package enery.data_access.in_memory;

import java.util.HashMap;
import java.util.Map;

import enery.entity.user.User;
import enery.entity.user.UserProfile;
import enery.use_case.change_password.ChangePasswordUserDataAccessInterface;
import enery.use_case.forgot_password.ForgotPasswordUserDataAccessInterface;
import enery.use_case.login.LoginUserDataAccessInterface;
import enery.use_case.logout.LogoutUserDataAccessInterface;
import enery.use_case.profile.ProfileUserDataAccessInterface;
import enery.use_case.search_exercise.SearchExerciseUserDataAccessInterface;
import enery.use_case.signup.SignupUserDataAccessInterface;

/**
 * In-memory implementation of the DAO for storing user data.
 *
 * <p>This implementation does not persist data between
 * program runs.</p>
 */
public class InMemoryUserDataAccessObject
        implements SignupUserDataAccessInterface,
        LoginUserDataAccessInterface,
        ChangePasswordUserDataAccessInterface,
        ForgotPasswordUserDataAccessInterface,
        LogoutUserDataAccessInterface,
        ProfileUserDataAccessInterface,
        SearchExerciseUserDataAccessInterface {

    private final Map<String, User> users =
            new HashMap<>();

    private String currentUsername;

    @Override
    public boolean existsByName(String identifier) {
        return users.containsKey(identifier);
    }

    @Override
    public void save(User user) {
        users.put(user.getName(), user);
    }

    @Override
    public User get(String username) {
        return users.get(username);
    }

    @Override
    public void changePassword(User user) {
        users.put(user.getName(), user);
    }

    @Override
    public void setCurrentUsername(String name) {
        this.currentUsername = name;
    }

    @Override
    public String getCurrentUsername() {
        return currentUsername;
    }

    @Override
    public UserProfile getUserProfile(String username) {
        final User user = get(username);

        if (user == null) {
            return null;
        }

        return user.getUserProfile();
    }
}

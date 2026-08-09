package enery.data_access;

import java.util.HashMap;
import java.util.Map;

import enery.entity.meal.MealEntry;
import enery.entity.user.User;
import enery.use_case.change_password.ChangePasswordUserDataAccessInterface;
import enery.use_case.log_meal.LogMealUserDataAccessInterface;
import enery.use_case.login.LoginUserDataAccessInterface;
import enery.use_case.forgot_password.ForgotPasswordUserDataAccessInterface;
import enery.use_case.logout.LogoutUserDataAccessInterface;
import enery.use_case.profile.ProfileUserDataAccessInterface;
import enery.use_case.recommendation.RecommendationUserDataAccessInterface;
import enery.use_case.signup.SignupUserDataAccessInterface;

/**
 * In-memory implementation of the DAO for storing user data. This implementation does
 * NOT persist data between runs of the program.
 */
public class InMemoryUserDataAccessObject implements SignupUserDataAccessInterface,
        LoginUserDataAccessInterface,
        ChangePasswordUserDataAccessInterface,
        ForgotPasswordUserDataAccessInterface,
        LogoutUserDataAccessInterface,
        ProfileUserDataAccessInterface,
        RecommendationUserDataAccessInterface,
        LogMealUserDataAccessInterface
{

    private final Map<String, User> users = new HashMap<>();

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
    public void saveMeal(String username, MealEntry mealEntry) {
        final User user = users.get(username);
        if (user != null && mealEntry != null) {
            final java.time.LocalDate logDate = mealEntry.getDate();
            user.getDailyLog(logDate).addMealEntry(mealEntry);
        }
    }

    @Override
    public java.util.List<enery.entity.meal.MealEntry> getMeals(String username, java.time.LocalDate date) {
        final User user = users.get(username);
        if (user != null) {
            return user.getDailyLog(date).getMealEntries();
        }
        return java.util.Collections.emptyList();
    }
}
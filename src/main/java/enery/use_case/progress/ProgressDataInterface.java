package enery.use_case.progress;

import enery.entity.user.UserProfile;
import enery.entity.user.WeightGoalPlan;

public interface ProgressDataInterface {

    UserProfile getUserProfile();

    double getTodaysCalories();

    double getDailyCalorieGoal();
}

package enery.use_case.recommendation;

import enery.entity.user.User;

public interface RecommendationUserDataAccessInterface {
    User get(String username);
}
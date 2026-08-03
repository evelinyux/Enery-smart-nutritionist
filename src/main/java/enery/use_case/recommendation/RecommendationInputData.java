package enery.use_case.recommendation;

public class RecommendationInputData {
    private final String username;

    public RecommendationInputData(String username) {
        this.username = username;
    }

    public String getUsername() { return username; }
}

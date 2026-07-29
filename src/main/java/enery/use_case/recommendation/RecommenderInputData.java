package enery.use_case.recommendation;

public class RecommenderInputData {
    private final String username;

    public RecommenderInputData(String username) {
        this.username = username;
    }

    public String getUsername() { return username; }
}

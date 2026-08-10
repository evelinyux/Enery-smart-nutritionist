package enery.use_case.daily_check;

public class DailyCheckInputData {

    private final String username;

    public DailyCheckInputData(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
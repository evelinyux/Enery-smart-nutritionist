package enery.use_case.tracking;

import java.time.LocalDate;

/**
 * Input data for viewing a user's daily nutrition and exercise totals.
 */
public final class TrackingInputData {

    private final String username;
    private final LocalDate date;

    public TrackingInputData(
            String username,
            LocalDate date) {

        this.username = username;
        this.date = date;
    }

    public String getUsername() {
        return username;
    }

    public LocalDate getDate() {
        return date;
    }
}

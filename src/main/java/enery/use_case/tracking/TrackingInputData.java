package enery.use_case.tracking;

import java.time.LocalDate;

/**
 * Input data for requesting a tracking summary.
 */
public final class TrackingInputData {

    private final String username;
    private final LocalDate selectedDate;
    private final TrackingPeriod period;

    public TrackingInputData(
            String username,
            LocalDate selectedDate,
            TrackingPeriod period) {

        this.username = username;
        this.selectedDate = selectedDate;
        this.period = period;
    }

    public String getUsername() {
        return username;
    }

    public LocalDate getSelectedDate() {
        return selectedDate;
    }

    public TrackingPeriod getPeriod() {
        return period;
    }
}

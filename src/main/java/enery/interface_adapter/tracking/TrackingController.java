package enery.interface_adapter.tracking;

import java.time.LocalDate;

import enery.use_case.tracking.TrackingInputBoundary;
import enery.use_case.tracking.TrackingInputData;

/**
 * Controller for requesting daily tracking information.
 */
public final class TrackingController {

    private final TrackingInputBoundary interactor;

    public TrackingController(
            TrackingInputBoundary interactor) {

        this.interactor = interactor;
    }

    /**
     * Requests the tracking summary for one user and date.
     *
     * @param username active username
     * @param date date to summarize
     */
    public void execute(
            String username,
            LocalDate date) {

        interactor.execute(
                new TrackingInputData(
                        username,
                        date
                )
        );
    }
}

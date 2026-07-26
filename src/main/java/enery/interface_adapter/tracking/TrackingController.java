package enery.interface_adapter.tracking;

import java.time.LocalDate;

import enery.use_case.tracking.TrackingInputBoundary;
import enery.use_case.tracking.TrackingInputData;
import enery.use_case.tracking.TrackingPeriod;

/**
 * Controller for daily and weekly tracking.
 */
public final class TrackingController {

    private final TrackingInputBoundary interactor;

    public TrackingController(
            TrackingInputBoundary interactor) {

        this.interactor = interactor;
    }

    public void execute(
            String username,
            LocalDate selectedDate,
            TrackingPeriod period) {

        interactor.execute(
                new TrackingInputData(
                        username,
                        selectedDate,
                        period
                )
        );
    }
}

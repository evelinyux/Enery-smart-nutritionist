package enery.use_case.tracking;

/**
 * Output boundary for daily tracking.
 */
public interface TrackingOutputBoundary {

    void prepareSuccessView(
            TrackingOutputData outputData);

    void prepareFailView(String errorMessage);
}

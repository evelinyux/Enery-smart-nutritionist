package enery.use_case.tracking;

/**
 * Output boundary for tracking summaries.
 */
public interface TrackingOutputBoundary {

    void prepareSuccessView(TrackingOutputData outputData);

    void prepareFailView(String errorMessage);
}

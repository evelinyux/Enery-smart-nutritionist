package enery.use_case.health_assessment;

import enery.use_case.tracking.TrackingOutputData;

public class HealthAssessmentInputData {
    private final String userId;

    private final TrackingOutputData trackingData;

    public HealthAssessmentInputData(String userId, TrackingOutputData trackingData) {
        this.userId = userId;
        this.trackingData = trackingData;
    }

    public String getUserId() { return userId; }
    public TrackingOutputData getTrackingData() { return trackingData; }
}
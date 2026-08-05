package enery.interface_adapter.health_assessment;

import enery.use_case.health_assessment.HealthAssessmentInputBoundary;
import enery.use_case.health_assessment.HealthAssessmentInputData;
import enery.use_case.tracking.TrackingOutputData;

public class HealthAssessmentController {

    private final HealthAssessmentInputBoundary healthAssessmentInteractor;

    public HealthAssessmentController(HealthAssessmentInputBoundary healthAssessmentInteractor) {
        this.healthAssessmentInteractor = healthAssessmentInteractor;
    }

    public void execute(String userId, TrackingOutputData trackingData) {
        HealthAssessmentInputData inputData = new HealthAssessmentInputData(userId, trackingData);
        healthAssessmentInteractor.execute(inputData);
    }
}
package enery.use_case.health_assessment;

public interface HealthAssessmentOutputBoundary {
    void prepareSuccessView(HealthAssessmentOutputData outputData);
    void prepareFailView(String error);
}
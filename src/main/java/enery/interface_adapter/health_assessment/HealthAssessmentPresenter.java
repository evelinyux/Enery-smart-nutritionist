package enery.interface_adapter.health_assessment;

import enery.use_case.health_assessment.HealthAssessmentOutputBoundary;
import enery.use_case.health_assessment.HealthAssessmentOutputData;

public class HealthAssessmentPresenter implements HealthAssessmentOutputBoundary {

    private final HealthAssessmentViewModel healthAssessmentViewModel;

    public HealthAssessmentPresenter(HealthAssessmentViewModel healthAssessmentViewModel) {
        this.healthAssessmentViewModel = healthAssessmentViewModel;
    }

    @Override
    public void prepareSuccessView(HealthAssessmentOutputData response) {
        HealthAssessmentState state = healthAssessmentViewModel.getState();

        // basic data
        state.setAssessmentId(response.assessmentId);
        state.setOverallScore(response.overallScore);
        state.setSeverity(response.severity);

        // diet data
        state.setDietScore(response.dietScore);
        state.setDietInsights(response.dietInsights);
        state.setDietActionableItems(response.dietActionableItems);

//        // exercise data
//        state.setExerciseScore(response.exerciseScore);
//        state.setExerciseInsights(response.exerciseInsights);
//        state.setExerciseActionableItems(response.exerciseActionableItems);

        state.setAssessmentError(null);

        healthAssessmentViewModel.setState(state);
        healthAssessmentViewModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String error) {
        HealthAssessmentState state = healthAssessmentViewModel.getState();

        state.setAssessmentError(error);

        healthAssessmentViewModel.setState(state);
        healthAssessmentViewModel.firePropertyChanged();
    }
}
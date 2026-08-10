package enery.use_case.daily_check;

public interface DailyCheckOutputBoundary {

    void prepareSuccessView(DailyCheckOutputData outputData);

    void prepareFailureView(String errorMessage);


}
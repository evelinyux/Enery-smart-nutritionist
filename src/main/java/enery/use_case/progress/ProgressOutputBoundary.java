package enery.use_case.progress;

public interface ProgressOutputBoundary {

    void prepareSuccessView(ProgressOutputData outputData);

    void prepareFailView(String errorMessage);
}

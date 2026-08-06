package enery.use_case.progress;

public interface ProgressOutputBoundary {

    /**
     * Prepares a Success view based on the output.
     * @param outputData Data output from the controller.
     */
    void prepareSuccessView(ProgressOutputData outputData);

    /**
     * Prepares a Fail view and provides an error message.
     * @param errorMessage a message you want to provide.
     */
    void prepareFailView(String errorMessage);
}

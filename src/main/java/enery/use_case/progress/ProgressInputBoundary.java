package enery.use_case.progress;

/**
 * Input boundary for tracking daily progress.
 */
public interface ProgressInputBoundary {

    /**
     * Obtains executes progress use case base on the input data.
     * @param inputData the data required for this use case.
     */
    void execute(ProgressInputData inputData);
}

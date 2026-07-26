package enery.interface_adapter.log_exercise;

/**
 * Stores the current result of the log-exercise use case.
 */
public final class LogExerciseState {

    private String successMessage = "";
    private String errorMessage = "";

    public String getSuccessMessage() {
        return successMessage;
    }

    public void setSuccessMessage(String successMessage) {
        this.successMessage = successMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}

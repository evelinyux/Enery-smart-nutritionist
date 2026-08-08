package enery.interface_adapter.progress;

import enery.use_case.progress.ProgressInputBoundary;
import enery.use_case.progress.ProgressInputData;

public final class ProgressController {

    private final ProgressInputBoundary progressInteractor;

    public ProgressController(ProgressInputBoundary progressInteractor) {
        this.progressInteractor = progressInteractor;
    }

    /**
     * Executes the interactor.
     * @param username username of the user logged-in.
     */
    public void execute(String username) {
        final ProgressInputData inputData = new ProgressInputData(username);

        progressInteractor.execute(inputData);
    }
}

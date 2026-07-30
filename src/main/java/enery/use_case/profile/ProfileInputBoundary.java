package enery.use_case.profile;

/**
 * Input Boundary for actions related to the user profile.
 */
public interface ProfileInputBoundary {

    /**
     * Executes the profile use case.
     *
     * @param profileInputData the input data
     */
    void execute(ProfileInputData profileInputData);

    /**
     * Executes the switch to home view use case.
     */
    void switchToHomeView();
}
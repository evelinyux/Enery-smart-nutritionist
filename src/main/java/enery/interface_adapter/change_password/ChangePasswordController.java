package enery.interface_adapter.change_password;

import enery.use_case.change_password.ChangePasswordInputBoundary;
import enery.use_case.change_password.ChangePasswordInputData;

/**
 * Controller for the Change Password Use Case.
 */
public class ChangePasswordController {

    private final ChangePasswordInputBoundary
            changePasswordUseCaseInteractor;

    /**
     * Creates the Change Password Controller.
     *
     * @param changePasswordUseCaseInteractor
     *        the change-password interactor
     */
    public ChangePasswordController(
            ChangePasswordInputBoundary
                    changePasswordUseCaseInteractor) {

        this.changePasswordUseCaseInteractor =
                changePasswordUseCaseInteractor;
    }

    /**
     * Executes the Change Password Use Case.
     *
     * @param password the new password
     * @param username the username
     */
    public void execute(
            String password,
            String username) {

        final ChangePasswordInputData inputData =
                new ChangePasswordInputData(
                        username,
                        password
                );

        changePasswordUseCaseInteractor.execute(
                inputData);
    }
}
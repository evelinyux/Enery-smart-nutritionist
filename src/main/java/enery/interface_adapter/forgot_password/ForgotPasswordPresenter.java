package enery.interface_adapter.forgot_password;

import enery.interface_adapter.shared.ViewManagerModel;
import enery.use_case.forgot_password.ForgotPasswordOutputBoundary;
import enery.use_case.forgot_password.ForgotPasswordOutputData;

/**
 * Presenter for the Forgot Password Use Case.
 */
public class ForgotPasswordPresenter
        implements ForgotPasswordOutputBoundary {

    private final ViewManagerModel viewManagerModel;
    private final ForgotPasswordViewModel forgotPasswordViewModel;

    public ForgotPasswordPresenter(
            ViewManagerModel viewManagerModel,
            ForgotPasswordViewModel forgotPasswordViewModel) {

        this.viewManagerModel = viewManagerModel;
        this.forgotPasswordViewModel = forgotPasswordViewModel;
    }

    @Override
    public void prepareSuccessView(
            ForgotPasswordOutputData outputData) {

        final ForgotPasswordState state =
                forgotPasswordViewModel.getState();

        state.setError(null);
        state.setSuccessMessage(
                "Password reset successfully.");

        state.setNewPassword("");
        state.setConfirmPassword("");

        forgotPasswordViewModel.setState(state);
        forgotPasswordViewModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String error) {

        final ForgotPasswordState state =
                forgotPasswordViewModel.getState();

        state.setError(error);
        state.setSuccessMessage(null);

        forgotPasswordViewModel.setState(state);
        forgotPasswordViewModel.firePropertyChanged();
    }

    @Override
    public void switchToLoginView() {
        viewManagerModel.setState("log in");
        viewManagerModel.firePropertyChanged();
    }
}
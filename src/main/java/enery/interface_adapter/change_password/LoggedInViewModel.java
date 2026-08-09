package enery.interface_adapter.change_password;

import enery.interface_adapter.shared.ViewModel;

/**
 * View model for the home screen displayed after login.
 */
public class LoggedInViewModel extends ViewModel<LoggedInState> {

    public static final String VIEW_NAME = "home";

    public LoggedInViewModel() {
        super(VIEW_NAME);
        setState(new LoggedInState());
    }
}

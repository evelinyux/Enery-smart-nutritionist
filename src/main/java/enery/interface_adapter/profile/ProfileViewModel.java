package enery.interface_adapter.profile;

import enery.interface_adapter.shared.ViewModel;

/**
 * View Model for the Profile View.
 */
public class ProfileViewModel
        extends ViewModel<ProfileState> {

    public static final String VIEW_NAME = "profile";

    public ProfileViewModel() {
        super(VIEW_NAME);
        setState(new ProfileState());
    }
}

package enery.interface_adapter.tracking;

import enery.interface_adapter.ViewModel;

/**
 * View model for the tracking screen.
 */
public final class TrackingViewModel
        extends ViewModel<TrackingState> {

    public static final String VIEW_NAME = "tracking";

    public TrackingViewModel() {
        super(VIEW_NAME);
        setState(new TrackingState());
    }
}

package enery.interface_adapter.daily_check;

import enery.interface_adapter.shared.ViewModel;

/**
 * View model for the Daily Check feature.
 */
public class DailyCheckViewModel extends ViewModel<DailyCheckState> {

    public static final String VIEW_NAME = "daily check";

    public DailyCheckViewModel() {
        super(VIEW_NAME);
        setState(new DailyCheckState());
    }
}

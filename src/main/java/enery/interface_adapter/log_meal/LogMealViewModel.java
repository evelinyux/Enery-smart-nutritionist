package enery.interface_adapter.log_meal;

import enery.interface_adapter.shared.ViewModel;

/**
 * View model for the log-meal screen.
 */
public final class LogMealViewModel
        extends ViewModel<LogMealState> {

    public static final String VIEW_NAME = "log meal";

    public LogMealViewModel() {
        super(VIEW_NAME);
        setState(new LogMealState());
    }
}

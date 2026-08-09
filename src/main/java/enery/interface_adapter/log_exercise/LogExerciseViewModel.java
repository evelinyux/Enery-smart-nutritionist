package enery.interface_adapter.log_exercise;

import enery.interface_adapter.shared.ViewModel;

/**
 * View model for exercise logging.
 */
public final class LogExerciseViewModel
        extends ViewModel<LogExerciseState> {

    public static final String VIEW_NAME = "log exercise";

    public LogExerciseViewModel() {
        super(VIEW_NAME);
        setState(new LogExerciseState());
    }
}

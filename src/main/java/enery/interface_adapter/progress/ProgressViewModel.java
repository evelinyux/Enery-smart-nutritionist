package enery.interface_adapter.progress;

import enery.interface_adapter.shared.ViewModel;

public class ProgressViewModel extends ViewModel<ProgressState> {
    public static final String VIEW_NAME = "progress";

    public ProgressViewModel() {
        super(VIEW_NAME);
        setState(new ProgressState());
    }
}

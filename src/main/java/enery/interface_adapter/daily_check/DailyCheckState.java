package enery.interface_adapter.daily_check;

/**
 * State for the Daily Check view.
 */
public class DailyCheckState {

    private boolean breakFastLogged;
    private boolean lunchLogged;
    private boolean dinnerLogged;
    private boolean exerciseLogged;

    private String dailyCheckError;
    private String successMessage;

    public DailyCheckState() {
    }

    public DailyCheckState(DailyCheckState copy) {
        this.breakFastLogged = copy.breakFastLogged;
        this.lunchLogged = copy.lunchLogged;
        this.dinnerLogged = copy.dinnerLogged;
        this.exerciseLogged = copy.exerciseLogged;
        this.dailyCheckError = copy.dailyCheckError;
        this.successMessage = copy.successMessage;
    }

    public boolean isBreakFastLogged() {
        return breakFastLogged;
    }

    public void setBreakFastLogged(boolean breakFastLogged) {
        this.breakFastLogged = breakFastLogged;
    }

    public boolean isLunchLogged() {
        return lunchLogged;
    }

    public void setLunchLogged(boolean lunchLogged) {
        this.lunchLogged = lunchLogged;
    }

    public boolean isDinnerLogged() {
        return dinnerLogged;
    }

    public void setDinnerLogged(boolean dinnerLogged) {
        this.dinnerLogged = dinnerLogged;
    }

    public boolean isExerciseLogged() {
        return exerciseLogged;
    }

    public void setExerciseLogged(boolean exerciseLogged) {
        this.exerciseLogged = exerciseLogged;
    }

    public String getDailyCheckError() {
        return dailyCheckError;
    }

    public void setDailyCheckError(String dailyCheckError) {
        this.dailyCheckError = dailyCheckError;
    }

    public String getSuccessMessage() {
        return successMessage;
    }

    public void setSuccessMessage(String successMessage) {
        this.successMessage = successMessage;
    }
}

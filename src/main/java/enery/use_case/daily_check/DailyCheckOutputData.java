package enery.use_case.daily_check;

public final class DailyCheckOutputData {
    private final boolean breakfastLogged;
    private final boolean lunchLogged;
    private final boolean dinnerLogged;
    private final boolean exerciseLogged;

    public DailyCheckOutputData(
            boolean breakfastLogged,
            boolean lunchLogged,
            boolean dinnerLogged,
            boolean exerciseLogged) {
        this.breakfastLogged = breakfastLogged;
        this.lunchLogged = lunchLogged;
        this.dinnerLogged = dinnerLogged;
        this.exerciseLogged = exerciseLogged;
    }
    public boolean isBreakfastLogged() {
        return breakfastLogged;
    }

    public boolean isLunchLogged() {
        return lunchLogged;
    }

    public boolean isDinnerLogged() {
        return dinnerLogged;
    }

    public boolean isExerciseLogged() {
        return exerciseLogged;
    }
}

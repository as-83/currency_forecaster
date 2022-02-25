package forecaster.domain;

public enum ForecastPeriod {
    TOMORROW(1),
    WEEK(7);
    private final int daysCount;

    ForecastPeriod(int days) {
        this.daysCount = days;
    }

    public int getDayCount() {
        return daysCount;
    }
}

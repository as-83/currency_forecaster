package forecaster.domain;

/**
 * Временные периоды
 */
public enum ForecastPeriod {

    /**
     * Завтрашний день
     */
    TOMORROW(1),

    /**
     * Неделя или семь дней
     */
    WEEK(7);
    private final int daysCount;

    ForecastPeriod(int days) {
        this.daysCount = days;
    }

    public int getDayCount() {
        return daysCount;
    }
}

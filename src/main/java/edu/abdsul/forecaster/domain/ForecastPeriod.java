package edu.abdsul.forecaster.domain;

import java.time.LocalDate;

/**
 * Сроки прогноза
 */
public enum ForecastPeriod {

    /**
     * Завтрашний день
     */
    TOMORROW(1),

    /**
     * Неделя или семь дней
     */
    WEEK(7),

    /**
     * Месяц
     */
    MONTH(LocalDate.now().lengthOfMonth()),

    /**
     * Конкретная дата
     */
    DATE(1);
    private final int daysCount;

    ForecastPeriod(int days) {
        this.daysCount = days;
    }

    public int getDayCount() {
        return daysCount;
    }
}

package edu.abdsul.forecaster.domain;

/**
 * Алгоритмы вычисления прогноза
 */
public enum Algorithm {
    /**
     * Актуальный
     */
    ACTUAL,

    /**
     * Мистический
     */
    MYSTIC,

    /**
     * Линейная регрессия
     */
    LINEAR_REGRESSION,

    /**
     * Линейная регрессия
     */
    LAST_WEEK_AVG
}

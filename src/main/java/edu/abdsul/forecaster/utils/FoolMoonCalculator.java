package edu.abdsul.forecaster.utils;


import java.time.*;
import java.time.temporal.ChronoUnit;

/**
 * Вычисляет дату полнолуния
 */
public class FoolMoonCalculator {

    public static final LocalDateTime FIRST_FOOL_MOON_21_CENTURY = LocalDateTime.of(2001, 1, 10, 0, 0, 1);
    public static final long MOON_MONTH_DURATION_MINUTES = (long)(29.53059 * 24 * 60);

    /**
     * Вычисляет дату полнолуния в месяце года
     *
     * @param date дата
     *
     * @return Дата полнолуния этого месяца
     */
    public static LocalDate getDate(LocalDate date) {
        LocalDateTime moonDate = FIRST_FOOL_MOON_21_CENTURY;
        while (moonDate.getYear() != date.getYear() ||  moonDate.getMonthValue() != date.getMonthValue()) {
            moonDate = moonDate.plusMinutes(MOON_MONTH_DURATION_MINUTES);
        }

        return moonDate.toLocalDate();
    }
}

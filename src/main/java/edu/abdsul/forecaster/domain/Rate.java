package edu.abdsul.forecaster.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Класс Rate представляет курс
 * валюты в определенную дату
 */
public class Rate {
    public static final String DATE_PATTERN = "E dd.MM.yyyy";
    private LocalDate date;
    private BigDecimal value;

    public Rate(LocalDate date, BigDecimal value) {
        this.date = date;
        this.value = value;
    }

    public LocalDate getDate() {
        return date;
    }

    public BigDecimal getValue() {
        return value;
    }

    @Override
    public String toString() {

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(DATE_PATTERN);
        return dateFormatter.format(date) +
                " " + value;
    }
}

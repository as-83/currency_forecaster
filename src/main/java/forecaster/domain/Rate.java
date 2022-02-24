package forecaster.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Класс Rate представляет курс
 * валюты в определенную дату
 */
public class Rate {
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
        String datePattern = "E dd.MM.yyyy";
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(datePattern);
        return  dateFormatter.format(date) +
                " " + value;
    }
}

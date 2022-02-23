package forecaster.domain;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Класс Rate представляет курс
 * валюты в определенную дату
 */
public class Rate {
    private LocalDate date;
    private double value;

    public Rate(LocalDate date, double value) {
        this.date = date;
        this.value = value;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        String datePattern = "E dd.MM.yyyy";
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(datePattern);
        return  dateFormatter.format(date) +
                " " + value;
    }
}

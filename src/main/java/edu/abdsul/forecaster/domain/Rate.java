package edu.abdsul.forecaster.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedHashMap;

/**
 * Класс Rate представляет курс
 * валюты в определенную дату
 */
public class Rate {
    private LocalDate startDate;
    private LocalDate finishDate;
    private CurrencyCode currencyCode;
    private int nominal;
    private String message;

    private LinkedHashMap<LocalDate, BigDecimal> rates;

    public Rate(CurrencyCode currencyCode) {
        this.currencyCode = currencyCode;
        rates = new LinkedHashMap<>();
    }

    public Rate(LocalDate startDate, LocalDate finishDate, CurrencyCode currencyCode, int nominal) {
        this.startDate = startDate;
        this.finishDate = finishDate;
        this.currencyCode = currencyCode;
        this.nominal = nominal;
        this.rates = new LinkedHashMap<>();
    }

    public CurrencyCode getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(CurrencyCode currencyCode) {
        this.currencyCode = currencyCode;
    }

    public int getNominal() {
        return nominal;
    }

    public void setNominal(int nominal) {
        this.nominal = nominal;
    }

    public LinkedHashMap<LocalDate, BigDecimal> getRates() {
        return rates;
    }

    public void addRate(LocalDate date, BigDecimal value) {
        rates.put(date, value);
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(LocalDate finishDate) {
        this.finishDate = finishDate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

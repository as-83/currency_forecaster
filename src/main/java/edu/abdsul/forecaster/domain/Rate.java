package edu.abdsul.forecaster.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Класс Rate представляет курс
 * валюты в определенную дату
 */
public class Rate {
    private CurrencyCode currencyCode;
    private int nominal;
    private LinkedHashMap<LocalDate, BigDecimal> rates;

    public Rate(CurrencyCode currencyCode) {
        this.currencyCode = currencyCode;
        rates = new LinkedHashMap<>();
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

    public void setRates(LinkedHashMap<LocalDate, BigDecimal> rates) {
        this.rates = rates;
    }

    public void addRate(LocalDate date, BigDecimal value) {
       rates.put(date, value);
    }

}

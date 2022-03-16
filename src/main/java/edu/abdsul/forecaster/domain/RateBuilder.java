package edu.abdsul.forecaster.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedHashMap;

/**
 * Класс RateBuilder создает объект
 * класса Rate
 */
public class RateBuilder {
    private Rate rate;
    private LocalDate startDate;
    private LocalDate finishDate;
    private CurrencyCode currencyCode;
    private int nominal;
    private String message;

    private LinkedHashMap<LocalDate, BigDecimal> rates;

    public RateBuilder() {
        rates = new LinkedHashMap<>();
    }


    public RateBuilder setCurrencyCode(CurrencyCode currencyCode) {
        this.currencyCode = currencyCode;
        return this;
    }

    public RateBuilder setNominal(int nominal) {
        this.nominal = nominal;
        return this;
    }

    public RateBuilder setStartDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public RateBuilder setFinishDate(LocalDate finishDate) {
        this.finishDate = finishDate;
        return this;
    }

    public Rate build() {
        return new Rate(startDate, finishDate, currencyCode, nominal);
    }
}

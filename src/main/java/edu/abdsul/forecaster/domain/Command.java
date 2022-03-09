package edu.abdsul.forecaster.domain;

import java.time.LocalDate;

/**
 * Класс Command представляет код валюты,
 * период прогноза  и корректость команды
 */
public class Command {
    private boolean correct;
    private ForecastPeriod forecastPeriod;
    private LocalDate forecastStartDate;//TODO forecastStartDate
    private CurrencyCode currencyCode;
    private Algorithm algorithm;
    private Output output;

    public ForecastPeriod getForecastPeriod() {
        return forecastPeriod;
    }
    public void setForecastPeriod(ForecastPeriod forecastPeriod) {
        this.forecastPeriod = forecastPeriod;
    }
    public CurrencyCode getCurrencyCode() {
        return currencyCode;
    }
    public void setCurrencyCode(CurrencyCode currencyCode) {
        this.currencyCode = currencyCode;
    }
    public boolean isCorrect() {
        return correct;
    }
    public void setCorrect(boolean correct) {
        this.correct = correct;
    }
    public Algorithm getAlgorithm() {
        return algorithm;
    }
    public void setAlgorithm(Algorithm algorithm) {
        this.algorithm = algorithm;
    }
    public Output getOutput() {
        return output;
    }
    public void setOutput(Output output) {
        this.output = output;
    }
    public LocalDate getForecastStartDate() {
        return forecastStartDate;
    }
    public void setForecastStartDate(LocalDate forecastStartDate) {
        this.forecastStartDate = forecastStartDate;
    }
}

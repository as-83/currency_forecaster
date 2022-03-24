package edu.abdsul.forecaster.domain;

import java.time.LocalDate;

/**
 * Класс Command представляет код валюты, период прогноза,
 * алгоритм прогнозирования, форму вывода результата  и корректость команды
 */
public class Command {

    private boolean correct;
    private ForecastPeriod forecastPeriod;
    private LocalDate forecastStartDate;
    private CurrencyCode currencyCode;
    private Algorithm algorithm;
    private OutputType outputType;

    public Command() {
    }

    public Command(boolean correct, ForecastPeriod forecastPeriod,
                   LocalDate forecastStartDate, CurrencyCode currencyCode,
                   Algorithm algorithm, OutputType outputType) {
        this.correct = correct;
        this.forecastPeriod = forecastPeriod;
        this.forecastStartDate = forecastStartDate;
        this.currencyCode = currencyCode;
        this.algorithm = algorithm;
        this.outputType = outputType;
    }

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

    public OutputType getOutput() {
        return outputType;
    }

    public void setOutput(OutputType outputType) {
        this.outputType = outputType;
    }

    public LocalDate getForecastStartDate() {
        return forecastStartDate;
    }

    public void setForecastStartDate(LocalDate forecastStartDate) {
        this.forecastStartDate = forecastStartDate;
    }
}

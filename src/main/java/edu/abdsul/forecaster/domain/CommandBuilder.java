package edu.abdsul.forecaster.domain;

import java.time.LocalDate;

/**
 * Класс Command представляет код валюты, период прогноза,
 * алгоритм прогнозирования, форму вывода результата  и корректость команды
 */
public class CommandBuilder {

    private boolean correct;
    private ForecastPeriod forecastPeriod;
    private LocalDate forecastStartDate;
    private CurrencyCode currencyCode;
    private Algorithm algorithm;
    private OutputType outputType;

    public CommandBuilder setForecastPeriod(ForecastPeriod forecastPeriod) {
        this.forecastPeriod = forecastPeriod;
        return this;
    }

    public CommandBuilder setCurrencyCode(CurrencyCode currencyCode) {
        this.currencyCode = currencyCode;
        return this;
    }

    public CommandBuilder setCorrect(boolean correct) {
        this.correct = correct;
        return this;
    }

    public CommandBuilder setAlgorithm(Algorithm algorithm) {
        this.algorithm = algorithm;
        return this;
    }

    public CommandBuilder setOutput(OutputType outputType) {
        this.outputType = outputType;
        return this;
    }

    public CommandBuilder setForecastStartDate(LocalDate forecastStartDate) {
        this.forecastStartDate = forecastStartDate;
        return this;
    }

    public boolean isCorrect() {
        return correct;
    }

    public ForecastPeriod getForecastPeriod() {
        return forecastPeriod;
    }

    public LocalDate getForecastStartDate() {
        return forecastStartDate;
    }

    public CurrencyCode getCurrencyCode() {
        return currencyCode;
    }

    public Algorithm getAlgorithm() {
        return algorithm;
    }

    public OutputType getOutputType() {
        return outputType;
    }

    public Command build() {
        return new Command(correct, forecastPeriod, forecastStartDate,
                currencyCode, algorithm, outputType);
    }
}

package edu.abdsul.forecaster;

import edu.abdsul.forecaster.algorithm.ForecastType;
import edu.abdsul.forecaster.algorithm.LastSevenAvgForecast;
import edu.abdsul.forecaster.domain.CurrencyCode;
import edu.abdsul.forecaster.domain.Rate;

/**
 * Класс Forecaster выполняет прогноз курса валюты
 * на заданный период в днях, по заданному алгоритму
 * прогнозирования  основываясь на исторических данных курса валюты
 * Алгоритм по умолчанию: среднее значение последних семи дней
 * Источник данных по умолчанию - csv-файл
 */
public class Forecaster {
    private ForecastType forecastType = new LastSevenAvgForecast();

    /**
     * Прогноз курса выбранной валюты, на заданный период
     * основыванный на исторических данных курса валюты
     *
     * @param currencyCode     код валюты
     * @param forecastDuration срок прогноза в днях начиная с завтрашнего дня
     * @return Прогноз курса валюты на заданное количество дней
     */
    public Rate getForecast(CurrencyCode currencyCode, int forecastDuration) {
        return forecastType.getForecast(currencyCode, forecastDuration);
    }

    public ForecastType getForecastType() {
        return forecastType;
    }

    public void setForecastType(ForecastType forecastType) {
        this.forecastType = forecastType;
    }
}

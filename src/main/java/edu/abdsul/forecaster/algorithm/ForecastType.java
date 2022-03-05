package edu.abdsul.forecaster.algorithm;

import edu.abdsul.forecaster.domain.CurrencyCode;
import edu.abdsul.forecaster.domain.Rate;
import edu.abdsul.forecaster.source.DataSource;

import java.util.List;

/**
 * Получение прогноза курса валюты на заданное
 * количество дней
 */
public interface ForecastType {
    /**
     * Прогноз курса валюты основыванный на исторических данных
     *
     * @param currencyCode          код валюты
     * @param forecastDuration длительность прогноза в днях
     * @return список прогнозируемых значений курса валюты на заданное количество дней
     */
    Rate getForecast(CurrencyCode currencyCode, int forecastDuration);

    void setDataSource(DataSource dataSource);
}

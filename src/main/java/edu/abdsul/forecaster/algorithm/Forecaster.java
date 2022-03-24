package edu.abdsul.forecaster.algorithm;

import edu.abdsul.forecaster.domain.Command;
import edu.abdsul.forecaster.domain.Rate;
import edu.abdsul.forecaster.source.DataSource;

/**
 * Получение прогноза курса валюты на заданное
 * количество дней
 */
public interface Forecaster {
    /**
     * Прогноз курса валюты основыванный на исторических данных
     *
     * @param command объект содержащий команды запроса
     * @return прогнозируемые значения курса валюты на заданное количество дней
     */
    Rate getForecast(Command command);

    void setDataSource(DataSource dataSource);
}

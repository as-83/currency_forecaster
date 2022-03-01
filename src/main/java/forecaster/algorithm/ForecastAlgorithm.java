package forecaster.algorithm;

import forecaster.domain.Rate;

import java.util.List;

/**
 * Получение прогноза курса валюты на заданное
 * количество дней
 */
public interface ForecastAlgorithm {
    /**
     * Прогноз курса валюты основыванный на исторических данных
     *
     * @param rates            список исторических данных курса валюты
     * @param forecastDuration длительность прогноза в днях
     * @return список прогнозируемых значений курса валюты на заданное количество дней
     */
    List<Rate> getForecast(List<Rate> rates, int forecastDuration);
}

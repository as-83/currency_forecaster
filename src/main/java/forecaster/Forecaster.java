package forecaster;

import forecaster.algorithms.ForecastAlgorithm;
import forecaster.algorithms.LastSevenAvgForecast;
import forecaster.domain.Rate;
import forecaster.sources.DataSource;
import forecaster.sources.FileDataSource;

import java.util.List;

/**
 * Класс Forecaster выполняет прогноз курса валюты
 * на заданный период в днях, по заданному алгоритму
 * прогнозирования  основываясь на исторических данных курса валюты
 * Алгоритм по умолчанию: среднее значение последних семи дней
 * Источник данных по умолчанию - csv-файл
 */
public class Forecaster {

    private DataSource dataSource = new FileDataSource();
    private ForecastAlgorithm forecastType = new LastSevenAvgForecast();

    /**
     * Прогноз курса выбранной валюты, на заданный период
     * основыванный на исторических данных курса валюты
     *
     * @param currencyCode     код валюты
     * @param forecastDuration срок прогноза в днях начиная с завтрашнего дня
     * @return Прогноз курса валюты на заданное количество дней
     */
    public List<Rate> getForecast(String currencyCode, int forecastDuration) {
        List<Rate> ratesFromSrc = dataSource.getRates(currencyCode);
        return forecastType.getForecast(ratesFromSrc, forecastDuration);
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void setForecastType(ForecastAlgorithm forecast) {
        this.forecastType = forecast;
    }

}

package forecaster;

import forecaster.algorithms.ForecastType;
import forecaster.algorithms.LastSevenAvgForecast;
import forecaster.domain.Rate;
import forecaster.sources.DataSource;
import forecaster.sources.FileDataSource;

import java.io.IOException;
import java.util.List;

/**
 * Класс Forecaster выполняет прогноз курса валюты
 * <p>
 * основываясь на данных, полученных классом,
 * реализующим  interface DataSource,
 * <p>
 * Прогноз осуществляется по алгоритму, заданному в классе,
 * реализующем  interface ForecastType,
 */
public class Forecaster {
    private DataSource dataSource = new FileDataSource();
    private ForecastType forecastType = new LastSevenAvgForecast();

    /**
     * Прогноз курса выбранной валюты,
     * основыванный на исторических данных курса валюты
     * <p>
     * Источник данных- класс, реализующий интерфейс
     * forecaster.sources.DataSource
     * Источник данных по умолчанию - файлы формата CSV с разделителем  ';'
     *
     *
     * Алгоритм прогнозирования задается классом,
     * реализующим  interface forecaster.algorithms.ForecastType.
     * Алгоритм по умолчанию:  Среднее арифметическое
     * значение на основании 7 последних значений
     *
     * @param currencyCode     код валюты
     * @param forecastDuration срок прогноза
     * @return Прогноз курса валюты на заданное количество дней
     * @throws IOException Если организация не была найдена
     */
    public List<Rate> getForecast(String currencyCode, int forecastDuration) throws IOException {
        List<Rate> ratesFromSrc = dataSource.getRates(currencyCode);
        return forecastType.getForecast(ratesFromSrc, forecastDuration);
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void setForecastType(ForecastType forecast) {
        this.forecastType = forecast;
    }
}

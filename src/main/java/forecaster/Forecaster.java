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
     *
     * @param currencyCode     код валюты
     * @param forecastDuration срок прогноза
     * @return Прогноз курса валюты на заданное количество дней
     */
    public List<Rate> getForecast(String currencyCode, int forecastDuration){
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

package forecaster;

import forecaster.algorithms.LastSevenAvgForecast;
import forecaster.algorithms.ForecastType;
import forecaster.domain.Rate;
import forecaster.sources.DataSource;
import forecaster.sources.FileDataSource;

import java.io.IOException;
import java.util.Collections;
import java.util.List;


public class Forecaster {
    private DataSource dataSource = new FileDataSource();
    private ForecastType forecast  = new LastSevenAvgForecast();

    public List<Rate> getForecast(String currencyCode, int forecastDuration){
        List<Rate> forecastsList = Collections.emptyList();
        try {
            List<Rate>  ratesFromSrc = dataSource.getRates(currencyCode);
            forecastsList = forecast.getForecast(ratesFromSrc, forecastDuration);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return forecastsList;

    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void setForecastType(ForecastType forecast) {
        this.forecast = forecast;
    }
}

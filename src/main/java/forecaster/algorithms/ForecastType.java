package forecaster.algorithms;

import forecaster.domain.Rate;

import java.util.List;

public interface ForecastType {
    List<Rate> getForecast(List<Rate> rates, int forecastDuration);
}

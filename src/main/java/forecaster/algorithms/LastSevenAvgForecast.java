package forecaster.algorithms;

import forecaster.domain.Rate;

import java.time.LocalDate;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LastSevenAvgForecast implements ForecastType {
    private static final long DAYS_LIMIT = 7;

    @Override
    public List<Rate> getForecast(List<Rate> rates, int forecastDuration) {
        ArrayDeque<Rate> lastSevenRates = rates.stream().limit(DAYS_LIMIT)
                .collect(Collectors.toCollection(ArrayDeque::new));
        List<Rate> forecasts = new ArrayList<>();
        for (int i = 1; i < forecastDuration + 1; i++) {
            double avg = lastSevenRates.stream().mapToDouble(r -> r.getValue())
                    .average().orElse(0);
            Rate forecastRate = new Rate(LocalDate.now().plusDays(i), avg);
            lastSevenRates.removeLast();
            lastSevenRates.addFirst(forecastRate);
            forecasts.add(forecastRate);
        }

        return forecasts;
    }
}

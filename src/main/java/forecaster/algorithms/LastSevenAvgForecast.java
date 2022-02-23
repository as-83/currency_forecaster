package forecaster.algorithms;

import forecaster.domain.Rate;

import java.time.Duration;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Класс LastSevenAvgForecast осуществляет прогноз курса валюты,
 * основываясь на исторических данных
 * <p>
 * Алгоритм прогнозирования: Среднее арифметическое
 * значение на основании 7 последних значений
 */
public class LastSevenAvgForecast implements ForecastType {
    private static final long DAYS_LIMIT = 7;

    /**
     * Прогноз курса валюты основыванный на исторических данных
     * <p>
     * Алгоритм прогнозирования: Среднее арифметическое
     * значение на основании 7 последних значений
     *
     * @param rates            код валюты
     * @param forecastDuration исторических данные курса валюты
     * @return Прогноз курса валюты на заданное количество дней
     */
    @Override
    public List<Rate> getForecast(List<Rate> rates, int forecastDuration) {

        List<Rate> forecasts = new ArrayList<>();

        if (rates.size() > 0) {
            LocalDate lastDateInHistory = rates.get(0).getDate();
            int absentDaysCount = Period.between(lastDateInHistory, LocalDate.now()).getDays();

            forecastDuration += absentDaysCount;
            ArrayDeque<Rate> lastSevenRates = rates.stream().limit(DAYS_LIMIT)
                    .collect(Collectors.toCollection(ArrayDeque::new));

            for (int i = 1; i <= forecastDuration ; i++) {
                double avgValue = lastSevenRates.stream().mapToDouble(Rate::getValue)
                        .average().orElse(0);

                LocalDate nextDate = lastDateInHistory.plusDays(i);
                Rate forecastRate = new Rate(nextDate, avgValue);

                lastSevenRates.removeLast();
                lastSevenRates.addFirst(forecastRate);

                if (i > absentDaysCount) {
                    forecasts.add(forecastRate);
                }
            }
        }
        return forecasts;
    }
}

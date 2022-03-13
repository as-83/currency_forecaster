package edu.abdsul.forecaster.algorithm;

import edu.abdsul.forecaster.domain.Command;
import edu.abdsul.forecaster.domain.Rate;
import edu.abdsul.forecaster.source.DataSource;
import edu.abdsul.forecaster.source.FileDataSource;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayDeque;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Класс LastSevenAvgForecaster осуществляет вычисление прогнозируемого курса валюты,
 * основываясь на исторических данных
 * <p>
 * Алгоритм вычисления: Среднее арифметическое
 * значение на основании 7 последних значений
 */
public class LastSevenAvgForecaster implements Forecaster {
    private static final long DAYS_LIMIT = 7;
    private DataSource dataSource = new FileDataSource();

    /**
     * Вычисление прогнозируемого курса валюты на заданный в днях срок
     * <p>
     * Алгоритм вычисления: Среднее арифметическо 7 последних значений
     *
     * @param command объект содержащий команды: длительность прогноза в днях, код валюты
     * @return список прогнозируемых значений курса валюты на заданное количество дней
     */
    @Override
    public Rate getForecast(Command command) {

        Rate forecasts = new Rate(command.getCurrencyCode());

        Rate rateHistory = dataSource.getAllRates(command.getCurrencyCode());

        if (rateHistory.getRates().isEmpty()) {
            return rateHistory;
        }

        LocalDate lastDateInHistory = (LocalDate) rateHistory.getRates().keySet().toArray()[0];
        int absentDaysCount = Period.between(lastDateInHistory, command.getForecastStartDate()).getDays() - 1;

        int forecastDuration = absentDaysCount + command.getForecastPeriod().getDayCount();
        ArrayDeque<BigDecimal> lastSevenRates = rateHistory.getRates().entrySet().stream()
                .limit(DAYS_LIMIT)
                .map(Map.Entry::getValue)
                .collect(Collectors.toCollection(ArrayDeque::new));

        for (int i = 1; i <= forecastDuration; i++) {
            BigDecimal avgValue = lastSevenRates.stream()
                    .reduce(BigDecimal.ZERO, BigDecimal::add).divide(BigDecimal.valueOf(DAYS_LIMIT), RoundingMode.CEILING);

            LocalDate nextDate = lastDateInHistory.plusDays(i);

            lastSevenRates.removeLast();
            lastSevenRates.addFirst(avgValue);

            if (i > absentDaysCount) {
                forecasts.addRate(nextDate, avgValue);
            }
        }
        forecasts.setStartDate(command.getForecastStartDate());
        forecasts.setFinishDate(command.getForecastStartDate().plusDays(command.getForecastPeriod().getDayCount() - 1));
        return forecasts;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}

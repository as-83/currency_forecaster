package edu.abdsul.forecaster.algorithm;

import edu.abdsul.forecaster.domain.Command;
import edu.abdsul.forecaster.domain.Rate;
import edu.abdsul.forecaster.source.DataSource;
import edu.abdsul.forecaster.source.FileDataSource;
import edu.abdsul.forecaster.utils.LinearRegression;
import edu.abdsul.forecaster.utils.RateHistoryHelper;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.stream.DoubleStream;

/**
 * Класс LinearRegressionForecaster осуществляет вычисление прогнозируемого курса валюты,
 * основываясь на исторических данных
 * <p>
 * Алгоритм вычисления: Линейная регоессия
 */
public class LinearRegressionForecaster implements Forecaster {

    private DataSource dataSource = new FileDataSource();

    /**
     * Вычисление прогнозируемого курса валюты на заданный в днях срок
     * <p>
     * Алгоритм вычисления: Линейная регрессия
     *
     * @param command объект содержащий команды: длительность прогноза в днях, код валюты
     * @return список прогнозируемых значений курса валюты на заданное количество дней
     */
    @Override
    public Rate getForecast(Command command) {

        Rate forecasts = new Rate(command.getCurrencyCode());

        Rate rateHistory = dataSource.getLastNRates(command.getCurrencyCode(), 30);
        boolean isPastDate = command.getForecastStartDate().isBefore(LocalDate.now().plusDays(1));

        if (rateHistory.getRates().isEmpty() || isPastDate) {
            return forecasts;
        }

        LocalDate lastRateDate = rateHistory.getRates().keySet().stream().findFirst().get();
        int absentDays = (int)ChronoUnit.DAYS.between(lastRateDate, command.getForecastStartDate());

        double[] lastMonthRates = RateHistoryHelper.format(rateHistory);
        double[] xAxis = DoubleStream.iterate(1.0, x -> x + 1).limit(30).toArray();

        LinearRegression linearRegression = new LinearRegression(xAxis, lastMonthRates);

        forecasts.setNominal(rateHistory.getNominal());
        forecasts.setCurrencyCode(rateHistory.getCurrencyCode());
        forecasts.setStartDate(command.getForecastStartDate());
        forecasts.setFinishDate(command.getForecastStartDate().plusDays(command.getForecastPeriod().getDayCount() - 1));

        for (int i = 0; i < command.getForecastPeriod().getDayCount(); i++) {
            LocalDate date = command.getForecastStartDate().plusDays(i);
            double value = linearRegression.predict(30 + i + 1 + absentDays);
            forecasts.addRate(date, new BigDecimal(value));
        }
        return forecasts;
    }



    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}

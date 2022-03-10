package edu.abdsul.forecaster.algorithm;

import edu.abdsul.forecaster.domain.Command;
import edu.abdsul.forecaster.domain.Rate;
import edu.abdsul.forecaster.source.DataSource;
import edu.abdsul.forecaster.source.FileDataSource;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Класс LastSevenAvgForecaster осуществляет вычисление прогнозируемого курса валюты,
 * основываясь на исторических данных
 * <p>
 * Алгоритм вычисления: Среднее арифметическое
 * значение на основании 7 последних значений
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
        boolean isForecastable = !command.getForecastStartDate().isAfter(LocalDate.now().plusYears(2));
        if(!isForecastable) {//TODO error field in Rate and write error to it
            return forecasts;
        }

        if (rateHistory.getRates().isEmpty()) {
            return rateHistory;
        }



        return forecasts;
    }



    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}

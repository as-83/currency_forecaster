package edu.abdsul.forecaster.algorithm;

import edu.abdsul.forecaster.domain.Command;
import edu.abdsul.forecaster.domain.Rate;
import edu.abdsul.forecaster.source.DataSource;
import edu.abdsul.forecaster.source.FileDataSource;
import edu.abdsul.forecaster.utils.RateHistoryHelper;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Класс LastSevenAvgForecaster осуществляет вычисление прогнозируемого курса валюты,
 * основываясь на исторических данных
 * <p>
 * Алгоритм вычисления: Среднее арифметическое
 * значение на основании 7 последних значений
 */
public class ActualAlgorithmForecaster implements Forecaster {

    private DataSource dataSource = new FileDataSource();

    /**
     * Вычисление прогнозируемого курса валюты на заданный в днях срок
     * <p>
     * Алгоритм вычисления: “Актуальный”. Рассчитывается, как сумма курса за
     * (текущий год - 2 + текущий год - 3), то есть прогноз на 25.12.2022 будет
     * считаться как прогноз 25.12.2020 + 25.12.2019. Если число сильно впереди
     * и нет данных за год -  ошибка.
     *
     * @param command объект содержащий команды: длительность прогноза в днях, код валюты
     * @return список прогнозируемых значений курса валюты на заданное количество дней
     */
    @Override
    public Rate getForecast(Command command) {

        Rate forecasts = new Rate(command.getCurrencyCode());

        Rate rateHistory = dataSource.getAllRates(command.getCurrencyCode());
        boolean isForecastable = !command.getForecastStartDate().isAfter(LocalDate.now().plusYears(2));
        if(!isForecastable) {//TODO error field in Rate and write error to it
            return forecasts;
        }

        if (rateHistory.getRates().isEmpty()) {
            return rateHistory;
        }


        BigDecimal twoYearsAgo = RateHistoryHelper.getRateFromPast(rateHistory, command.getForecastStartDate().minusYears(2));
        BigDecimal threeYearsAgo = RateHistoryHelper.getRateFromPast(rateHistory, command.getForecastStartDate().minusYears(3));
        BigDecimal expectedRate = twoYearsAgo.add(threeYearsAgo);
        forecasts.addRate(command.getForecastStartDate(), expectedRate);
        forecasts.setNominal(rateHistory.getNominal());
        return forecasts;
    }


    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}

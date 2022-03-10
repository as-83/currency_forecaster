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
public class MysticAlgorithmForecaster implements Forecaster {

    private DataSource dataSource = new FileDataSource();

    /**
     * Вычисление прогнозируемого курса валюты на заданный в днях срок
     * <p>
     * Алгоритм “Мистический”
     * Для расчета на дату используем среднее арифметическое из трех последних от этой даты полнолуний.
     * Для расчета на неделю и месяц. Первый курс рассчитывается аналогично предыдущему пункту.
     * Последующие даты рассчитываются рекуррентно по формуле - значение предыдущей даты  + случайное
     * число от -10% до +10% от значения предыдущей даты.
     *
     * @param command объект содержащий команды: длительность прогноза в днях, код валюты
     * @return список прогнозируемых значений курса валюты на заданное количество дней
     */
    @Override
    public Rate getForecast(Command command) {

        Rate forecast = new Rate(command.getCurrencyCode());

        Rate rateHistory = dataSource.getAllRates(command.getCurrencyCode());
        boolean isForecastable = command.getForecastStartDate().isAfter(LocalDate.now().plusMonths(1));
        if(!isForecastable) {//TODO error field in Rate and write error to it
            return forecast;
        }

        if (rateHistory.getRates().isEmpty()) {
            return rateHistory;
        }


        BigDecimal twoYearsAgo = getRateFromPast(rateHistory, command.getForecastStartDate().minusMonths(2));
        BigDecimal threeYearsAgo = getRateFromPast(rateHistory, command.getForecastStartDate().minusMonths(2));
        BigDecimal expectedRate = twoYearsAgo.add(threeYearsAgo);
        forecast.addRate(command.getForecastStartDate(), expectedRate);
        forecast.setNominal(rateHistory.getNominal());
        return forecast;
    }

    private BigDecimal getRateFromPast(Rate rateHistory, LocalDate pastDate) {
        LocalDate nearestDate = pastDate;
        BigDecimal rateInPast = rateHistory.getRates().get(pastDate);
        while (rateInPast == null) {
            nearestDate = nearestDate.minusDays(1);
            rateInPast = rateHistory.getRates().get(nearestDate);
        }
        return rateInPast;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}

package edu.abdsul.forecaster.algorithm;

import edu.abdsul.forecaster.domain.Command;
import edu.abdsul.forecaster.domain.Rate;
import edu.abdsul.forecaster.source.DataSource;
import edu.abdsul.forecaster.source.FileDataSource;
import edu.abdsul.forecaster.utils.FoolMoonCalculator;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Класс MysticAlgorithmForecaster осуществляет вычисление прогнозируемого курса валюты,
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
     * Для расчета на дату используется среднее арифметическое из трех последних от этой даты полнолуний.
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
        if (!isForecastable) {//TODO error field in Rate and write error to it
            return forecast;
        }

        if (rateHistory.getRates().isEmpty()) {
            return rateHistory;
        }

        forecast.setNominal(rateHistory.getNominal());
        //Вычисляем даты трех последних полнолуний
        List<LocalDate> moonDates = get3LastFoolMoons(command.getForecastStartDate());

        //Получаем сумму курсов валют в эти дни
        BigDecimal rateSum = new BigDecimal(0);
        for (LocalDate date : moonDates) {
            BigDecimal rate = getRateFromPast(rateHistory, date);
            rateSum = rateSum.add(rate);
        }

        //Вычисляем среднее
        BigDecimal avgRate = rateSum.divide(BigDecimal.valueOf(3));
        forecast.addRate(command.getForecastStartDate(), avgRate);

        //Если прогноз больше чем на день, то Последующие даты рассчитываются
        // рекуррентно по формуле - значение предыдущей даты  + случайное
        // число от -10% до +10% от значения предыдущей даты

        for (int i = 1; i < command.getForecastPeriod().getDayCount(); i++) {
            double rand = 0.9 + new Random().nextDouble() * 0.2;
            BigDecimal rateValue = avgRate.multiply(BigDecimal.valueOf(rand));
            forecast.addRate(command.getForecastStartDate().minusDays(i), rateValue);
        }

        return forecast;
    }


    private List<LocalDate> get3LastFoolMoons(LocalDate forecastStartDate) {
        List<LocalDate> moonDates = new ArrayList<>();

        while (moonDates.size() < 3) {
            LocalDate foolMoonDate = FoolMoonCalculator.getDate(forecastStartDate);
            if (foolMoonDate.isBefore(forecastStartDate)) {
                moonDates.add(foolMoonDate);
            }
        }

        return moonDates;
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

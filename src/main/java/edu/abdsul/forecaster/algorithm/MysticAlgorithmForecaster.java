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

    public static final int ALGORITHM_LIMIT = 1;
    private DataSource dataSource = new FileDataSource();
    private Rate forecast;

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
        forecast = new Rate(command.getCurrencyCode());

        Rate rateHistory = dataSource.getAllRates(command.getCurrencyCode());

        if (!isDateInScope(command.getForecastStartDate()) || rateHistory.getRates().isEmpty()) {
            return forecast;
        }

        forecast.setNominal(rateHistory.getNominal());
        forecast.setStartDate(command.getForecastStartDate());
        forecast.setFinishDate(command.getForecastStartDate().plusDays(command.getForecastPeriod().getDayCount() - 1));

        List<LocalDate> moonDates = get3LastFoolMoons(command.getForecastStartDate());

        //Получаем средний курс трех последних полнолуний
        BigDecimal avgRate = getAvgRate(rateHistory, moonDates);

        forecast.addRate(command.getForecastStartDate(), avgRate);

        generateNextRates(command, avgRate);

        return forecast;
    }

    private void generateNextRates(Command command, BigDecimal avgRate) {
        for (int i = 1; i < command.getForecastPeriod().getDayCount(); i++) {
            double rand = 0.9 + new Random().nextDouble() * 0.2;
            BigDecimal rateValue = avgRate.multiply(BigDecimal.valueOf(rand));
            forecast.addRate(command.getForecastStartDate().minusDays(i), rateValue);
        }
    }

    private BigDecimal getAvgRate(Rate rateHistory, List<LocalDate> moonDates) {
        BigDecimal rateSum = new BigDecimal(0);
        for (LocalDate date : moonDates) {
            BigDecimal rate = getRateFromPast(rateHistory, date);
            rateSum = rateSum.add(rate);
        }
        
        return rateSum.divide(new BigDecimal(3), BigDecimal.ROUND_UP);
    }


    private List<LocalDate> get3LastFoolMoons(LocalDate forecastStartDate) {
        List<LocalDate> moonDates = new ArrayList<>();
        int i = 0;
        while (moonDates.size() < 3) {
            LocalDate foolMoonDate = FoolMoonCalculator.getDate(forecastStartDate.minusMonths(i++));
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

    private boolean isDateInScope(LocalDate date) {
        return date.isBefore(LocalDate.now().plusMonths(ALGORITHM_LIMIT))
                && date.isAfter(LocalDate.now());
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}

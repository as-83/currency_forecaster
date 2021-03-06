package edu.abdsul.forecaster.algorithm;

import edu.abdsul.forecaster.domain.Command;
import edu.abdsul.forecaster.domain.Rate;
import edu.abdsul.forecaster.source.DataSource;
import edu.abdsul.forecaster.source.FileDataSource;
import edu.abdsul.forecaster.utils.RateHistoryHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Класс ActualAlgorithmForecaster осуществляет вычисление прогнозируемого курса валюты,
 * основываясь на исторических данных
 * <p>
 * Алгоритм вычисления: “Актуальный”
 */
public class ActualAlgorithmForecaster implements Forecaster {

    private static final Logger logger = LoggerFactory.getLogger(ActualAlgorithmForecaster.class);
    public static final int ALGORITHM_SCOPE = 2;
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

        if(!isDateInScope(command.getForecastStartDate()) || rateHistory.getRates().isEmpty()) {
            return forecasts;
        }

        forecasts.setNominal(rateHistory.getNominal());
        forecasts.setStartDate(command.getForecastStartDate());
        forecasts.setFinishDate(command.getForecastStartDate().plusDays(command.getForecastPeriod().getDayCount() - 1));

        for (int i = 0; i < command.getForecastPeriod().getDayCount(); i++) {
            BigDecimal twoYearsAgo = RateHistoryHelper.getRateFromPast(rateHistory, command.getForecastStartDate().minusYears(2).plusDays(i));
            BigDecimal threeYearsAgo = RateHistoryHelper.getRateFromPast(rateHistory, command.getForecastStartDate().minusYears(3).plusDays(i));
            BigDecimal expectedRate = twoYearsAgo.add(threeYearsAgo);
            forecasts.addRate(command.getForecastStartDate().plusDays(i), expectedRate);
        }
        return forecasts;
    }


    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private boolean isDateInScope(LocalDate date) {
        boolean isInScope = date.isBefore(LocalDate.now().plusYears(ALGORITHM_SCOPE))
                && date.isAfter(LocalDate.now());
        logger.debug(date + " is  in scope of this algorithm - " + isInScope);
        return isInScope;
    }
}

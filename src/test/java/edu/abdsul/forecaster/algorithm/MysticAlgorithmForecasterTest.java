package edu.abdsul.forecaster.algorithm;

import edu.abdsul.forecaster.domain.Command;
import edu.abdsul.forecaster.domain.CurrencyCode;
import edu.abdsul.forecaster.domain.ForecastPeriod;
import edu.abdsul.forecaster.domain.Rate;
import org.assertj.core.data.Percentage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Java6Assertions.assertThat;

class MysticAlgorithmForecasterTest {

    public static final double RATE_RANGE = 10.0;
    private static Forecaster forecaster;
    private static Command command;

    @BeforeAll
    static void init() {
        forecaster = new MysticAlgorithmForecaster();
        command = new Command();
    }

    @Test
    void forecastEur2022April12() {
        command.setForecastStartDate(LocalDate.of(2022, 4, 12));
        command.setCurrencyCode(CurrencyCode.EUR);
        command.setForecastPeriod(ForecastPeriod.DATE);

        Rate rate = forecaster.getForecast(command);

        assertThat(rate.getRates()).hasSize(1);

        assertThat(rate.getRates().get(command.getForecastStartDate()))
                .isEqualByComparingTo(new BigDecimal("96.5621"));
    }

    @Test
    void forecastForMonthInProperRangeAndCount() {
        command.setForecastStartDate(LocalDate.now().plusDays(1));
        command.setCurrencyCode(CurrencyCode.EUR);
        command.setForecastPeriod(ForecastPeriod.MONTH);

        Rate rate = forecaster.getForecast(command);
        assertThat(rate.getRates()).hasSize(LocalDate.now().lengthOfMonth());
        BigDecimal firstValue = rate.getRates().values().stream().findFirst().get();
        rate.getRates().values()
                .forEach(v -> assertThat(v).isCloseTo(firstValue, Percentage.withPercentage(RATE_RANGE)));
    }

    @Test
    void whenDateForeLastFullMoonInFutureThenEmptyList() {
        command.setCurrencyCode(CurrencyCode.EUR);
        command.setForecastPeriod(ForecastPeriod.DATE);
        command.setForecastStartDate(LocalDate.now().plusMonths(1));

        Rate rate = forecaster.getForecast(command);
        assertThat(rate.getRates()).isEmpty();
    }

    @Test
    void whenDateIsPastThenEmptyList() {
        command.setCurrencyCode(CurrencyCode.EUR);
        command.setForecastPeriod(ForecastPeriod.DATE);
        command.setForecastStartDate(LocalDate.now().minusMonths(1));

        Rate rate = forecaster.getForecast(command);
        assertThat(rate.getRates()).isEmpty();
    }

}

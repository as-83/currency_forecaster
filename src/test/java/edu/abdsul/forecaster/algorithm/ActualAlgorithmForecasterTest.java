package edu.abdsul.forecaster.algorithm;

import edu.abdsul.forecaster.domain.Command;
import edu.abdsul.forecaster.domain.CurrencyCode;
import edu.abdsul.forecaster.domain.ForecastPeriod;
import edu.abdsul.forecaster.domain.Rate;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Java6Assertions.assertThat;

class ActualAlgorithmForecasterTest {

    private static Forecaster forecaster;
    private static Command command;

    @BeforeAll
    static void init() {
        forecaster = new ActualAlgorithmForecaster();
        command = new Command();
        command.setCurrencyCode(CurrencyCode.EUR);
    }


    @Test
    void getForecastEurForApril12() {
        command.setForecastStartDate(LocalDate.of(2022, 4, 12));
        command.setForecastPeriod(ForecastPeriod.DATE);

        Rate rate = forecaster.getForecast(command);

        assertThat(rate.getRates()).hasSize(1);

        assertThat(rate.getRates().get(command.getForecastStartDate()))
                .isEqualByComparingTo(new BigDecimal("153.3909"));
    }

    @Test
    void whenPeriodMonthThenCountIsMonthLength() {
        command.setForecastPeriod(ForecastPeriod.MONTH);
        command.setForecastStartDate(LocalDate.now().plusDays(1));

        Rate rate = forecaster.getForecast(command);

        assertThat(rate.getRates()).hasSize(LocalDate.now().lengthOfMonth());
    }


    @Test
    void whenNoDateForeTwoYearsAgoThenEmptyList() {
        command.setForecastPeriod(ForecastPeriod.DATE);
        command.setForecastStartDate(LocalDate.of(2024, 4, 12));

        Rate rate = forecaster.getForecast(command);

        assertThat(rate.getRates()).isEmpty();
    }


}

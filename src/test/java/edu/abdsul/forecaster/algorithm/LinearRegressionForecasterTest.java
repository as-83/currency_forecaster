package edu.abdsul.forecaster.algorithm;

import edu.abdsul.forecaster.domain.*;
import org.assertj.core.data.Percentage;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.stream.Collectors;

import static org.assertj.core.api.Java6Assertions.assertThat;

class LinearRegressionForecasterTest {

    @Test
    void getForecastReturnsProperSizeAndDates() {
        Forecaster forecaster = new LinearRegressionForecaster();
        Command command = new CommandBuilder().setForecastStartDate(LocalDate.now().plusDays(1))
                .setCurrencyCode(CurrencyCode.EUR)
                .setForecastPeriod(ForecastPeriod.MONTH).build();

        Rate rate = forecaster.getForecast(command);

        int lengthOfMonth = LocalDate.now().lengthOfMonth();

        assertThat(rate.getRates().keySet().stream().distinct().collect(Collectors.toList())).hasSize(lengthOfMonth);

        assertThat(rate.getRates().keySet()).contains(LocalDate.now().plusDays(1));

        rate.getRates().keySet().forEach(date ->
                assertThat(date.isAfter(LocalDate.now())
                        && !date.isAfter(LocalDate.now().plusDays(command.getForecastPeriod().getDayCount())))
                        .isTrue()
        );

        assertThat(rate.getRates().keySet()).contains(LocalDate.now().plusDays(lengthOfMonth));

        assertThat(rate.getRates().get(LocalDate.of(2022, 3,25)))
                .isCloseTo(new BigDecimal("123.43"), Percentage.withPercentage(0.005));
    }

    @Test
    void whenDateIsPastThenEmptyList() {
        Forecaster forecaster = new LinearRegressionForecaster();
        Command command = new CommandBuilder().setForecastStartDate(LocalDate.now().plusDays(1))
                .setCurrencyCode(CurrencyCode.EUR)
                .setForecastStartDate(LocalDate.now().minusDays(10))
                .build();

        Rate rate = forecaster.getForecast(command);
        assertThat(rate.getRates()).isEmpty();
    }

}

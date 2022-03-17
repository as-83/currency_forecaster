package edu.abdsul.forecaster.algorithm;

import edu.abdsul.forecaster.domain.*;
import org.assertj.core.data.Percentage;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Java6Assertions.assertThat;

class LinearRegressionForecasterTest {

    @Test
    void getForecastReturnsProperSizeAndDates() {
        Forecaster forecaster = new LinearRegressionForecaster();
        Command command = new CommandBuilder().setForecastStartDate(LocalDate.now().plusDays(1))
                .setCurrencyCode(CurrencyCode.EUR)
                .setForecastPeriod(ForecastPeriod.MONTH).build();

        Rate rate = forecaster.getForecast(command);

        assertThat(rate.getRates()).hasSize(30);
        assertThat(rate.getRates().keySet()).contains(LocalDate.now().plusDays(1));
        assertThat(rate.getRates().keySet()).contains(LocalDate.now().plusDays(30));
        assertThat(rate.getRates().get(LocalDate.of(2022, 3,25)))
                .isCloseTo(new BigDecimal("123.43"), Percentage.withPercentage(0.005));

    }

}

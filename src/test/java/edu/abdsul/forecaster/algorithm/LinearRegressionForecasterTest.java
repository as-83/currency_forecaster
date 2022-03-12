package edu.abdsul.forecaster.algorithm;

import edu.abdsul.forecaster.domain.Command;
import edu.abdsul.forecaster.domain.CurrencyCode;
import edu.abdsul.forecaster.domain.ForecastPeriod;
import edu.abdsul.forecaster.domain.Rate;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Java6Assertions.assertThat;

class LinearRegressionForecasterTest {

    @Test
    void getForecastReturnsProperSizeForecast() {
        Forecaster forecaster = new LinearRegressionForecaster();
        Command command = new Command();
        command.setForecastStartDate(LocalDate.now().plusDays(1));
        command.setCurrencyCode(CurrencyCode.EUR);
        command.setForecastPeriod(ForecastPeriod.MONTH);

        Rate rate = forecaster.getForecast(command);

        assertThat(rate.getRates()).hasSize(30);
        assertThat(rate.getRates().keySet()).contains(LocalDate.now().plusDays(1));
        assertThat(rate.getRates().keySet()).contains(LocalDate.now().plusDays(30));

    }

}

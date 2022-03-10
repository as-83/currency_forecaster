package edu.abdsul.forecaster.algorithm;

import edu.abdsul.forecaster.domain.Command;
import edu.abdsul.forecaster.domain.CurrencyCode;
import edu.abdsul.forecaster.domain.ForecastPeriod;
import edu.abdsul.forecaster.domain.Rate;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ActualAlgorithmForecasterTest {



    @Test
    void getForecast() {
        Forecaster forecaster = new ActualAlgorithmForecaster();
        Command command = new Command();
        command.setForecastStartDate(LocalDate.now().plusDays(1));
        command.setCurrencyCode(CurrencyCode.EUR);
        command.setForecastPeriod(ForecastPeriod.DATE);

        Rate rate = forecaster.getForecast(command);

        assertThat(rate.getRates()).hasSize(1);

    }
}
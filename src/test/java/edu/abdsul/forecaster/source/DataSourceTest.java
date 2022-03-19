package edu.abdsul.forecaster.source;

import edu.abdsul.forecaster.domain.CurrencyCode;
import edu.abdsul.forecaster.domain.Rate;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Java6Assertions.assertThat;

class DataSourceTest {

    @Test
    public void getLastNRatesGetsNRates() {
        int numberOfRates = 10;
        DataSource dataSource = new FileDataSource();
        Rate rate = dataSource.getLastNRates(CurrencyCode.TRY, numberOfRates);
        assertThat(rate.getRates()).hasSize(numberOfRates);
        assertThat(rate.getCurrencyCode()).isEqualTo(CurrencyCode.TRY);
        assertThat(rate.getRates().keySet()).allMatch(
                k -> k.isAfter(LocalDate.of(2022, 2, 18)));
    }

}

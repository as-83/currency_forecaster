package edu.abdsul.forecaster.utils;

import edu.abdsul.forecaster.domain.CurrencyCode;
import edu.abdsul.forecaster.domain.Rate;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Java6Assertions.assertThat;

class RateHistoryFormatterTest {

    @Test
    public void returnsRateValuesForEvery30Days() {
        Rate rateHistory = new Rate(CurrencyCode.TRY);
        for (int i = 0; i < 30; i++) {
            if ((i + 1) % 7 != 0) {
                rateHistory.addRate(LocalDate.now().minusDays(i), BigDecimal.valueOf(55 - 0.2 * i));
            }
        }

        assertThat(rateHistory.getRates()).hasSize(26);

        assertThat(RateHistoryHelper.format(rateHistory)).hasSize(30);
    }

}

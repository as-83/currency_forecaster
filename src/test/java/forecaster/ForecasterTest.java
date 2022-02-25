package forecaster;

import forecaster.domain.Rate;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


class ForecasterTest {
    @Test
    void whenDurationOneThenSizeOne() throws IOException {
        Forecaster forecaster = new Forecaster();
        List<Rate> rates = forecaster.getForecast("TRY", 1);
        assertThat(rates.size()).isEqualTo(1);
    }

    @Test
    void whenDurationSevenThenSizeSeven() throws IOException {
        Forecaster forecaster = new Forecaster();
        List<Rate> rates = forecaster.getForecast("TRY", 7);
        assertThat(rates.size()).isEqualTo(7);
    }

    @Test
    void whenTryAnd23FebruaryAndOneDay() throws IOException {
        Forecaster forecaster = new Forecaster();
        List<Rate> rates = forecaster.getForecast("TRY", 1);
        BigDecimal expectedValue = BigDecimal.valueOf(57.92);
        BigDecimal currentValue =  rates.get(0).getValue().setScale(2, RoundingMode.HALF_UP);
        assertThat(currentValue.compareTo(expectedValue)).isEqualTo(0);
    }

    @Test
    void whenUsdAnd23FebruaryAndOneDay() throws IOException {
        Forecaster forecaster = new Forecaster();
        List<Rate> rates = forecaster.getForecast("USD", 1);
        BigDecimal expectedValue = BigDecimal.valueOf(78.11);
        BigDecimal currentValue =  rates.get(0).getValue().setScale(2, RoundingMode.HALF_UP);
        assertThat(currentValue.compareTo(expectedValue)).isEqualTo(0);
    }

}

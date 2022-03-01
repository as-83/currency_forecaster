package forecaster;

import forecaster.domain.CurrencyCode;
import forecaster.domain.Rate;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


class ForecasterTest {
    private static Forecaster forecaster;

    @BeforeAll
    static void   init(){
        forecaster = new Forecaster();
    }

    @Test
    void whenDurationOneThenSizeOne() {

        List<Rate> rates = forecaster.getForecast(CurrencyCode.TRY, 1);
        assertThat(rates.size()).isEqualTo(1);
    }

    @Test
    void whenDurationSevenThenSizeSeven() {
        List<Rate> rates = forecaster.getForecast(CurrencyCode.TRY, 7);
        assertThat(rates.size()).isEqualTo(7);
    }

    @Test
    void whenTryAnd23FebruaryAndOneDay() {
        List<Rate> rates = forecaster.getForecast(CurrencyCode.TRY, 1);
        BigDecimal expectedValue = BigDecimal.valueOf(57.86);
        BigDecimal currentValue =  rates.get(0).getValue().setScale(2, RoundingMode.HALF_UP);
        assertThat(currentValue.compareTo(expectedValue)).isEqualTo(0);
    }

    @Test
    void whenUsdAnd23FebruaryAndOneDay() {
        List<Rate> rates = forecaster.getForecast(CurrencyCode.USD, 1);
        BigDecimal expectedValue = BigDecimal.valueOf(77.98);
        BigDecimal currentValue =  rates.get(0).getValue().setScale(2, RoundingMode.HALF_UP);
        assertThat(currentValue.compareTo(expectedValue)).isEqualTo(0);
    }



}

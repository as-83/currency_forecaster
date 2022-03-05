package edu.abdsul.forecaster;

import edu.abdsul.forecaster.domain.CurrencyCode;
import edu.abdsul.forecaster.domain.Rate;
import edu.abdsul.forecaster.source.DataSource;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
class ForecasterTest {
    private static Forecaster forecaster;

    @Mock
    private DataSource mockDataSource;

    @BeforeEach
    void   init(){
        forecaster = new Forecaster();
    }

    @Test
    void whenDurationOneThenSizeOne() {

        Rate rate = forecaster.getForecast(CurrencyCode.USD, 1);
        System.out.println(rate.getRates().entrySet().size());
        assertThat(rate.getRates()).isNotEmpty();
        assertThat(rate.getRates().keySet().size()).isEqualTo(1);
    }

    @Test
    public void whenDurationSevenThenSizeSeven() {
        Rate rate = forecaster.getForecast(CurrencyCode.TRY, 7);
        assertThat(rate.getRates()).hasSize(7);
    }

    @Test
    public void whenTryAnd23FebruaryAndOneDay() {
        Rate rate = forecaster.getForecast(CurrencyCode.TRY, 1);
        BigDecimal expectedValue = BigDecimal.valueOf(68.86);
        BigDecimal currentValue =  rate.getRates().values().iterator().next().setScale(2, RoundingMode.HALF_UP);
        assertThat(currentValue.compareTo(expectedValue)).isEqualTo(0);
    }

    @Test
    public void whenUsdAnd23FebruaryAndOneDay() {
        Rate rate = forecaster.getForecast(CurrencyCode.USD, 1);
        BigDecimal expectedValue = BigDecimal.valueOf(96.66);
        BigDecimal currentValue =  rate.getRates().values().iterator().next().setScale(2, RoundingMode.HALF_UP);
        System.out.println(currentValue.doubleValue());
        assertThat(currentValue.compareTo(expectedValue)).isEqualTo(0);
    }

    @Test
    public void testWithMock() {
        MockitoAnnotations.openMocks(this);
        forecaster.getForecastType().setDataSource(mockDataSource);
        Rate mockRate = new Rate(CurrencyCode.EUR);
                IntStream.range(1, 8)
                        .forEach(i -> mockRate.addRate(LocalDate.now().minusDays(i), new BigDecimal(120)));

        given(mockDataSource.getAllRates(CurrencyCode.EUR)).willReturn(mockRate);

        Rate result = forecaster.getForecast(CurrencyCode.EUR, 1);
        assertThat(result.getRates().values()).hasSize(1);
        assertThat(result.getRates().values().iterator().next().compareTo(new BigDecimal(120))).isEqualTo(0);


    }



}

package edu.abdsul.forecaster;

import edu.abdsul.forecaster.source.DataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.assertj.core.api.Assertions.assertThat;

class ControllerTest {
    private static ForecasterController forecasterController;

    @Mock
    private DataSource mockDataSource;

    @BeforeEach
    void   init(){
        forecasterController = new ForecasterController();
    }

    @Test
    void whenDurationOneThenSizeOne() {

        String rate = forecasterController.getForecast("rate USD tomorrow");
        System.out.println(rate);
        //assertThat(rate.getRates()).isNotEmpty();
        //assertThat(rate.getRates().keySet().size()).isEqualTo(1);
    }

    @Test
    public void whenDurationSevenThenSizeSeven() {
        String rate = forecasterController.getForecast("rate USD week");
        System.out.println(rate);
        //assertThat(rate).hasSize(7);
    }

    @Test
    public void whenTryAnd23FebruaryAndOneDay() {
       /* Rate rate = forecasterController.getForecast(CurrencyCode.TRY, 1);
        BigDecimal expectedValue = BigDecimal.valueOf(68.86);
        BigDecimal currentValue =  rate.getRates().values().iterator().next().setScale(2, RoundingMode.HALF_UP);
        assertThat(currentValue.compareTo(expectedValue)).isEqualTo(0);*/
    }

    @Test
    public void whenUsdAnd23FebruaryAndOneDay() {
       /* Rate rate = forecasterController.getForecast(CurrencyCode.USD, 1);
        BigDecimal expectedValue = BigDecimal.valueOf(96.66);
        BigDecimal currentValue =  rate.getRates().values().iterator().next().setScale(2, RoundingMode.HALF_UP);
        System.out.println(currentValue.doubleValue());
        assertThat(currentValue.compareTo(expectedValue)).isEqualTo(0);*/
    }

    @Test
    public void testWithMock() {
        /*MockitoAnnotations.openMocks(this);
        forecasterController.getForecastType().setDataSource(mockDataSource);
        Rate mockRate = new Rate(CurrencyCode.EUR);
                IntStream.range(1, 8)
                        .forEach(i -> mockRate.addRate(LocalDate.now().minusDays(i), new BigDecimal(120)));

        given(mockDataSource.getAllRates(CurrencyCode.EUR)).willReturn(mockRate);

        Rate result = forecasterController.getForecast(CurrencyCode.EUR, 1);
        assertThat(result.getRates().values()).hasSize(1);
        assertThat(result.getRates().values().iterator().next().compareTo(new BigDecimal(120))).isEqualTo(0);*/


    }



}

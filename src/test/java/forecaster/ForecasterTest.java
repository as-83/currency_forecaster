package forecaster;

import forecaster.domain.Rate;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class ForecasterTest {
    @Test
    void whenDurationOneThenSizeOne() throws IOException {
        Forecaster forecaster = new Forecaster();
        List<Rate> rates = forecaster.getForecast("TRY", 1);
        assertEquals(rates.size(), 1);
    }

    @Test
    void whenDurationSevenThenSizeSeven() throws IOException {
        Forecaster forecaster = new Forecaster();
        List<Rate> rates = forecaster.getForecast("TRY", 7);
        assertEquals(rates.size(), 7);
    }

    @Test
    void whenTryAnd23FebruaryAndOneDay() throws IOException {
        Forecaster forecaster = new Forecaster();
        List<Rate> rates = forecaster.getForecast("TRY", 1);
        rates.forEach(System.out::println);
        assertTrue(Math.abs(rates.get(0).getValue() - 57.92) < 0.009);
    }

    @Test
    void whenUsdAnd23FebruaryAndOneDay() throws IOException {
        Forecaster forecaster = new Forecaster();
        List<Rate> rates = forecaster.getForecast("USD", 1);
        rates.forEach(System.out::println);
        assertTrue(Math.abs(rates.get(0).getValue() - 78.11) < 0.009);
    }
}
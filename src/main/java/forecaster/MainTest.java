package forecaster;

import forecaster.domain.Rate;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ForecasterTest {
    @Test
    void test() {
        Forecaster forecaster = new Forecaster();
        List<Rate> rates = forecaster.getForecast("TRY", 1);
        rates.forEach(System.out::println);
    }

}
